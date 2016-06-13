package com.force.five.app.repository;

import com.force.five.app.domain.Assignments;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Assignments entity.
 */
public interface AssignmentsRepository extends JpaRepository<Assignments,Long> {

}
