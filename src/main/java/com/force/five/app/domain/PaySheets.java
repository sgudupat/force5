package com.force.five.app.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PaySheets.
 */
@Entity
@Table(name = "pay_sheets")
public class PaySheets implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "regular_days")
    private Integer regularDays;
    
    @Column(name = "overtime")
    private Integer overtime;
    
    @Column(name = "start_date")
    private ZonedDateTime startDate;
    
    @Column(name = "end_date")
    private ZonedDateTime endDate;
    
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaySheets paySheets = (PaySheets) o;
        if(paySheets.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, paySheets.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PaySheets{" +
            "id=" + id +
            ", regularDays='" + regularDays + "'" +
            ", overtime='" + overtime + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
