package com.force.five.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.force.five.app.domain.SalarySheets;
import com.force.five.app.service.AssignmentsService;
import com.force.five.app.service.SalarySheetsService;
import com.force.five.app.web.rest.dto.AssignmentsDTO;
import com.force.five.app.web.rest.mapper.AssignmentsMapper;
import com.force.five.app.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Assignments.
 */
@RestController
@RequestMapping("/api")
public class SalarySheetsResource {

    private final Logger log = LoggerFactory.getLogger(SalarySheetsResource.class);

    @Inject
    private SalarySheetsService salarySheetsService;

    @RequestMapping(value = "/salarySheets/generate", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssignmentsDTO> generateSalarySheets(@RequestBody SalarySheets salarySheets) throws URISyntaxException {
        salarySheetsService.generateSalarySheets(salarySheets);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assignments", ""))
            .body(null);
    }

    @RequestMapping(value = "/salarySheets/fetch", method = RequestMethod.GET)
    @Timed
    public ResponseEntity<?> fetchSalarySheets() throws URISyntaxException,IOException {
        List<String> reports = salarySheetsService.fetchSalarySheets();
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assignments", ""))
            .body(reports);
    }

    @RequestMapping(value = "/billingSheets/fetch", method = RequestMethod.GET)
    @Timed
    public ResponseEntity<?> fetchBillingSheets() throws URISyntaxException,IOException {
        List<String> reports = salarySheetsService.fetchBillingSheets();
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assignments", ""))
            .body(reports);
    }

    @RequestMapping(value = "/invoiceSheets/fetch", method = RequestMethod.GET)
    @Timed
    public ResponseEntity<?> fetchInvoiceSheets() throws URISyntaxException,IOException {
        List<String> reports = salarySheetsService.fetchInvoiceSheets();
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assignments", ""))
            .body(reports);
    }



    @RequestMapping(value = "/billingReport/generate", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssignmentsDTO> generateBillingReport(@RequestBody SalarySheets SalarySheets) throws URISyntaxException {
        salarySheetsService.generateBillingReport(SalarySheets);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assignments", ""))
            .body(null);
    }

    @RequestMapping(value = "/invoiceReport/generate", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AssignmentsDTO> generateInvoiceReport(@RequestBody SalarySheets salarySheets) throws URISyntaxException {
        salarySheetsService.generateInvoiceReport(salarySheets);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("assignments", ""))
            .body(null);
    }
}
