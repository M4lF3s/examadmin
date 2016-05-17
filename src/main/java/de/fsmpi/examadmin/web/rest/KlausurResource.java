package de.fsmpi.examadmin.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.fsmpi.examadmin.domain.Klausur;
import de.fsmpi.examadmin.repository.KlausurRepository;
import de.fsmpi.examadmin.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Klausur.
 */
@RestController
@RequestMapping("/api")
public class KlausurResource {

    private final Logger log = LoggerFactory.getLogger(KlausurResource.class);
        
    @Inject
    private KlausurRepository klausurRepository;
    
    /**
     * POST  /klausurs : Create a new klausur.
     *
     * @param klausur the klausur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new klausur, or with status 400 (Bad Request) if the klausur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/klausurs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Klausur> createKlausur(@Valid @RequestBody Klausur klausur) throws URISyntaxException {
        log.debug("REST request to save Klausur : {}", klausur);
        if (klausur.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("klausur", "idexists", "A new klausur cannot already have an ID")).body(null);
        }
        Klausur result = klausurRepository.save(klausur);
        return ResponseEntity.created(new URI("/api/klausurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("klausur", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /klausurs : Updates an existing klausur.
     *
     * @param klausur the klausur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated klausur,
     * or with status 400 (Bad Request) if the klausur is not valid,
     * or with status 500 (Internal Server Error) if the klausur couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/klausurs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Klausur> updateKlausur(@Valid @RequestBody Klausur klausur) throws URISyntaxException {
        log.debug("REST request to update Klausur : {}", klausur);
        if (klausur.getId() == null) {
            return createKlausur(klausur);
        }
        Klausur result = klausurRepository.save(klausur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("klausur", klausur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /klausurs : get all the klausurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of klausurs in body
     */
    @RequestMapping(value = "/klausurs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Klausur> getAllKlausurs() {
        log.debug("REST request to get all Klausurs");
        List<Klausur> klausurs = klausurRepository.findAll();
        return klausurs;
    }

    /**
     * GET  /klausurs/:id : get the "id" klausur.
     *
     * @param id the id of the klausur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the klausur, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/klausurs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Klausur> getKlausur(@PathVariable Long id) {
        log.debug("REST request to get Klausur : {}", id);
        Klausur klausur = klausurRepository.findOne(id);
        return Optional.ofNullable(klausur)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /klausurs/:id : delete the "id" klausur.
     *
     * @param id the id of the klausur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/klausurs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKlausur(@PathVariable Long id) {
        log.debug("REST request to delete Klausur : {}", id);
        klausurRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("klausur", id.toString())).build();
    }

}
