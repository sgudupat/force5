package com.force.five.app.web.rest.mapper;

import com.force.five.app.domain.*;
import com.force.five.app.web.rest.dto.TripleSetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TripleSet and its DTO TripleSetDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TripleSetMapper {

    TripleSetDTO tripleSetToTripleSetDTO(TripleSet tripleSet);

    TripleSet tripleSetDTOToTripleSet(TripleSetDTO tripleSetDTO);
}
