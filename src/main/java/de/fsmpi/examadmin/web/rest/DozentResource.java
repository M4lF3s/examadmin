package de.fsmpi.examadmin.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.fsmpi.examadmin.domain.Dozent;
import de.fsmpi.examadmin.repository.DozentRepository;
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
 * REST controller for managing Dozent.
 */
@RestController
@RequestMapping("/api")
public class DozentResource {

    private final Logger log = LoggerFactory.getLogger(DozentResource.class);
        
    @Inject
    private DozentRepository dozentRepository;
    
    /**
     * POST  /dozents : Create a new dozent.
     *
     * @param dozent the dozent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dozent, or with status 400 (Bad Request) if the dozent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dozents",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Dozent> createDozent(@Valid @RequestBody Dozent dozent) throws URISyntaxException {
        log.debug("REST request to save Dozent : {}", dozent);
        if (dozent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dozent", "idexists", "A new dozent cannot already have an ID")).body(null);
        }
        Dozent result = dozentRepository.save(dozent);
        return ResponseEntity.created(new URI("/api/dozents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dozent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dozents : Updates an existing dozent.
     *
     * @param dozent the dozent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dozent,
     * or with status 400 (Bad Request) if the dozent is not valid,
     * or with status 500 (Internal Server Error) if the dozent couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/dozents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Dozent> updateDozent(@Valid @RequestBody Dozent dozent) throws URISyntaxException {
        log.debug("REST request to update Dozent : {}", dozent);
        if (dozent.getId() == null) {
            return createDozent(dozent);
        }
        Dozent result = dozentRepository.save(dozent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dozent", dozent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dozents : get all the dozents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dozents in body
     */
    @RequestMapping(value = "/dozents",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Dozent> getAllDozents() {
        log.debug("REST request to get all Dozents");
        List<Dozent> dozents = dozentRepository.findAllWithEagerRelationships();
        return dozents;
    }

    /**
     * GET  /dozents/:id : get the "id" dozent.
     *
     * @param id the id of the dozent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dozent, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/dozents/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Dozent> getDozent(@PathVariable Long id) {
        log.debug("REST request to get Dozent : {}", id);
        Dozent dozent = dozentRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(dozent)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dozents/:id : delete the "id" dozent.
     *
     * @param id the id of the dozent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/dozents/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDozent(@PathVariable Long id) {
        log.debug("REST request to delete Dozent : {}", id);
        dozentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dozent", id.toString())).build();
    }

}
