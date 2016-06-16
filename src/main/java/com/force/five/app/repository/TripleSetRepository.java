package com.force.five.app.repository;

import com.force.five.app.domain.TripleSet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TripleSet entity.
 */
public interface TripleSetRepository extends JpaRepository<TripleSet,Long> {

}
