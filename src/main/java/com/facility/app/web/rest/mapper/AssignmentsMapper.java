package com.facility.app.web.rest.mapper;

import com.facility.app.domain.*;
import com.facility.app.web.rest.dto.AssignmentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Assignments and its DTO AssignmentsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssignmentsMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "client.id", target = "clientId")
    AssignmentsDTO assignmentsToAssignmentsDTO(Assignments assignments);

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "clientId", target = "client")
    Assignments assignmentsDTOToAssignments(AssignmentsDTO assignmentsDTO);

    default Employee employeeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }

    default Client clientFromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
