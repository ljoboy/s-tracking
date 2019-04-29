package dev.eazylife.s_tracking.web.rest;

import com.codahale.metrics.annotation.Timed;
import dev.eazylife.s_tracking.domain.Voitures;
import dev.eazylife.s_tracking.repository.VoituresRepository;
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
 * REST controller for managing Voitures.
 */
@RestController
@RequestMapping("/api")
public class VoituresResource {

    private final Logger log = LoggerFactory.getLogger(VoituresResource.class);

    private static final String ENTITY_NAME = "voitures";

    private final VoituresRepository voituresRepository;

    public VoituresResource(VoituresRepository voituresRepository) {
        this.voituresRepository = voituresRepository;
    }

    /**
     * POST  /voitures : Create a new voitures.
     *
     * @param voitures the voitures to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voitures, or with status 400 (Bad Request) if the voitures has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/voitures")
    @Timed
    public ResponseEntity<Voitures> createVoitures(@Valid @RequestBody Voitures voitures) throws URISyntaxException {
        log.debug("REST request to save Voitures : {}", voitures);
        if (voitures.getId() != null) {
            throw new BadRequestAlertException("A new voitures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Voitures result = voituresRepository.save(voitures);
        return ResponseEntity.created(new URI("/api/voitures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /voitures : Updates an existing voitures.
     *
     * @param voitures the voitures to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voitures,
     * or with status 400 (Bad Request) if the voitures is not valid,
     * or with status 500 (Internal Server Error) if the voitures couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/voitures")
    @Timed
    public ResponseEntity<Voitures> updateVoitures(@Valid @RequestBody Voitures voitures) throws URISyntaxException {
        log.debug("REST request to update Voitures : {}", voitures);
        if (voitures.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Voitures result = voituresRepository.save(voitures);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, voitures.getId().toString()))
            .body(result);
    }

    /**
     * GET  /voitures : get all the voitures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of voitures in body
     */
    @GetMapping("/voitures")
    @Timed
    public ResponseEntity<List<Voitures>> getAllVoitures(Pageable pageable) {
        log.debug("REST request to get a page of Voitures");
        Page<Voitures> page = voituresRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/voitures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /voitures/:id : get the "id" voitures.
     *
     * @param id the id of the voitures to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voitures, or with status 404 (Not Found)
     */
    @GetMapping("/voitures/{id}")
    @Timed
    public ResponseEntity<Voitures> getVoitures(@PathVariable Long id) {
        log.debug("REST request to get Voitures : {}", id);
        Optional<Voitures> voitures = voituresRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(voitures);
    }

    /**
     * DELETE  /voitures/:id : delete the "id" voitures.
     *
     * @param id the id of the voitures to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/voitures/{id}")
    @Timed
    public ResponseEntity<Void> deleteVoitures(@PathVariable Long id) {
        log.debug("REST request to delete Voitures : {}", id);

        voituresRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     *
     *
     * @param id the client id
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("clients/{id}/voitures")
    public ResponseEntity<List<Voitures>> getClientsVoitures(@PathVariable Long id, Pageable pageable){
        log.debug("Try to get clients voitures");

        Page<Voitures> page = voituresRepository.getAllByDevicesClientsId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client/{id}/voitures/");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
