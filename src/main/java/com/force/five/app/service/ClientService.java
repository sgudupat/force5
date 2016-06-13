package com.force.five.app.service;

import com.force.five.app.domain.Client;
import com.force.five.app.web.rest.dto.ClientDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Client.
 */
public interface ClientService {

    /**
     * Save a client.
     * @return the persisted entity
     */
    public ClientDTO save(ClientDTO clientDTO);

    /**
     *  get all the clients.
     *  @return the list of entities
     */
    public List<ClientDTO> findAll();

    /**
     *  get the "id" client.
     *  @return the entity
     */
    public ClientDTO findOne(Long id);

    /**
     *  delete the "id" client.
     */
    public void delete(Long id);
}
