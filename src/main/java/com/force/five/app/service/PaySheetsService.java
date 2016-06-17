package com.force.five.app.service;

import com.force.five.app.domain.PaySheets;
import com.force.five.app.web.rest.dto.PaySheetsDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing PaySheets.
 */
public interface PaySheetsService {

    /**
     * Save a paySheets.
     * @return the persisted entity
     */
    public PaySheetsDTO save(PaySheetsDTO paySheetsDTO);

    /**
     *  get all the paySheetss.
     *  @return the list of entities
     */
    public List<PaySheetsDTO> findAll();

    /**
     *  get the "id" paySheets.
     *  @return the entity
     */
    public PaySheetsDTO findOne(Long id);

    /**
     *  delete the "id" paySheets.
     */
    public void delete(Long id);
}
