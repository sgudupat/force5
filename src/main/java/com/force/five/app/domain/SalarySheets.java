package com.force.five.app.domain;

import javax.persistence.*;
import java.io.Serializable;
/**
 * A Assignments.
 */
@Entity
@Table(name = "salarySheets")
public class SalarySheets implements Serializable {

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "month")
    private String month;

    @Column(name = "year")
    private String year;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "SalarySheets{" +
            "clientId=" + clientId +
            ", month='" + month + '\'' +
            ", year='" + year + '\'' +
            '}';
    }
}
