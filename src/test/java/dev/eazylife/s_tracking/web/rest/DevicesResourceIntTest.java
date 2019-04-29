package dev.eazylife.s_tracking.web.rest;

import dev.eazylife.s_tracking.STrackingApp;

import dev.eazylife.s_tracking.domain.Devices;
import dev.eazylife.s_tracking.repository.DevicesRepository;
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
 * Test class for the DevicesResource REST controller.
 *
 * @see DevicesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = STrackingApp.class)
public class DevicesResourceIntTest {

    private static final Long DEFAULT_IMEI = 1L;
    private static final Long UPDATED_IMEI = 2L;

    private static final Long DEFAULT_NUMERO_SIM = 1L;
    private static final Long UPDATED_NUMERO_SIM = 2L;

    @Autowired
    private DevicesRepository devicesRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDevicesMockMvc;

    private Devices devices;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DevicesResource devicesResource = new DevicesResource(devicesRepository);
        this.restDevicesMockMvc = MockMvcBuilders.standaloneSetup(devicesResource)
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
    public static Devices createEntity(EntityManager em) {
        Devices devices = new Devices()
            .imei(DEFAULT_IMEI)
            .numeroSim(DEFAULT_NUMERO_SIM);
        return devices;
    }

    @Before
    public void initTest() {
        devices = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevices() throws Exception {
        int databaseSizeBeforeCreate = devicesRepository.findAll().size();

        // Create the Devices
        restDevicesMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devices)))
            .andExpect(status().isCreated());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeCreate + 1);
        Devices testDevices = devicesList.get(devicesList.size() - 1);
        assertThat(testDevices.getImei()).isEqualTo(DEFAULT_IMEI);
        assertThat(testDevices.getNumeroSim()).isEqualTo(DEFAULT_NUMERO_SIM);
    }

    @Test
    @Transactional
    public void createDevicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = devicesRepository.findAll().size();

        // Create the Devices with an existing ID
        devices.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDevicesMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devices)))
            .andExpect(status().isBadRequest());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkImeiIsRequired() throws Exception {
        int databaseSizeBeforeTest = devicesRepository.findAll().size();
        // set the field null
        devices.setImei(null);

        // Create the Devices, which fails.

        restDevicesMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devices)))
            .andExpect(status().isBadRequest());

        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroSimIsRequired() throws Exception {
        int databaseSizeBeforeTest = devicesRepository.findAll().size();
        // set the field null
        devices.setNumeroSim(null);

        // Create the Devices, which fails.

        restDevicesMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devices)))
            .andExpect(status().isBadRequest());

        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDevices() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        // Get all the devicesList
        restDevicesMockMvc.perform(get("/api/devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devices.getId().intValue())))
            .andExpect(jsonPath("$.[*].imei").value(hasItem(DEFAULT_IMEI.intValue())))
            .andExpect(jsonPath("$.[*].numeroSim").value(hasItem(DEFAULT_NUMERO_SIM.intValue())));
    }
    

    @Test
    @Transactional
    public void getDevices() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        // Get the devices
        restDevicesMockMvc.perform(get("/api/devices/{id}", devices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(devices.getId().intValue()))
            .andExpect(jsonPath("$.imei").value(DEFAULT_IMEI.intValue()))
            .andExpect(jsonPath("$.numeroSim").value(DEFAULT_NUMERO_SIM.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDevices() throws Exception {
        // Get the devices
        restDevicesMockMvc.perform(get("/api/devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevices() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        int databaseSizeBeforeUpdate = devicesRepository.findAll().size();

        // Update the devices
        Devices updatedDevices = devicesRepository.findById(devices.getId()).get();
        // Disconnect from session so that the updates on updatedDevices are not directly saved in db
        em.detach(updatedDevices);
        updatedDevices
            .imei(UPDATED_IMEI)
            .numeroSim(UPDATED_NUMERO_SIM);

        restDevicesMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDevices)))
            .andExpect(status().isOk());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeUpdate);
        Devices testDevices = devicesList.get(devicesList.size() - 1);
        assertThat(testDevices.getImei()).isEqualTo(UPDATED_IMEI);
        assertThat(testDevices.getNumeroSim()).isEqualTo(UPDATED_NUMERO_SIM);
    }

    @Test
    @Transactional
    public void updateNonExistingDevices() throws Exception {
        int databaseSizeBeforeUpdate = devicesRepository.findAll().size();

        // Create the Devices

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDevicesMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devices)))
            .andExpect(status().isBadRequest());

        // Validate the Devices in the database
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDevices() throws Exception {
        // Initialize the database
        devicesRepository.saveAndFlush(devices);

        int databaseSizeBeforeDelete = devicesRepository.findAll().size();

        // Get the devices
        restDevicesMockMvc.perform(delete("/api/devices/{id}", devices.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Devices> devicesList = devicesRepository.findAll();
        assertThat(devicesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Devices.class);
        Devices devices1 = new Devices();
        devices1.setId(1L);
        Devices devices2 = new Devices();
        devices2.setId(devices1.getId());
        assertThat(devices1).isEqualTo(devices2);
        devices2.setId(2L);
        assertThat(devices1).isNotEqualTo(devices2);
        devices1.setId(null);
        assertThat(devices1).isNotEqualTo(devices2);
    }
}
