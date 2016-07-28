package com.force.five.app.domain;


import com.force.five.app.service.util.ForceConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private Integer regularDays ;

    @Column(name = "days_worked")
    private Integer daysWorked;

    @Column(name = "weekly_off")
    private Integer weeklyOff;

    @Column(name = "comp_off")
    private Integer compOff;

    @Column(name = "holidays")
    private Integer holidays;

    @Column(name = "overtime")
    private Integer overtime;

    @ManyToOne
    @JoinColumn(name = "assignments_id")
    private Assignments assignments;

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

    public Integer getDaysWorked() {
        return daysWorked;
    }

    public void setDaysWorked(Integer daysWorked) {
        this.daysWorked = daysWorked;
    }

    public Integer getWeeklyOff() {
        return weeklyOff;
    }

    public void setWeeklyOff(Integer weeklyOff) {
        this.weeklyOff = weeklyOff;
    }

    public Integer getCompOff() {
        return compOff;
    }

    public void setCompOff(Integer compOff) {
        this.compOff = compOff;
    }

    public Integer getHolidays() {
        return holidays;
    }

    public void setHolidays(Integer holidays) {
        this.holidays = holidays;
    }

    public Integer getOvertime() {
        return overtime;
    }

    public void setOvertime(Integer overtime) {
        this.overtime = overtime;
    }

    public Assignments getAssignments() {
        return assignments;
    }

    public void setAssignments(Assignments assignments) {
        this.assignments = assignments;
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
            ", daysWorked='" + daysWorked + "'" +
            ", weeklyOff='" + weeklyOff + "'" +
            ", compOff='" + compOff + "'" +
            ", holidays='" + holidays + "'" +
            ", overtime='" + overtime + "'" +
            '}';
    }

    //Calculated columns
    public BigDecimal getTotalWages() {
        BigDecimal basic = this.getAssignments().getEmployee().getBasic();
        BigDecimal allowance = this.getAssignments().getEmployee().getAllowances();
        return basic.add(allowance);

    }

    public BigDecimal getEarnedBasic() {
        BigDecimal basic = this.getAssignments().getEmployee().getBasic();
        return basic.divide(ForceConstants.TOTAL_DAYS, 2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(this.regularDays)).setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getOTWages() {
        return getTotalWages().divide(ForceConstants.TOTAL_DAYS, 2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(this.overtime)).setScale(2, RoundingMode.HALF_EVEN);
    }
    public BigDecimal getEarnedAllowances() {
        return getAssignments().getEmployee().getAllowances().divide(ForceConstants.TOTAL_DAYS, 2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(this.regularDays)).setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getGrossWages() {
        return getEarnedBasic().add(getOTWages()).add(getEarnedAllowances());
    }

    public BigDecimal getPF() {
        return getEarnedBasic().multiply(ForceConstants.PF_CAL).setScale(2, RoundingMode.HALF_EVEN);
    }
    public BigDecimal getESIC() {
        return getEarnedBasic().multiply(ForceConstants.ESIC).setScale(2, RoundingMode.HALF_EVEN);
    }
    public BigDecimal getTotalDedu() {
        return getPF().add(getESIC());
    }

    public BigDecimal getNetSalary() {
        return getGrossWages().subtract(getTotalDedu());
    }

}
