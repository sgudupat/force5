package com.force.five.app.web.rest.mapper;

import com.force.five.app.domain.*;
import com.force.five.app.web.rest.dto.PaySheetsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PaySheets and its DTO PaySheetsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaySheetsMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.name", target = "employeeName")
    PaySheetsDTO paySheetsToPaySheetsDTO(PaySheets paySheets);


    @Mapping(source = "employeeId", target = "employee")
    PaySheets paySheetsDTOToPaySheets(PaySheetsDTO paySheetsDTO);

    default Employee employeeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
