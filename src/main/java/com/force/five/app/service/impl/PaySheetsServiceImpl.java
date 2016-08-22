package com.force.five.app.service.impl;

import com.force.five.app.domain.PaySheets;
import com.force.five.app.repository.PaySheetsRepository;
import com.force.five.app.service.PaySheetsService;
import com.force.five.app.web.rest.dto.PaySheetsDTO;
import com.force.five.app.web.rest.mapper.PaySheetsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
        Integer days = paySheets.getDaysWorked() + paySheets.getWeeklyOff() + paySheets.getCompOff() + paySheets.getHolidays();
        paySheets.setRegularDays(days);
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
        List<PaySheetsDTO> result = new ArrayList<PaySheetsDTO>();
        List<PaySheets> sheets = paySheetsRepository.findAll();
        for (PaySheets sheet : sheets) {
            PaySheetsDTO dto = paySheetsMapper.paySheetsToPaySheetsDTO(sheet);
            dto.setClientName(sheet.getAssignments().getClient().getName());
            dto.setEmployeeName(sheet.getAssignments().getEmployee().getName());
            result.add(dto);
        }
        return result;
    }

    /**
     * get one paySheets by id.
     * \
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

    public List<PaySheets> getPaysheetRecords(Long clientId, String month, String year) {
        List<PaySheets> records = paySheetsRepository.fetchSalarySheets(clientId, month, year);
        return records;
    }
}
