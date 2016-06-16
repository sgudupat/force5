package com.force.five.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TripleSet.
 */
@Entity
@Table(name = "triple_set")
public class TripleSet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "control")
    private String control;
    
    @Column(name = "parent")
    private String parent;
    
    @Column(name = "child")
    private String child;
    
    @Column(name = "config")
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
        TripleSet tripleSet = (TripleSet) o;
        if(tripleSet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tripleSet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TripleSet{" +
            "id=" + id +
            ", control='" + control + "'" +
            ", parent='" + parent + "'" +
            ", child='" + child + "'" +
            ", config='" + config + "'" +
            '}';
    }
}
