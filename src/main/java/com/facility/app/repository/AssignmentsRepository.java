package com.facility.app.repository;

import com.facility.app.domain.Assignments;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Assignments entity.
 */
public interface AssignmentsRepository extends JpaRepository<Assignments,Long> {

}
