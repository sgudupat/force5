package com.force.five.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the PaySheets entity.
 */
public class PaySheetsDTO implements Serializable {

    private Long id;

    private Integer regularDays;

    private Integer overtime;

    private Long assignmentsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaySheetsDTO paySheetsDTO = (PaySheetsDTO) o;

        if ( ! Objects.equals(id, paySheetsDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PaySheetsDTO{" +
            "id=" + id +
            '}';
    }
}
