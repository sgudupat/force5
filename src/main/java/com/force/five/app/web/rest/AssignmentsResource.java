package com.force.five.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.force.five.app.domain.Assignments;
import com.force.five.app.service.AssignmentsService;
import com.force.five.app.web.rest.util.HeaderUtil;
import com.force.five.app.web.rest.dto.AssignmentsDTO;
import com.force.five.app.web.rest.mapper.AssignmentsMapper;
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
 * REST controller for managing Assignments.
 */
@RestController
@RequestMapping("/api")
public class AssignmentsResource {

    private final Logger log = LoggerFactory.getLogger(AssignmentsResource.class);
        
    @Inject
    private AssignmentsService assignmentsService;
    
    @Inject
    private AssignmentsMapper assignmentsMapper;
    
    /**
     * POST  /assignmentss -> Create a new assignments.
     */
    @RequestMapping(value = "/assignmentss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssignmentsDTO> createAssignments(@RequestBody AssignmentsDTO assignmentsDTO) throws URISyntaxException {
        log.debug("REST request to save Assignments : {}", assignmentsDTO);
        if (assignmentsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("assignments", "idexists", "A new assignments cannot already have an ID")).body(null);
        }
        AssignmentsDTO result = assignmentsService.save(assignmentsDTO);
        return ResponseEntity.created(new URI("/api/assignmentss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("assignments", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assignmentss -> Updates an existing assignments.
     */
    @RequestMapping(value = "/assignmentss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssignmentsDTO> updateAssignments(@RequestBody AssignmentsDTO assignmentsDTO) throws URISyntaxException {
        log.debug("REST request to update Assignments : {}", assignmentsDTO);
        if (assignmentsDTO.getId() == null) {
            return createAssignments(assignmentsDTO);
        }
        AssignmentsDTO result = assignmentsService.save(assignmentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assignments", assignmentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assignmentss -> get all the assignmentss.
     */
    @RequestMapping(value = "/assignmentss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<AssignmentsDTO> getAllAssignmentss() {
        log.debug("REST request to get all Assignmentss");
        return assignmentsService.findAll();
            }

    /**
     * GET  /assignmentss/:id -> get the "id" assignments.
     */
    @RequestMapping(value = "/assignmentss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssignmentsDTO> getAssignments(@PathVariable Long id) {
        log.debug("REST request to get Assignments : {}", id);
        AssignmentsDTO assignmentsDTO = assignmentsService.findOne(id);
        return Optional.ofNullable(assignmentsDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /assignmentss/:id -> delete the "id" assignments.
     */
    @RequestMapping(value = "/assignmentss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAssignments(@PathVariable Long id) {
        log.debug("REST request to delete Assignments : {}", id);
        assignmentsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("assignments", id.toString())).build();
    }
}
