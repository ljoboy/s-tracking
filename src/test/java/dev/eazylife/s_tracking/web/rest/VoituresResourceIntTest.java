package dev.eazylife.s_tracking.web.rest;

import dev.eazylife.s_tracking.STrackingApp;

import dev.eazylife.s_tracking.domain.Voitures;
import dev.eazylife.s_tracking.repository.VoituresRepository;
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
import java.util.List;


import static dev.eazylife.s_tracking.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VoituresResource REST controller.
 *
 * @see VoituresResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = STrackingApp.class)
public class VoituresResourceIntTest {

    private static final String DEFAULT_PLAQUE = "AAAAAAAAAA";
    private static final String UPDATED_PLAQUE = "BBBBBBBBBB";

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final String DEFAULT_COULEUR = "AAAAAAAAAA";
    private static final String UPDATED_COULEUR = "BBBBBBBBBB";

    @Autowired
    private VoituresRepository voituresRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVoituresMockMvc;

    private Voitures voitures;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoituresResource voituresResource = new VoituresResource(voituresRepository);
        this.restVoituresMockMvc = MockMvcBuilders.standaloneSetup(voituresResource)
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
    public static Voitures createEntity(EntityManager em) {
        Voitures voitures = new Voitures()
            .plaque(DEFAULT_PLAQUE)
            .marque(DEFAULT_MARQUE)
            .couleur(DEFAULT_COULEUR);
        return voitures;
    }

    @Before
    public void initTest() {
        voitures = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoitures() throws Exception {
        int databaseSizeBeforeCreate = voituresRepository.findAll().size();

        // Create the Voitures
        restVoituresMockMvc.perform(post("/api/voitures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voitures)))
            .andExpect(status().isCreated());

        // Validate the Voitures in the database
        List<Voitures> voituresList = voituresRepository.findAll();
        assertThat(voituresList).hasSize(databaseSizeBeforeCreate + 1);
        Voitures testVoitures = voituresList.get(voituresList.size() - 1);
        assertThat(testVoitures.getPlaque()).isEqualTo(DEFAULT_PLAQUE);
        assertThat(testVoitures.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testVoitures.getCouleur()).isEqualTo(DEFAULT_COULEUR);
    }

    @Test
    @Transactional
    public void createVoituresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voituresRepository.findAll().size();

        // Create the Voitures with an existing ID
        voitures.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoituresMockMvc.perform(post("/api/voitures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voitures)))
            .andExpect(status().isBadRequest());

        // Validate the Voitures in the database
        List<Voitures> voituresList = voituresRepository.findAll();
        assertThat(voituresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPlaqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = voituresRepository.findAll().size();
        // set the field null
        voitures.setPlaque(null);

        // Create the Voitures, which fails.

        restVoituresMockMvc.perform(post("/api/voitures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voitures)))
            .andExpect(status().isBadRequest());

        List<Voitures> voituresList = voituresRepository.findAll();
        assertThat(voituresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVoitures() throws Exception {
        // Initialize the database
        voituresRepository.saveAndFlush(voitures);

        // Get all the voituresList
        restVoituresMockMvc.perform(get("/api/voitures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voitures.getId().intValue())))
            .andExpect(jsonPath("$.[*].plaque").value(hasItem(DEFAULT_PLAQUE.toString())))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE.toString())))
            .andExpect(jsonPath("$.[*].couleur").value(hasItem(DEFAULT_COULEUR.toString())));
    }
    

    @Test
    @Transactional
    public void getVoitures() throws Exception {
        // Initialize the database
        voituresRepository.saveAndFlush(voitures);

        // Get the voitures
        restVoituresMockMvc.perform(get("/api/voitures/{id}", voitures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voitures.getId().intValue()))
            .andExpect(jsonPath("$.plaque").value(DEFAULT_PLAQUE.toString()))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE.toString()))
            .andExpect(jsonPath("$.couleur").value(DEFAULT_COULEUR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingVoitures() throws Exception {
        // Get the voitures
        restVoituresMockMvc.perform(get("/api/voitures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoitures() throws Exception {
        // Initialize the database
        voituresRepository.saveAndFlush(voitures);

        int databaseSizeBeforeUpdate = voituresRepository.findAll().size();

        // Update the voitures
        Voitures updatedVoitures = voituresRepository.findById(voitures.getId()).get();
        // Disconnect from session so that the updates on updatedVoitures are not directly saved in db
        em.detach(updatedVoitures);
        updatedVoitures
            .plaque(UPDATED_PLAQUE)
            .marque(UPDATED_MARQUE)
            .couleur(UPDATED_COULEUR);

        restVoituresMockMvc.perform(put("/api/voitures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVoitures)))
            .andExpect(status().isOk());

        // Validate the Voitures in the database
        List<Voitures> voituresList = voituresRepository.findAll();
        assertThat(voituresList).hasSize(databaseSizeBeforeUpdate);
        Voitures testVoitures = voituresList.get(voituresList.size() - 1);
        assertThat(testVoitures.getPlaque()).isEqualTo(UPDATED_PLAQUE);
        assertThat(testVoitures.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testVoitures.getCouleur()).isEqualTo(UPDATED_COULEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingVoitures() throws Exception {
        int databaseSizeBeforeUpdate = voituresRepository.findAll().size();

        // Create the Voitures

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVoituresMockMvc.perform(put("/api/voitures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voitures)))
            .andExpect(status().isBadRequest());

        // Validate the Voitures in the database
        List<Voitures> voituresList = voituresRepository.findAll();
        assertThat(voituresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVoitures() throws Exception {
        // Initialize the database
        voituresRepository.saveAndFlush(voitures);

        int databaseSizeBeforeDelete = voituresRepository.findAll().size();

        // Get the voitures
        restVoituresMockMvc.perform(delete("/api/voitures/{id}", voitures.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Voitures> voituresList = voituresRepository.findAll();
        assertThat(voituresList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voitures.class);
        Voitures voitures1 = new Voitures();
        voitures1.setId(1L);
        Voitures voitures2 = new Voitures();
        voitures2.setId(voitures1.getId());
        assertThat(voitures1).isEqualTo(voitures2);
        voitures2.setId(2L);
        assertThat(voitures1).isNotEqualTo(voitures2);
        voitures1.setId(null);
        assertThat(voitures1).isNotEqualTo(voitures2);
    }
}
