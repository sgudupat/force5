package com.force.five.app.repository;

import com.force.five.app.domain.PaySheets;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PaySheets entity.
 */
public interface PaySheetsRepository extends JpaRepository<PaySheets,Long> {

}
