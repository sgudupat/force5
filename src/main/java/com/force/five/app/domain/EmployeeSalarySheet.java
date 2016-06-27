package com.force.five.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmployeeSalarySheet implements Serializable {

    private String name;
    private String category;
    private BigDecimal basic;
    private BigDecimal allowances;
    private BigDecimal regularDays;
    private BigDecimal overtime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getBasic() {
        return basic;
    }

    public void setBasic(BigDecimal basic) {
        this.basic = basic;
    }

    public BigDecimal getAllowances() {
        return allowances;
    }

    public void setAllowances(BigDecimal allowances) {
        this.allowances = allowances;
    }

    public BigDecimal getRegularDays() {
        return regularDays;
    }

    public void setRegularDays(BigDecimal regularDays) {
        this.regularDays = regularDays;
    }

    public BigDecimal getOvertime() {
        return overtime;
    }

    public void setOvertime(BigDecimal overtime) {
        this.overtime = overtime;
    }

    @Override
    public String toString() {
        return "EmployeeSalarySheet{" +
            "name='" + name + '\'' +
            ", category='" + category + '\'' +
            ", basic=" + basic +
            ", allowances=" + allowances +
            ", regularDays=" + regularDays +
            ", overtime=" + overtime +
            '}';
    }
}
