package dev.eazylife.s_tracking.web.rest;

import dev.eazylife.s_tracking.STrackingApp;

import dev.eazylife.s_tracking.domain.Clients;
import dev.eazylife.s_tracking.repository.ClientsRepository;
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

import dev.eazylife.s_tracking.domain.enumeration.Types;
/**
 * Test class for the ClientsResource REST controller.
 *
 * @see ClientsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = STrackingApp.class)
public class ClientsResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_ABONNEMENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ABONNEMENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Types DEFAULT_TYPES = Types.PARTICULIER;
    private static final Types UPDATED_TYPES = Types.ENTREPRISE;

    private static final Long DEFAULT_TELEPHONE = 1L;
    private static final Long UPDATED_TELEPHONE = 2L;

    private static final String DEFAULT_EMAIL = "Q6@K";
    private static final String UPDATED_EMAIL = "6R@P";

    private static final String DEFAULT_RCCM = "AAAAAAAAAA";
    private static final String UPDATED_RCCM = "BBBBBBBBBB";

    @Autowired
    private ClientsRepository clientsRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientsMockMvc;

    private Clients clients;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientsResource clientsResource = new ClientsResource(clientsRepository);
        this.restClientsMockMvc = MockMvcBuilders.standaloneSetup(clientsResource)
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
    public static Clients createEntity(EntityManager em) {
        Clients clients = new Clients()
            .nom(DEFAULT_NOM)
            .dateAbonnement(DEFAULT_DATE_ABONNEMENT)
            .province(DEFAULT_PROVINCE)
            .ville(DEFAULT_VILLE)
            .adresse(DEFAULT_ADRESSE)
            .password(DEFAULT_PASSWORD)
            .types(DEFAULT_TYPES)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL)
            .rccm(DEFAULT_RCCM);
        return clients;
    }

    @Before
    public void initTest() {
        clients = createEntity(em);
    }

    @Test
    @Transactional
    public void createClients() throws Exception {
        int databaseSizeBeforeCreate = clientsRepository.findAll().size();

        // Create the Clients
        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isCreated());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate + 1);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testClients.getDateAbonnement()).isEqualTo(DEFAULT_DATE_ABONNEMENT);
        assertThat(testClients.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testClients.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testClients.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testClients.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testClients.getTypes()).isEqualTo(DEFAULT_TYPES);
        assertThat(testClients.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testClients.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClients.getRccm()).isEqualTo(DEFAULT_RCCM);
    }

    @Test
    @Transactional
    public void createClientsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientsRepository.findAll().size();

        // Create the Clients with an existing ID
        clients.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientsRepository.findAll().size();
        // set the field null
        clients.setNom(null);

        // Create the Clients, which fails.

        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isBadRequest());

        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientsRepository.findAll().size();
        // set the field null
        clients.setPassword(null);

        // Create the Clients, which fails.

        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isBadRequest());

        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientsRepository.findAll().size();
        // set the field null
        clients.setTelephone(null);

        // Create the Clients, which fails.

        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isBadRequest());

        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientsRepository.findAll().size();
        // set the field null
        clients.setEmail(null);

        // Create the Clients, which fails.

        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isBadRequest());

        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList
        restClientsMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clients.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].dateAbonnement").value(hasItem(DEFAULT_DATE_ABONNEMENT.toString())))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].types").value(hasItem(DEFAULT_TYPES.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].rccm").value(hasItem(DEFAULT_RCCM.toString())));
    }
    

    @Test
    @Transactional
    public void getClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get the clients
        restClientsMockMvc.perform(get("/api/clients/{id}", clients.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clients.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.dateAbonnement").value(DEFAULT_DATE_ABONNEMENT.toString()))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE.toString()))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.types").value(DEFAULT_TYPES.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.rccm").value(DEFAULT_RCCM.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingClients() throws Exception {
        // Get the clients
        restClientsMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Update the clients
        Clients updatedClients = clientsRepository.findById(clients.getId()).get();
        // Disconnect from session so that the updates on updatedClients are not directly saved in db
        em.detach(updatedClients);
        updatedClients
            .nom(UPDATED_NOM)
            .dateAbonnement(UPDATED_DATE_ABONNEMENT)
            .province(UPDATED_PROVINCE)
            .ville(UPDATED_VILLE)
            .adresse(UPDATED_ADRESSE)
            .password(UPDATED_PASSWORD)
            .types(UPDATED_TYPES)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .rccm(UPDATED_RCCM);

        restClientsMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClients)))
            .andExpect(status().isOk());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testClients.getDateAbonnement()).isEqualTo(UPDATED_DATE_ABONNEMENT);
        assertThat(testClients.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testClients.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testClients.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testClients.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testClients.getTypes()).isEqualTo(UPDATED_TYPES);
        assertThat(testClients.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testClients.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClients.getRccm()).isEqualTo(UPDATED_RCCM);
    }

    @Test
    @Transactional
    public void updateNonExistingClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Create the Clients

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientsMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeDelete = clientsRepository.findAll().size();

        // Get the clients
        restClientsMockMvc.perform(delete("/api/clients/{id}", clients.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clients.class);
        Clients clients1 = new Clients();
        clients1.setId(1L);
        Clients clients2 = new Clients();
        clients2.setId(clients1.getId());
        assertThat(clients1).isEqualTo(clients2);
        clients2.setId(2L);
        assertThat(clients1).isNotEqualTo(clients2);
        clients1.setId(null);
        assertThat(clients1).isNotEqualTo(clients2);
    }
}
