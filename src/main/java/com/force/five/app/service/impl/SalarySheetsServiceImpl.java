package com.force.five.app.service.impl;

import com.force.five.app.domain.Client;
import com.force.five.app.domain.PaySheets;
import com.force.five.app.domain.SalarySheets;
import com.force.five.app.service.PaySheetsService;
import com.force.five.app.service.SalarySheetsService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SalarySheetsServiceImpl implements SalarySheetsService {

    private final Logger log = LoggerFactory.getLogger(SalarySheetsServiceImpl.class);

    @Inject
    private PaySheetsService paySheetsService;

    @Override
    public void generateSalarySheets(SalarySheets ss) {
        log.debug("This is the Salary Sheet call");
        log.debug("Data:" + ss.toString());
        generateSalarySheets(ss.getClientId(), ss.getMonth(), ss.getYear());
    }

    public void generateBillingReport(SalarySheets ss) {
        log.debug("This is the Billing Report call");
        log.debug("Data:" + ss.toString());
        generateBillingReport(ss.getClientId(), ss.getMonth(), ss.getYear());
    }

    public void generateInvoiceReport(SalarySheets ss) {
        log.debug("This is the Invoice  call");
        log.debug("Data:" + ss.toString());
        generateInvoiceReport(ss.getClientId(), ss.getMonth(), ss.getYear());
    }

    private void generateSalarySheets(Long clientId, String month, String year) {
        Font bfBold12 = new Font(Font.FontFamily.COURIER, 8, Font.BOLD, new BaseColor(0, 0, 0));
        Document document = new Document();

        List<PaySheets> records = paySheetsService.getPaysheetRecords(clientId, month, year);
        System.out.println("records::" + records);
        Client client = new Client();
        for (PaySheets record : records) {
            client = record.getAssignments().getClient();
            break;
        }
        try {
            String fileName = "salary_sheet_" + clientId + "_" + month.toLowerCase() + "_" + year + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            // create a paragraph
            Paragraph paragraph = new Paragraph();
            // Report Header
            float[] columnWidths = {3f, 5f, 4f, 5f, 6f, 6f, 6f, 6f, 6f, 5f, 6f, 6f, 5f, 5f, 5f, 5f, 5f, 6f, 6f, 5f};
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the  page width
            table.setWidthPercentage(90f);
            //Put Header information
            insertCell(table, client.getName(), Element.ALIGN_CENTER, 20, bfBold12);
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

            //elements for total
            BigDecimal grandTotalDays = BigDecimal.ZERO;
            BigDecimal GrandTotalOT = BigDecimal.ZERO;
            BigDecimal GrandTotalbasic = BigDecimal.ZERO;
            BigDecimal GrandTotalAllow = BigDecimal.ZERO;
            BigDecimal GrandToatlWages = BigDecimal.ZERO;
            BigDecimal GrandTotalEarnedBasic = BigDecimal.ZERO;
            BigDecimal GrandtotalOT = BigDecimal.ZERO;
            BigDecimal GrandToatlEarnedAllow = BigDecimal.ZERO;
            BigDecimal GrandToatlGW = BigDecimal.ZERO;
            BigDecimal GrandTotalPF = BigDecimal.ZERO;
            BigDecimal GrandTotalESIC = BigDecimal.ZERO;
            BigDecimal GrandTotalTolDedu = BigDecimal.ZERO;
            BigDecimal GrandTotalNatSal = BigDecimal.ZERO;

            int i = 1;
            for (PaySheets record : records) {
                BigDecimal regDays = new BigDecimal(record.getRegularDays());
                BigDecimal basic = record.getAssignments().getEmployee().getBasic();
                BigDecimal overTime = new BigDecimal(record.getOvertime());
                BigDecimal allowance = record.getAssignments().getEmployee().getAllowances();

                // BigDecimal earnedBasic = null;

                //add to total
                grandTotalDays = grandTotalDays.add(regDays);
                GrandTotalOT = GrandTotalOT.add(overTime);
                GrandTotalbasic = GrandTotalbasic.add(basic);
                GrandTotalAllow = GrandTotalAllow.add(allowance);

                //Calculations
                //basic + overtime
                BigDecimal totalWages = record.getTotalWages();
                GrandToatlWages = GrandToatlWages.add(totalWages);

                // basic/30 * regDays
                BigDecimal earnedBasic = record.getEarnedBasic();
                GrandTotalEarnedBasic = GrandTotalEarnedBasic.add(earnedBasic);

                // totalWages/30 * overTime
                BigDecimal otWags = record.getOTWages();
                GrandtotalOT = GrandtotalOT.add(otWags);

                // Allow/30*RegDays
                BigDecimal earnedAllowances = record.getEarnedAllowances();
                GrandToatlEarnedAllow = GrandToatlEarnedAllow.add(earnedAllowances);

                //GW=EarnedBasic+OT+Allow
                BigDecimal GrossWages = record.getGrossWages();
                GrandToatlGW = GrandToatlGW.add(GrossWages);

                //P.F.=(EarnedBasic*12%)
                BigDecimal pf = record.getPF();
                GrandTotalPF = GrandTotalPF.add(pf);

                //E.S.I.C =(EarnedBasic*1.75*)
                BigDecimal esic = record.getESIC();
                GrandTotalESIC = GrandTotalESIC.add(esic);

                //TotalDedu=PF+ESIC
                BigDecimal TotalDedu = record.getTotalDedu();
                GrandTotalTolDedu = GrandTotalTolDedu.add(TotalDedu);

                //NetSalary= GrossWages-TotalDedu
                BigDecimal NetSal = record.getNetSalary();
                GrandTotalNatSal = GrandTotalNatSal.add(NetSal);

                insertCell(table, String.valueOf(i++), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, record.getAssignments().getEmployee().getCategory(), Element.ALIGN_LEFT, 1, bfBold12);
                insertCell(table, String.valueOf(regDays), Element.ALIGN_LEFT, 1, bfBold12);
                insertCell(table, String.valueOf(overTime), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, record.getAssignments().getEmployee().getName(), Element.ALIGN_RIGHT, 1, bfBold12);

                insertCell(table, String.valueOf(basic), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(allowance), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(totalWages), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(earnedBasic), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(otWags), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(earnedAllowances), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(GrossWages), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(pf), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(esic), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(TotalDedu), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(NetSal), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "", Element.ALIGN_RIGHT, 1, bfBold12);
            }

            //total or footer

            insertCell(table, "TOTAL", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, String.valueOf(grandTotalDays), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, String.valueOf(GrandTotalOT), Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, String.valueOf(GrandTotalbasic), Element.ALIGN_RIGHT, 1, bfBold12);

            insertCell(table, String.valueOf(GrandTotalAllow), Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, String.valueOf(GrandToatlWages), Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, String.valueOf(GrandTotalEarnedBasic), Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, String.valueOf(GrandtotalOT), Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, String.valueOf(GrandToatlEarnedAllow), Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, String.valueOf(GrandToatlGW), Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, String.valueOf(GrandTotalPF), Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, String.valueOf(GrandTotalESIC), Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, String.valueOf(GrandTotalTolDedu), Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, String.valueOf(GrandTotalNatSal), Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "", Element.ALIGN_RIGHT, 1, bfBold12);
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

    private void generateBillingReport(Long clientId, String month, String year) {

        Font bfBold12 = new Font(Font.FontFamily.COURIER, 8, Font.BOLD, new BaseColor(0, 0, 0));
        Document document = new Document();

        List<PaySheets> records = paySheetsService.getPaysheetRecords(clientId, month, year);
        System.out.println("records::" + records);
        Client client = new Client();
        for (PaySheets record : records) {
            client = record.getAssignments().getClient();
            break;
        }


        try {
            String fileName = "billing_summary_sheet_" + clientId + "_" + month.toLowerCase() + "_" + year + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            // create a paragraph
            Paragraph paragraph = new Paragraph();
            // Report Header
            float[] columnWidths = {3f, 5f, 5f, 5f, 5f, 6f, 6f, 6f, 6f, 5f, 5f, 8f};
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the  page width
            table.setWidthPercentage(90f);
            //Put Header information
            insertCell(table, client.getName(), Element.ALIGN_CENTER, 20, bfBold12);
            //TODO: Rename title
            insertCell(table, "BILLING SUMMARY FOR THE MONTH OF " + month + " " + year, Element.ALIGN_CENTER, 20, bfBold12);
            insertCell(table, "SLNO", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Design", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Name", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "No of Days worked", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Weekly Off", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Comp Off", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "OT No of days", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Holidays", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Total", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Cost", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "Per day cost", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(table, "GrandTotal", Element.ALIGN_RIGHT, 1, bfBold12);

            log.debug("Table value display");
            int i = 1;

            for (PaySheets record : records) {
                BigDecimal DaysWorked = new BigDecimal(record.getDaysWorked());
                BigDecimal WeeklyOff = new BigDecimal(record.getWeeklyOff());
                BigDecimal CompOff = new BigDecimal(record.getCompOff());
                BigDecimal overtime = new BigDecimal(record.getOvertime());
                BigDecimal Holidays = new BigDecimal(record.getHolidays());
                BigDecimal cost = record.getAssignments().getCost();

                //Calculations
                //Toatl= No of days worked + Weekly off + Comp off + OT No of days + Holidays
                BigDecimal Total = record.getTotal();
                // Per day Cost = Cost/29
                BigDecimal PerDayCost = record.getCostPerDay();
                //GrandTotal = PerdayCost * Total
                 BigDecimal GrandTotal = record.getGrandToatl();


                insertCell(table, String.valueOf(i++), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, "", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(DaysWorked), Element.ALIGN_LEFT, 1, bfBold12);
                insertCell(table, String.valueOf(WeeklyOff), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(CompOff), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(overtime), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(Holidays), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(Total), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(cost), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(PerDayCost), Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(table, String.valueOf(GrandTotal), Element.ALIGN_RIGHT, 1, bfBold12);
            }

            //total or footer
            // add the PDF table to the paragraph
            paragraph.add(table);
            // add the paragraph to the document
            document.add(paragraph);
            document.close();
        } catch (Exception e) {
        }
    }

    private void generateInvoiceReport(Long clientId, String month, String year) {

        Font bfBold12 = new Font(Font.FontFamily.COURIER, 8, Font.BOLD, new BaseColor(0, 0, 0));
        Document document = new Document();

        List<PaySheets> records = paySheetsService.getPaysheetRecords(clientId, month, year);
        System.out.println("records::" + records);
        Client client = new Client();
        for (PaySheets record : records) {
            client = record.getAssignments().getClient();
            break;
        }
        try {
            String fileName = "Invoice_summary_sheet_" + clientId + "_" + month.toLowerCase() + "_" + year + ".pdf";

            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            // create a paragraph
            Paragraph paragraph = new Paragraph();
            //Invoice Header
            // Report Header
            float[] invoiceHeaderCols = {45f, 45f};
            PdfPTable invoiceHeader = new PdfPTable(invoiceHeaderCols);
            // set table width a percentage of the  page width
            invoiceHeader.setWidthPercentage(90f);
            insertCell(invoiceHeader, "INVOICE NO:" + "F5PMSPL/2015-16/1744", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeader, "Dated:" + new Date(), Element.ALIGN_RIGHT, 1, bfBold12);

            insertCell(invoiceHeader, client.getName(), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeader, "", Element.ALIGN_RIGHT, 1, bfBold12);

            insertCell(invoiceHeader, client.getAddress(), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeader, "", Element.ALIGN_RIGHT, 1, bfBold12);

            insertCell(invoiceHeader, client.getCity(), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeader, "", Element.ALIGN_RIGHT, 1, bfBold12);

            insertCell(invoiceHeader, client.getState(), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeader, "", Element.ALIGN_RIGHT, 1, bfBold12);

            log.debug("Table value display");
            int i = 1;

            // add the PDF table to the paragraph
            paragraph.add(invoiceHeader);
            // add the paragraph to the document
            document.add(paragraph);


            //Second Table for Body

            Paragraph paragraphBody = new Paragraph();
            //Invoice Header
            // Report Header
            float[] invoiceHeadercolBody = {20f, 80f, 40f, 40f, 50f};
            PdfPTable invoiceHeaderBody = new PdfPTable(invoiceHeadercolBody);
            // set table width a percentage of the  page width
            invoiceHeader.setWidthPercentage(90f);
            insertCell(invoiceHeaderBody, "SL.NO", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "DESCRIPTION", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(invoiceHeaderBody, "NO.OF DUTIES", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "RATE", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(invoiceHeaderBody, "AMOUNT", Element.ALIGN_CENTER, 1, bfBold12);

            int j = 1;
            for (PaySheets record : records) {
                insertCell(invoiceHeaderBody, String.valueOf(j++), Element.ALIGN_LEFT, 1, bfBold12);
                insertCell(invoiceHeaderBody, "House Keeping  charges for the month of February 26th 2016 To March 25th 2016 ", Element.ALIGN_LEFT, 1, bfBold12);
                insertCell(invoiceHeaderBody, "in hrs/days", Element.ALIGN_RIGHT, 1, bfBold12);
                insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);
                log.debug("Table value display");
            }

            insertCell(invoiceHeaderBody, String.valueOf(j), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, " Charges For House Keeper", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);

            insertCell(invoiceHeaderBody, String.valueOf(j + 1), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, " Payment by cross order cheque only", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);

            insertCell(invoiceHeaderBody, String.valueOf(j + 2), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "Interest at 25%will be charged if the payment is not paid within 15 days of this bill", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);

            insertCell(invoiceHeaderBody, String.valueOf(j + 3), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "All disputes subject to Bangalore jurisidiction ,E.& O.E", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);


            insertCell(invoiceHeaderBody, String.valueOf(j + 4), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "Regist No U55101KA2007PTC043136/2007-2008.Date 15/6/2007", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);

            insertCell(invoiceHeaderBody, String.valueOf(j + 5), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, " E.S.I. C. NO. 53-22645-101", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);

            insertCell(invoiceHeaderBody, String.valueOf(j + 6), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, " P.F. NO. KN/WF/45011", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);

            insertCell(invoiceHeaderBody, String.valueOf(j + 7), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, " PAN NO: AABCF3440P", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);


            insertCell(invoiceHeaderBody, String.valueOf(j + 8), Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, " Service Tax No :AABCF3440PST001 We here by certify that our Regd. Cert.under the BS Act 1956 is in force on the date on which the sale of goods specified in this bill/cash memo" +
                "is made by us,and that the transaction of the sale covered in this bill/cash memo has been effected by us in the regular course of our business", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_RIGHT, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);
            insertCell(invoiceHeaderBody, "", Element.ALIGN_CENTER, 1, bfBold12);

            // add the PDF table to the paragraph
            paragraphBody.add(invoiceHeaderBody);
            // add the paragraph to the document
            document.add(paragraphBody);
            document.close();
        } catch (Exception e) {

        }
    }

    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {
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
}
