package com.force.five.app.web.rest.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the PaySheets entity.
 */
public class PaySheetsDTO implements Serializable {

    private Long id;

    private Integer regularDays;


    private Integer overtime;


    private ZonedDateTime startDate;


    private ZonedDateTime endDate;


    private Long employeeId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaySheetsDTO paySheetsDTO = (PaySheetsDTO) o;

        if ( ! Objects.equals(id, paySheetsDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PaySheetsDTO{" +
            "id=" + id +
            ", regularDays='" + regularDays + "'" +
            ", overtime='" + overtime + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
