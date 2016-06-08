package com.facility.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Client entity.
 */
public class ClientDTO implements Serializable {

    private Long id;

    private String name;


    private String conatactPerson;


    private String address;


    private String city;


    private String state;


    private Long zipcode;


    private Boolean pf;


    private Boolean esic;


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
    public String getConatactPerson() {
        return conatactPerson;
    }

    public void setConatactPerson(String conatactPerson) {
        this.conatactPerson = conatactPerson;
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

        ClientDTO clientDTO = (ClientDTO) o;

        if ( ! Objects.equals(id, clientDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", conatactPerson='" + conatactPerson + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zipcode='" + zipcode + "'" +
            ", pf='" + pf + "'" +
            ", esic='" + esic + "'" +
            ", workHours='" + workHours + "'" +
            '}';
    }
}
