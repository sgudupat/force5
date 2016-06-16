package com.force.five.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the TripleSet entity.
 */
public class TripleSetDTO implements Serializable {

    private Long id;

    private String control;


    private String parent;


    private String child;


    private String config;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }
    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TripleSetDTO tripleSetDTO = (TripleSetDTO) o;

        if ( ! Objects.equals(id, tripleSetDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TripleSetDTO{" +
            "id=" + id +
            ", control='" + control + "'" +
            ", parent='" + parent + "'" +
            ", child='" + child + "'" +
            ", config='" + config + "'" +
            '}';
    }
}
