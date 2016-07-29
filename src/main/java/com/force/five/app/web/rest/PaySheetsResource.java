package com.force.five.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.force.five.app.service.PaySheetsService;
import com.force.five.app.web.rest.dto.PaySheetsDTO;
import com.force.five.app.web.rest.mapper.PaySheetsMapper;
import com.force.five.app.web.rest.util.HeaderUtil;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
}
