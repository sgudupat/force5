package com.force.five.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.force.five.app.domain.TripleSet;
import com.force.five.app.service.TripleSetService;
import com.force.five.app.web.rest.util.HeaderUtil;
import com.force.five.app.web.rest.dto.TripleSetDTO;
import com.force.five.app.web.rest.mapper.TripleSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing TripleSet.
 */
@RestController
@RequestMapping("/api")
public class TripleSetResource {

    private final Logger log = LoggerFactory.getLogger(TripleSetResource.class);
        
    @Inject
    private TripleSetService tripleSetService;
    
    @Inject
    private TripleSetMapper tripleSetMapper;
    
    /**
     * POST  /tripleSets -> Create a new tripleSet.
     */
    @RequestMapping(value = "/tripleSets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TripleSetDTO> createTripleSet(@RequestBody TripleSetDTO tripleSetDTO) throws URISyntaxException {
        log.debug("REST request to save TripleSet : {}", tripleSetDTO);
        if (tripleSetDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tripleSet", "idexists", "A new tripleSet cannot already have an ID")).body(null);
        }
        TripleSetDTO result = tripleSetService.save(tripleSetDTO);
        return ResponseEntity.created(new URI("/api/tripleSets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tripleSet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tripleSets -> Updates an existing tripleSet.
     */
    @RequestMapping(value = "/tripleSets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TripleSetDTO> updateTripleSet(@RequestBody TripleSetDTO tripleSetDTO) throws URISyntaxException {
        log.debug("REST request to update TripleSet : {}", tripleSetDTO);
        if (tripleSetDTO.getId() == null) {
            return createTripleSet(tripleSetDTO);
        }
        TripleSetDTO result = tripleSetService.save(tripleSetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tripleSet", tripleSetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tripleSets -> get all the tripleSets.
     */
    @RequestMapping(value = "/tripleSets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<TripleSetDTO> getAllTripleSets() {
        log.debug("REST request to get all TripleSets");
        return tripleSetService.findAll();
            }

    /**
     * GET  /tripleSets/:id -> get the "id" tripleSet.
     */
    @RequestMapping(value = "/tripleSets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TripleSetDTO> getTripleSet(@PathVariable Long id) {
        log.debug("REST request to get TripleSet : {}", id);
        TripleSetDTO tripleSetDTO = tripleSetService.findOne(id);
        return Optional.ofNullable(tripleSetDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tripleSets/:id -> delete the "id" tripleSet.
     */
    @RequestMapping(value = "/tripleSets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTripleSet(@PathVariable Long id) {
        log.debug("REST request to delete TripleSet : {}", id);
        tripleSetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tripleSet", id.toString())).build();
    }
}
