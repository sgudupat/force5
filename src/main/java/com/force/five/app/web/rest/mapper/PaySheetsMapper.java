package com.force.five.app.web.rest.mapper;

import com.force.five.app.domain.*;
import com.force.five.app.web.rest.dto.PaySheetsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PaySheets and its DTO PaySheetsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaySheetsMapper {

    @Mapping(source = "assignments.id", target = "assignmentsId")
    @Mapping(source = "assignments.client.name", target = "clientName")
    @Mapping(source = "assignments.employee.name", target = "employeeName")
    PaySheetsDTO paySheetsToPaySheetsDTO(PaySheets paySheets);

    @Mapping(source = "assignmentsId", target = "assignments")
    PaySheets paySheetsDTOToPaySheets(PaySheetsDTO paySheetsDTO);

    default Assignments assignmentsFromId(Long id) {
        if (id == null) {
            return null;
        }
        Assignments assignments = new Assignments();
        assignments.setId(id);
        return assignments;
    }
}
