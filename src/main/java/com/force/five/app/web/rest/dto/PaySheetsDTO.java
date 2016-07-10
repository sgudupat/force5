package com.force.five.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the PaySheets entity.
 */
public class PaySheetsDTO implements Serializable {

    private Long id;

    private Integer regularDays;

    private Integer overtime;

    private Long assignmentsId;

    private String clientName;

    private String employeeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaySheetsDTO paySheetsDTO = (PaySheetsDTO) o;

        if (!Objects.equals(id, paySheetsDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Integer getRegularDays() {
        return regularDays;
    }

    public void setRegularDays(Integer regularDays) {
        this.regularDays = regularDays;
    }

    public Integer getOvertime() {
        return overtime;
    }

    public void setOvertime(Integer overtime) {
        this.overtime = overtime;
    }

    public Long getAssignmentsId() {
        return assignmentsId;
    }

    public void setAssignmentsId(Long assignmentsId) {
        this.assignmentsId = assignmentsId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "PaySheetsDTO{" +
            "id=" + id +
            ", regularDays=" + regularDays +
            ", overtime=" + overtime +
            ", assignmentsId=" + assignmentsId +
            ", clientName='" + clientName + '\'' +
            ", employeeName='" + employeeName + '\'' +
            '}';
    }
}
