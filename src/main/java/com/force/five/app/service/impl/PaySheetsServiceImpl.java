package com.force.five.app.service.impl;

import com.force.five.app.domain.EmployeeSalarySheet;
import com.force.five.app.service.PaySheetsService;
import com.force.five.app.domain.PaySheets;
import com.force.five.app.repository.PaySheetsRepository;
import com.force.five.app.web.rest.dto.PaySheetsDTO;
import com.force.five.app.web.rest.mapper.PaySheetsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PaySheets.
 */
@Service
@Transactional
public class PaySheetsServiceImpl implements PaySheetsService {

    private final Logger log = LoggerFactory.getLogger(PaySheetsServiceImpl.class);

    @Inject
    private PaySheetsRepository paySheetsRepository;

    @Inject
    private PaySheetsMapper paySheetsMapper;

    /**
     * Save a paySheets.
     *
     * @return the persisted entity
     */
    public PaySheetsDTO save(PaySheetsDTO paySheetsDTO) {
        log.debug("Request to save PaySheets : {}", paySheetsDTO);
        PaySheets paySheets = paySheetsMapper.paySheetsDTOToPaySheets(paySheetsDTO);
        paySheets = paySheetsRepository.save(paySheets);
        PaySheetsDTO result = paySheetsMapper.paySheetsToPaySheetsDTO(paySheets);
        return result;
    }

    /**
     * get all the paySheetss.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PaySheetsDTO> findAll() {
        log.debug("Request to get all PaySheetss");
        List<PaySheetsDTO> result = paySheetsRepository.findAll().stream()
            .map(paySheetsMapper::paySheetsToPaySheetsDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     * get one paySheets by id.
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PaySheetsDTO findOne(Long id) {
        log.debug("Request to get PaySheets : {}", id);
        PaySheets paySheets = paySheetsRepository.findOne(id);
        PaySheetsDTO paySheetsDTO = paySheetsMapper.paySheetsToPaySheetsDTO(paySheets);
        return paySheetsDTO;
    }

    /**
     * delete the  paySheets by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaySheets : {}", id);
        paySheetsRepository.delete(id);
    }

    public List<EmployeeSalarySheet> getPaysheetRecords(String clientName, String month, String year) {
        try {
            Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return paySheetsRepository.fetchSalarySheets(clientName, cal.get(Calendar.MONTH), year);
        } catch (ParseException pe) {
            log.error("Parse Exception:", pe);
            return  null;
        }
    }
}
