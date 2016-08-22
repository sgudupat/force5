package com.force.five.app.repository;

import com.force.five.app.domain.PaySheets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the PaySheets entity.
 */
public interface PaySheetsRepository extends JpaRepository<PaySheets, Long> {

    @Query(nativeQuery = true, value =
        "  select ps.* " +
            "  from client c " +
            "      ,assignments a " +
            "      ,pay_sheets ps " +
            "      ,employee e " +
            "  where c.id = ?1 " +
            "    and a.client_id = c.id " +
            "    and a.employee_id = e.id " +
            "    and ps.month = ?2 " +
            "    and ps.year = ?3 " +
            "    and ps.assignments_id = a.id; ")
    List<PaySheets> fetchSalarySheets(Long clientId, String month, String year);
}
