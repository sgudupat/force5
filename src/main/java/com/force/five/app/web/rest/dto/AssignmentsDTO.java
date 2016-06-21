package com.force.five.app.web.rest.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Assignments entity.
 */
public class AssignmentsDTO implements Serializable {

    private Long id;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private Long employeeId;

    private Long clientId;
    
    private String employeeName;
    
    private String clientName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }
    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssignmentsDTO assignmentsDTO = (AssignmentsDTO) o;

        if ( ! Objects.equals(id, assignmentsDTO.id)) return false;

        return true;
    }

    public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

	@Override
	public String toString() {
		return "AssignmentsDTO [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", employeeId="
				+ employeeId + ", clientId=" + clientId + ", employeeName=" + employeeName + ", clientName="
				+ clientName + "]";
	}

    
}
