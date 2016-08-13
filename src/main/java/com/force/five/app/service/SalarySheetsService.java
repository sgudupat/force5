package com.force.five.app.service;

import com.force.five.app.domain.SalarySheets;

import java.io.IOException;
import java.util.List;

public interface SalarySheetsService {

    void generateSalarySheets(SalarySheets ss);

    void generateBillingReport(SalarySheets br);

    void generateInvoiceReport(SalarySheets ir);

    List<String> fetchSalarySheets() throws IOException;

    List<String> fetchBillingSheets() throws IOException;

    List<String> fetchInvoiceSheets() throws IOException;
}
