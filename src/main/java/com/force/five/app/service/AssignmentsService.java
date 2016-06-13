package com.force.five.app.service;

import com.force.five.app.domain.Assignments;
import com.force.five.app.web.rest.dto.AssignmentsDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Assignments.
 */
public interface AssignmentsService {

    /**
     * Save a assignments.
     * @return the persisted entity
     */
    public AssignmentsDTO save(AssignmentsDTO assignmentsDTO);

    /**
     *  get all the assignmentss.
     *  @return the list of entities
     */
    public List<AssignmentsDTO> findAll();

    /**
     *  get the "id" assignments.
     *  @return the entity
     */
    public AssignmentsDTO findOne(Long id);

    /**
     *  delete the "id" assignments.
     */
    public void delete(Long id);
}
