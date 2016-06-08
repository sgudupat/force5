package com.facility.app.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "basic", precision=10, scale=2)
    private BigDecimal basic;
    
    @Column(name = "allowances", precision=10, scale=2)
    private BigDecimal allowances;
    
    @Column(name = "start_date")
    private ZonedDateTime startDate;
    
    @Column(name = "end_date")
    private ZonedDateTime endDate;
    
    @Column(name = "mobile")
    private Long mobile;
    
    @Column(name = "mobile2")
    private Long mobile2;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "state")
    private String state;
    
    @Column(name = "zipcode")
    private Long zipcode;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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

    public Long getMobile() {
        return mobile;
    }
    
    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public Long getMobile2() {
        return mobile2;
    }
    
    public void setMobile2(Long mobile2) {
        this.mobile2 = mobile2;
    }

    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    public Long getZipcode() {
        return zipcode;
    }
    
    public void setZipcode(Long zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        if(employee.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", basic='" + basic + "'" +
            ", allowances='" + allowances + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", mobile='" + mobile + "'" +
            ", mobile2='" + mobile2 + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zipcode='" + zipcode + "'" +
            '}';
    }
}
