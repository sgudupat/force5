package com.force.five.app.service.impl;

import com.force.five.app.service.TripleSetService;
import com.force.five.app.domain.TripleSet;
import com.force.five.app.repository.TripleSetRepository;
import com.force.five.app.web.rest.dto.TripleSetDTO;
import com.force.five.app.web.rest.mapper.TripleSetMapper;
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
 * Service Implementation for managing TripleSet.
 */
@Service
@Transactional
public class TripleSetServiceImpl implements TripleSetService{

    private final Logger log = LoggerFactory.getLogger(TripleSetServiceImpl.class);
    
    @Inject
    private TripleSetRepository tripleSetRepository;
    
    @Inject
    private TripleSetMapper tripleSetMapper;
    
    /**
     * Save a tripleSet.
     * @return the persisted entity
     */
    public TripleSetDTO save(TripleSetDTO tripleSetDTO) {
        log.debug("Request to save TripleSet : {}", tripleSetDTO);
        TripleSet tripleSet = tripleSetMapper.tripleSetDTOToTripleSet(tripleSetDTO);
        tripleSet = tripleSetRepository.save(tripleSet);
        TripleSetDTO result = tripleSetMapper.tripleSetToTripleSetDTO(tripleSet);
        return result;
    }

    /**
     *  get all the tripleSets.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TripleSetDTO> findAll() {
        log.debug("Request to get all TripleSets");
        List<TripleSetDTO> result = tripleSetRepository.findAll().stream()
            .map(tripleSetMapper::tripleSetToTripleSetDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one tripleSet by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TripleSetDTO findOne(Long id) {
        log.debug("Request to get TripleSet : {}", id);
        TripleSet tripleSet = tripleSetRepository.findOne(id);
        TripleSetDTO tripleSetDTO = tripleSetMapper.tripleSetToTripleSetDTO(tripleSet);
        return tripleSetDTO;
    }

    /**
     *  delete the  tripleSet by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete TripleSet : {}", id);
        tripleSetRepository.delete(id);
    }
}
