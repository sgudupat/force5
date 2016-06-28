package com.force.five.app.domain;


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
            ", overtime='" + overtime + "'" +
            '}';
    }
}
