package dev.eazylife.s_tracking.web.rest;

import com.codahale.metrics.annotation.Timed;
import dev.eazylife.s_tracking.domain.Devices;
import dev.eazylife.s_tracking.repository.DevicesRepository;
import dev.eazylife.s_tracking.web.rest.errors.BadRequestAlertException;
import dev.eazylife.s_tracking.web.rest.util.HeaderUtil;
import dev.eazylife.s_tracking.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Devices.
 */
@RestController
@RequestMapping("/api")
public class DevicesResource {

    private final Logger log = LoggerFactory.getLogger(DevicesResource.class);

    private static final String ENTITY_NAME = "devices";

    private final DevicesRepository devicesRepository;

    public DevicesResource(DevicesRepository devicesRepository) {
        this.devicesRepository = devicesRepository;
    }

    /**
     * POST  /devices : Create a new devices.
     *
     * @param devices the devices to create
     * @return the ResponseEntity with status 201 (Created) and with body the new devices, or with status 400 (Bad Request) if the devices has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/devices")
    @Timed
    public ResponseEntity<Devices> createDevices(@Valid @RequestBody Devices devices) throws URISyntaxException {
        log.debug("REST request to save Devices : {}", devices);
        if (devices.getId() != null) {
            throw new BadRequestAlertException("A new devices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Devices result = devicesRepository.save(devices);
        return ResponseEntity.created(new URI("/api/devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /devices : Updates an existing devices.
     *
     * @param devices the devices to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated devices,
     * or with status 400 (Bad Request) if the devices is not valid,
     * or with status 500 (Internal Server Error) if the devices couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/devices")
    @Timed
    public ResponseEntity<Devices> updateDevices(@Valid @RequestBody Devices devices) throws URISyntaxException {
        log.debug("REST request to update Devices : {}", devices);
        if (devices.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Devices result = devicesRepository.save(devices);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, devices.getId().toString()))
            .body(result);
    }

    /**
     * GET  /devices : get all the devices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of devices in body
     */
    @GetMapping("/devices")
    @Timed
    public ResponseEntity<List<Devices>> getAllDevices(Pageable pageable) {
        log.debug("REST request to get a page of Devices");
        Page<Devices> page = devicesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/devices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /devices/:id : get the "id" devices.
     *
     * @param id the id of the devices to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devices, or with status 404 (Not Found)
     */
    @GetMapping("/devices/{id}")
    @Timed
    public ResponseEntity<Devices> getDevices(@PathVariable Long id) {
        log.debug("REST request to get Devices : {}", id);
        Optional<Devices> devices = devicesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(devices);
    }

    /**
     * DELETE  /devices/:id : delete the "id" devices.
     *
     * @param id the id of the devices to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/devices/{id}")
    @Timed
    public ResponseEntity<Void> deleteDevices(@PathVariable Long id) {
        log.debug("REST request to delete Devices : {}", id);

        devicesRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
