package com.facility.app.service.impl;

import com.facility.app.service.ClientService;
import com.facility.app.domain.Client;
import com.facility.app.repository.ClientRepository;
import com.facility.app.web.rest.dto.ClientDTO;
import com.facility.app.web.rest.mapper.ClientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Client.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService{

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    
    @Inject
    private ClientRepository clientRepository;
    
    @Inject
    private ClientMapper clientMapper;
    
    /**
     * Save a client.
     * @return the persisted entity
     */
    public ClientDTO save(ClientDTO clientDTO) {
        log.debug("Request to save Client : {}", clientDTO);
        Client client = clientMapper.clientDTOToClient(clientDTO);
        client = clientRepository.save(client);
        ClientDTO result = clientMapper.clientToClientDTO(client);
        return result;
    }

    /**
     *  get all the clients.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ClientDTO> findAll() {
        log.debug("Request to get all Clients");
        List<ClientDTO> result = clientRepository.findAll().stream()
            .map(clientMapper::clientToClientDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  get one client by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ClientDTO findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        Client client = clientRepository.findOne(id);
        ClientDTO clientDTO = clientMapper.clientToClientDTO(client);
        return clientDTO;
    }

    /**
     *  delete the  client by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.delete(id);
    }
}
