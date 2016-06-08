package com.facility.app.web.rest.mapper;

import com.facility.app.domain.*;
import com.facility.app.web.rest.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Employee and its DTO EmployeeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmployeeMapper {

    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);
}
