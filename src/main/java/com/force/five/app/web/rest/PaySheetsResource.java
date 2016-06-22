package com.force.five.app.web.rest;

import com.codahale.metrics.annotation.Timed;
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

import javax.inject.Inject;

import java.io.FileOutputStream;
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
		generatePDF();
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

	private void generatePDF() {
		  Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
		  Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("newPDF2.pdf"));
			document.open();
			// create a paragraph
			Paragraph paragraph = new Paragraph("Manipulation");
			// specify column widths
			float[] columnWidths = { 1.5f, 2f, 5f, 2f };
			PdfPTable table = new PdfPTable(columnWidths);
			// set table width a percentage of the page width
			table.setWidthPercentage(90f);

			// insert column headings
			insertCell(table, "Order No", Element.ALIGN_RIGHT, 1, bfBold12);
			insertCell(table, "Account No", Element.ALIGN_LEFT, 1, bfBold12);
			insertCell(table, "Account Name", Element.ALIGN_LEFT, 1, bfBold12);
			insertCell(table, "Order Total", Element.ALIGN_RIGHT, 1, bfBold12);
			table.setHeaderRows(1);

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
}
