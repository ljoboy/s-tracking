package dev.eazylife.s_tracking.web.rest;

import dev.eazylife.s_tracking.STrackingApp;

import dev.eazylife.s_tracking.domain.Historiques;
import dev.eazylife.s_tracking.repository.HistoriquesRepository;
import dev.eazylife.s_tracking.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static dev.eazylife.s_tracking.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HistoriquesResource REST controller.
 *
 * @see HistoriquesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = STrackingApp.class)
public class HistoriquesResourceIntTest {

    private static final Instant DEFAULT_DATE_HISTORIQUE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_HISTORIQUE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    @Autowired
    private HistoriquesRepository historiquesRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHistoriquesMockMvc;

    private Historiques historiques;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HistoriquesResource historiquesResource = new HistoriquesResource(historiquesRepository);
        this.restHistoriquesMockMvc = MockMvcBuilders.standaloneSetup(historiquesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historiques createEntity(EntityManager em) {
        Historiques historiques = new Historiques()
            .dateHistorique(DEFAULT_DATE_HISTORIQUE)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE);
        return historiques;
    }

    @Before
    public void initTest() {
        historiques = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistoriques() throws Exception {
        int databaseSizeBeforeCreate = historiquesRepository.findAll().size();

        // Create the Historiques
        restHistoriquesMockMvc.perform(post("/api/historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiques)))
            .andExpect(status().isCreated());

        // Validate the Historiques in the database
        List<Historiques> historiquesList = historiquesRepository.findAll();
        assertThat(historiquesList).hasSize(databaseSizeBeforeCreate + 1);
        Historiques testHistoriques = historiquesList.get(historiquesList.size() - 1);
        assertThat(testHistoriques.getDateHistorique()).isEqualTo(DEFAULT_DATE_HISTORIQUE);
        assertThat(testHistoriques.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testHistoriques.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
    }

    @Test
    @Transactional
    public void createHistoriquesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historiquesRepository.findAll().size();

        // Create the Historiques with an existing ID
        historiques.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoriquesMockMvc.perform(post("/api/historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiques)))
            .andExpect(status().isBadRequest());

        // Validate the Historiques in the database
        List<Historiques> historiquesList = historiquesRepository.findAll();
        assertThat(historiquesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiquesRepository.findAll().size();
        // set the field null
        historiques.setLongitude(null);

        // Create the Historiques, which fails.

        restHistoriquesMockMvc.perform(post("/api/historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiques)))
            .andExpect(status().isBadRequest());

        List<Historiques> historiquesList = historiquesRepository.findAll();
        assertThat(historiquesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = historiquesRepository.findAll().size();
        // set the field null
        historiques.setLatitude(null);

        // Create the Historiques, which fails.

        restHistoriquesMockMvc.perform(post("/api/historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiques)))
            .andExpect(status().isBadRequest());

        List<Historiques> historiquesList = historiquesRepository.findAll();
        assertThat(historiquesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHistoriques() throws Exception {
        // Initialize the database
        historiquesRepository.saveAndFlush(historiques);

        // Get all the historiquesList
        restHistoriquesMockMvc.perform(get("/api/historiques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historiques.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateHistorique").value(hasItem(DEFAULT_DATE_HISTORIQUE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getHistoriques() throws Exception {
        // Initialize the database
        historiquesRepository.saveAndFlush(historiques);

        // Get the historiques
        restHistoriquesMockMvc.perform(get("/api/historiques/{id}", historiques.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(historiques.getId().intValue()))
            .andExpect(jsonPath("$.dateHistorique").value(DEFAULT_DATE_HISTORIQUE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingHistoriques() throws Exception {
        // Get the historiques
        restHistoriquesMockMvc.perform(get("/api/historiques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoriques() throws Exception {
        // Initialize the database
        historiquesRepository.saveAndFlush(historiques);

        int databaseSizeBeforeUpdate = historiquesRepository.findAll().size();

        // Update the historiques
        Historiques updatedHistoriques = historiquesRepository.findById(historiques.getId()).get();
        // Disconnect from session so that the updates on updatedHistoriques are not directly saved in db
        em.detach(updatedHistoriques);
        updatedHistoriques
            .dateHistorique(UPDATED_DATE_HISTORIQUE)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE);

        restHistoriquesMockMvc.perform(put("/api/historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHistoriques)))
            .andExpect(status().isOk());

        // Validate the Historiques in the database
        List<Historiques> historiquesList = historiquesRepository.findAll();
        assertThat(historiquesList).hasSize(databaseSizeBeforeUpdate);
        Historiques testHistoriques = historiquesList.get(historiquesList.size() - 1);
        assertThat(testHistoriques.getDateHistorique()).isEqualTo(UPDATED_DATE_HISTORIQUE);
        assertThat(testHistoriques.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testHistoriques.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingHistoriques() throws Exception {
        int databaseSizeBeforeUpdate = historiquesRepository.findAll().size();

        // Create the Historiques

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHistoriquesMockMvc.perform(put("/api/historiques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(historiques)))
            .andExpect(status().isBadRequest());

        // Validate the Historiques in the database
        List<Historiques> historiquesList = historiquesRepository.findAll();
        assertThat(historiquesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHistoriques() throws Exception {
        // Initialize the database
        historiquesRepository.saveAndFlush(historiques);

        int databaseSizeBeforeDelete = historiquesRepository.findAll().size();

        // Get the historiques
        restHistoriquesMockMvc.perform(delete("/api/historiques/{id}", historiques.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Historiques> historiquesList = historiquesRepository.findAll();
        assertThat(historiquesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Historiques.class);
        Historiques historiques1 = new Historiques();
        historiques1.setId(1L);
        Historiques historiques2 = new Historiques();
        historiques2.setId(historiques1.getId());
        assertThat(historiques1).isEqualTo(historiques2);
        historiques2.setId(2L);
        assertThat(historiques1).isNotEqualTo(historiques2);
        historiques1.setId(null);
        assertThat(historiques1).isNotEqualTo(historiques2);
    }
}
