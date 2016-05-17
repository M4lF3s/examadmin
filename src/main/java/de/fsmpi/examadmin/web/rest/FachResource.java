package de.fsmpi.examadmin.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.fsmpi.examadmin.domain.Fach;
import de.fsmpi.examadmin.repository.FachRepository;
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
 * REST controller for managing Fach.
 */
@RestController
@RequestMapping("/api")
public class FachResource {

    private final Logger log = LoggerFactory.getLogger(FachResource.class);
        
    @Inject
    private FachRepository fachRepository;
    
    /**
     * POST  /faches : Create a new fach.
     *
     * @param fach the fach to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fach, or with status 400 (Bad Request) if the fach has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/faches",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fach> createFach(@Valid @RequestBody Fach fach) throws URISyntaxException {
        log.debug("REST request to save Fach : {}", fach);
        if (fach.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("fach", "idexists", "A new fach cannot already have an ID")).body(null);
        }
        Fach result = fachRepository.save(fach);
        return ResponseEntity.created(new URI("/api/faches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fach", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /faches : Updates an existing fach.
     *
     * @param fach the fach to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fach,
     * or with status 400 (Bad Request) if the fach is not valid,
     * or with status 500 (Internal Server Error) if the fach couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/faches",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fach> updateFach(@Valid @RequestBody Fach fach) throws URISyntaxException {
        log.debug("REST request to update Fach : {}", fach);
        if (fach.getId() == null) {
            return createFach(fach);
        }
        Fach result = fachRepository.save(fach);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fach", fach.getId().toString()))
            .body(result);
    }

    /**
     * GET  /faches : get all the faches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of faches in body
     */
    @RequestMapping(value = "/faches",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Fach> getAllFaches() {
        log.debug("REST request to get all Faches");
        List<Fach> faches = fachRepository.findAll();
        return faches;
    }

    /**
     * GET  /faches/:id : get the "id" fach.
     *
     * @param id the id of the fach to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fach, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/faches/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fach> getFach(@PathVariable Long id) {
        log.debug("REST request to get Fach : {}", id);
        Fach fach = fachRepository.findOne(id);
        return Optional.ofNullable(fach)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /faches/:id : delete the "id" fach.
     *
     * @param id the id of the fach to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/faches/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFach(@PathVariable Long id) {
        log.debug("REST request to delete Fach : {}", id);
        fachRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fach", id.toString())).build();
    }

}
