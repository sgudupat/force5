package com.force.five.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.force.five.app.domain.Employee;
import com.force.five.app.domain.EmployeeSalarySheet;
import com.force.five.app.domain.PaySheets;
import com.force.five.app.service.PaySheetsService;
import com.force.five.app.web.rest.util.HeaderUtil;
import com.force.five.app.web.rest.dto.PaySheetsDTO;
import com.force.five.app.web.rest.mapper.PaySheetsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import javax.inject.Inject;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing PaySheets.
 */
@RestController
@RequestMapping("/api")
public class PaySheetsResource {

    private final Logger log = LoggerFactory.getLogger(PaySheetsResource.class);



    @Inject
    private PaySheetsService paySheetsService;

    @Inject
    private PaySheetsMapper paySheetsMapper;

    /**
     * POST /paySheetss -> Create a new paySheets.
     */
    @RequestMapping(value = "/paySheetss", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PaySheetsDTO> createPaySheets(@RequestBody PaySheetsDTO paySheetsDTO)
        throws URISyntaxException {
        log.debug("REST request to save PaySheets : {}", paySheetsDTO);
        if (paySheetsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(
                HeaderUtil.createFailureAlert("paySheets", "idexists", "A new paySheets cannot already have an ID"))
                .body(null);
        }
        PaySheetsDTO result = paySheetsService.save(paySheetsDTO);
        return ResponseEntity.created(new URI("/api/paySheetss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("paySheets", result.getId().toString())).body(result);
    }

    /**
     * PUT /paySheetss -> Updates an existing paySheets.
     */
    @RequestMapping(value = "/paySheetss", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PaySheetsDTO> updatePaySheets(@RequestBody PaySheetsDTO paySheetsDTO)
        throws URISyntaxException {
        log.debug("REST request to update PaySheets : {}", paySheetsDTO);
        if (paySheetsDTO.getId() == null) {
            return createPaySheets(paySheetsDTO);
        }
        PaySheetsDTO result = paySheetsService.save(paySheetsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("paySheets", paySheetsDTO.getId().toString())).body(result);
    }

    /**
     * GET /paySheetss -> get all the paySheetss.
     */
    @RequestMapping(value = "/paySheetss", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<PaySheetsDTO> getAllPaySheetss() {
        log.debug("REST request to get all PaySheetss");
        //generatePDF();
        generateSalarySheets("PSC", "MARCH", "2016");
        log.debug("Run after PDF generation");
        return paySheetsService.findAll();
    }

    /**
     * GET /paySheetss/:id -> get the "id" paySheets.
     */
    @RequestMapping(value = "/paySheetss/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PaySheetsDTO> getPaySheets(@PathVariable Long id) {
        log.debug("REST request to get PaySheets : {}", id);
        PaySheetsDTO paySheetsDTO = paySheetsService.findOne(id);
        return Optional.ofNullable(paySheetsDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /paySheetss/:id -> delete the "id" paySheets.
     */
    @RequestMapping(value = "/paySheetss/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePaySheets(@PathVariable Long id) {
        log.debug("REST request to delete PaySheets : {}", id);
        paySheetsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("paySheets", id.toString())).build();
    }

    private static void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {
        // create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        // set the cell alignment
        cell.setHorizontalAlignment(align);
        // set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        // in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        // add the call to the table
        table.addCell(cell);

    }

    private void generateSalarySheets(String clientName, String month, String year) {
        Font bfBold12 = new Font(FontFamily.COURIER, 8, Font.BOLD, new BaseColor(0, 0, 0));
        Document document = new Document();

        List<PaySheets> records = paySheetsService.getPaysheetRecords(clientName, month, year);
        System.out.println("records::" + records);

        try {
            String fileName = "salary_sheet_" + clientName.toLowerCase() + "_" + month.toLowerCase() + "_" + year + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            // create a paragraph
            Paragraph paragraph = new Paragraph();
            log.debug("error");
            // Report Header
            float[] columnWidths = {3f, 5f, 4f, 5f, 6f, 6f, 6f, 6f, 6f, 5f, 6f, 6f, 5f, 5f, 5f, 5f, 5f, 6f, 6f, 5f};
            log.debug("After Header Display");
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(90f);
            //Put Header information
            insertCell(table, clientName, Element.ALIGN_CENTER, 20, bfBold12);
            insertCell(table, "SALARY SHEET FOR THE MONTH OF " + month + " " + year, Element.ALIGN_CENTER, 20, bfBold12);
            insertCell(table, "SLNO", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Rank", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "RegDay", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "OT Days", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Name", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Basic", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Allow", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Total Wages", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Earned Basic", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "OT Wag", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Allow", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Gross Wages", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "P.F.", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "E.S.I.C", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Unif", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "IDCA", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Advance", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Total deduction", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Net salary", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "sign", Element.ALIGN_RIGHT, 1, bfBold12);
            log.debug("Table value display");

            //TODO: Fetch Data from Database
        /*    for (EmployeeSalarySheet record : records) {
                log.debug("Record:" + record);
            }*/
            /*int i = 1;
            for (EmployeeSalarySheet record : records) {
                BigDecimal regDays = record.getRegularDays();
                BigDecimal basic = record.getBasic();

                Integer oT = record.getOvertime();
                BigDecimal overTime = new BigDecimal(oT);
                BigDecimal allowance = record.getAllowances();
                BigDecimal value = new BigDecimal("30");
               // BigDecimal earnedBasic = null;

                BigDecimal totalWages = basic.add(allowance);
                BigDecimal   earnedBasic = basic.divide(value).multiply(regDays);
                BigDecimal otWags = totalWages.divide(value).multiply(overTime);
                BigDecimal earnedAllowances = allowance.divide(value).multiply(regDays);
                BigDecimal GW = basic.add(otWags).add(overTime);
                BigDecimal netSalary = basic.add(otWags).add(overTime);

                insertCell(table, String.valueOf(i++), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, record.getCategory(), Element.ALIGN_LEFT, 1, bfBold12);
                insertCell(table, String.valueOf(record.getRegularDays()), Element.ALIGN_LEFT, 1, bfBold12);
                insertCell(table, String.valueOf(record.getOvertime()), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, record.getName(), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(record.getBasic()), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(record.getAllowances()), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, totalWages.toString(), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table,earnedBasic.toString(), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table,  otWags.toString(), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, earnedAllowances.toString(), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table,  GW.toString(), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "P.F.", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "E.S.I.C", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "Unif", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "IDCA", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "Advance", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "Total deduction", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "Net salary", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "sign", Element.ALIGN_RIGHT, 1, bfBold12);
            }*/
            // add the PDF table to the paragraph
            paragraph.add(table);
            // add the paragraph to the document
            document.add(paragraph);
            PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Cell 3"));
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            document.close();
        } catch (Exception e) {

        }
    }

}
