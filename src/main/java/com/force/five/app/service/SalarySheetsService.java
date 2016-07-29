package com.force.five.app.service;

import com.force.five.app.domain.SalarySheets;

public interface SalarySheetsService {

    void generateSalarySheets(SalarySheets ss);

    void generateBillingReport(SalarySheets br);

    void generateInvoiceReport(SalarySheets ir);

}
