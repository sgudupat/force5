package com.force.five.app.repository;

import com.force.five.app.domain.EmployeeSalarySheet;
import com.force.five.app.domain.PaySheets;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PaySheets entity.
 */
public interface PaySheetsRepository extends JpaRepository<PaySheets, Long> {
    @Query(nativeQuery = true, value =
        "select ps.regular_days, ps.overtime , e.name, e.category, e.basic, e.allowances " +
            "  from client c " +
            "      ,assignments a " +
            "      ,pay_sheets ps " +
            "      ,employee e " +
            "where c.name = ?1 " +
            "  and a.client_id = c.id " +
            "  and a.employee_id = ps.employee_id " +
            "  and month(ps.start_date) = ?2 " +
            "  and year(ps.start_date) = ?3 " +
            "  and ps.employee_id = e.id;")
    List<EmployeeSalarySheet> fetchSalarySheets(String clientName, Integer month, String year);
}
