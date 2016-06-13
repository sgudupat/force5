package com.force.five.app.service.impl;

import com.force.five.app.service.AssignmentsService;
import com.force.five.app.domain.Assignments;
import com.force.five.app.repository.AssignmentsRepository;
import com.force.five.app.web.rest.dto.AssignmentsDTO;
import com.force.five.app.web.rest.mapper.AssignmentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Assignments.
 */
@Service
@Transactional
public class AssignmentsServiceImpl implements AssignmentsService{

    private final Logger log = LoggerFactory.getLogger(AssignmentsServiceImpl.class);
    
    @Inject
    private AssignmentsRepository assignmentsRepository;
    
    @Inject
    private AssignmentsMapper assignmentsMapper;
    
    /**
     * Save a assignments.
     * @return the persisted entity
     */
    public AssignmentsDTO save(AssignmentsDTO assignmentsDTO) {
        log.debug("Request to save Assignments : {}", assignmentsDTO);
        Assignments assignments = assignmentsMapper.assignmentsDTOToAssignments(assignmentsDTO);
        assignments = assignmentsRepository.save(assignments);
        AssignmentsDTO result = assignmentsMapper.assignmentsToAssignmentsDTO(assignments);
        return result;
    }

    /**
     *  get all the assignmentss.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<AssignmentsDTO> findAll() {
        log.debug("Request to get all Assignmentss");
        List<AssignmentsDTO> result = assignmentsRepository.findAll().stream()
            .map(assignmentsMapper::assignmentsToAssignmentsDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one assignments by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AssignmentsDTO findOne(Long id) {
        log.debug("Request to get Assignments : {}", id);
        Assignments assignments = assignmentsRepository.findOne(id);
        AssignmentsDTO assignmentsDTO = assignmentsMapper.assignmentsToAssignmentsDTO(assignments);
        return assignmentsDTO;
    }

    /**
     *  delete the  assignments by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Assignments : {}", id);
        assignmentsRepository.delete(id);
    }
}
