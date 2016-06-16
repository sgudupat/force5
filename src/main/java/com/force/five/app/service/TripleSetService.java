package com.force.five.app.service;

import com.force.five.app.domain.TripleSet;
import com.force.five.app.web.rest.dto.TripleSetDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing TripleSet.
 */
public interface TripleSetService {

    /**
     * Save a tripleSet.
     * @return the persisted entity
     */
    public TripleSetDTO save(TripleSetDTO tripleSetDTO);

    /**
     *  get all the tripleSets.
     *  @return the list of entities
     */
    public List<TripleSetDTO> findAll();

    /**
     *  get the "id" tripleSet.
     *  @return the entity
     */
    public TripleSetDTO findOne(Long id);

    /**
     *  delete the "id" tripleSet.
     */
    public void delete(Long id);
}
