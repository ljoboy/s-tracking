package dev.eazylife.s_tracking.web.rest;

import com.codahale.metrics.annotation.Timed;
import dev.eazylife.s_tracking.domain.Clients;
import dev.eazylife.s_tracking.repository.ClientsRepository;
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
 * REST controller for managing Clients.
 */
@RestController
@RequestMapping("/api")
public class ClientsResource {

    private final Logger log = LoggerFactory.getLogger(ClientsResource.class);

    private static final String ENTITY_NAME = "clients";

    private final ClientsRepository clientsRepository;

    public ClientsResource(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    /**
     * POST  /clients : Create a new clients.
     *
     * @param clients the clients to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clients, or with status 400 (Bad Request) if the clients has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clients")
    @Timed
    public ResponseEntity<Clients> createClients(@Valid @RequestBody Clients clients) throws URISyntaxException {
        log.debug("REST request to save Clients : {}", clients);
        if (clients.getId() != null) {
            throw new BadRequestAlertException("A new clients cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Clients result = clientsRepository.save(clients);
        return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clients : Updates an existing clients.
     *
     * @param clients the clients to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clients,
     * or with status 400 (Bad Request) if the clients is not valid,
     * or with status 500 (Internal Server Error) if the clients couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clients")
    @Timed
    public ResponseEntity<Clients> updateClients(@Valid @RequestBody Clients clients) throws URISyntaxException {
        log.debug("REST request to update Clients : {}", clients);
        if (clients.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Clients result = clientsRepository.save(clients);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clients.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clients : get all the clients.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clients in body
     */
    @GetMapping("/clients")
    @Timed
    public ResponseEntity<List<Clients>> getAllClients(Pageable pageable) {
        log.debug("REST request to get a page of Clients");
        Page<Clients> page = clientsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clients/:id : get the "id" clients.
     *
     * @param id the id of the clients to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clients, or with status 404 (Not Found)
     */
    @GetMapping("/clients/{id}")
    @Timed
    public ResponseEntity<Clients> getClients(@PathVariable Long id) {
        log.debug("REST request to get Clients : {}", id);
        Optional<Clients> clients = clientsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(clients);
    }

    /**
     * DELETE  /clients/:id : delete the "id" clients.
     *
     * @param id the id of the clients to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clients/{id}")
    @Timed
    public ResponseEntity<Void> deleteClients(@PathVariable Long id) {
        log.debug("REST request to delete Clients : {}", id);

        clientsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /clients/:telephone/:password : get the "telephone and password" clients.
     *
     * @param telephone the client telephone
     * @param password the client password
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("clients/{telephone}/{password}")
    @Timed
    public ResponseEntity<Clients> getClientsByTelephonePassword(@PathVariable Long telephone, @PathVariable String password){
        log.debug("Tentative de connexion");

        Optional<Clients> clients = clientsRepository.findByTelephoneAndPassword(telephone, password);
        return ResponseUtil.wrapOrNotFound(clients);
    }
}
