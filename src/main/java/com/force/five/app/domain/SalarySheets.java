package com.force.five.app.domain;

public class SalarySheets {

    private Long clientId;

    private String month;

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
