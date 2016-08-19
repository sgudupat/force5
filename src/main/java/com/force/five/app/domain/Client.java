package com.force.five.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "contact_person")
    private String contactPerson;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "state")
    private String state;
    
    @Column(name = "zipcode")
    private Long zipcode;
    
    @Column(name = "pf")
    private Boolean pf;
    
    @Column(name = "esic")
    private Boolean esic;
    
    @Column(name = "vda")
    private Boolean vda;
    
    @Column(name = "work_hours")
    private Integer workHours;
    
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

    public String getContactPerson() {
        return contactPerson;
    }
    
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
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

    public Boolean getPf() {
        return pf;
    }
    
    public void setPf(Boolean pf) {
        this.pf = pf;
    }

    public Boolean getEsic() {
        return esic;
    }
    
    public void setEsic(Boolean esic) {
        this.esic = esic;
    }

    public Boolean getVda() {
        return vda;
    }
    
    public void setVda(Boolean vda) {
        this.vda = vda;
    }

    public Integer getWorkHours() {
        return workHours;
    }
    
    public void setWorkHours(Integer workHours) {
        this.workHours = workHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        if(client.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", contactPerson='" + contactPerson + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zipcode='" + zipcode + "'" +
            ", pf='" + pf + "'" +
            ", esic='" + esic + "'" +
            ", vda='" + vda + "'" +
            ", workHours='" + workHours + "'" +
            '}';
    }
}
