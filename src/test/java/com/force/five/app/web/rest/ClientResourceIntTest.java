package com.force.five.app.web.rest;

import com.force.five.app.Application;
import com.force.five.app.domain.Client;
import com.force.five.app.repository.ClientRepository;
import com.force.five.app.service.ClientService;
import com.force.five.app.web.rest.dto.ClientDTO;
import com.force.five.app.web.rest.mapper.ClientMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ClientResource REST controller.
 *
 * @see ClientResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClientResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_CONTACT_PERSON = "AAAAA";
    private static final String UPDATED_CONTACT_PERSON = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";
    private static final String DEFAULT_STATE = "AAAAA";
    private static final String UPDATED_STATE = "BBBBB";

    private static final Long DEFAULT_ZIPCODE = 1L;
    private static final Long UPDATED_ZIPCODE = 2L;

    private static final Boolean DEFAULT_PF = false;
    private static final Boolean UPDATED_PF = true;

    private static final Boolean DEFAULT_ESIC = false;
    private static final Boolean UPDATED_ESIC = true;

    private static final Boolean DEFAULT_VDA = false;
    private static final Boolean UPDATED_VDA = true;

    private static final Boolean DEFAULT_PARTIAL_TAX = false;
    private static final Boolean UPDATED_PARTIAL_TAX = true;

    private static final Integer DEFAULT_WORK_HOURS = 1;
    private static final Integer UPDATED_WORK_HOURS = 2;

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private ClientMapper clientMapper;

    @Inject
    private ClientService clientService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restClientMockMvc;

    private Client client;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientResource clientResource = new ClientResource();
        ReflectionTestUtils.setField(clientResource, "clientService", clientService);
        ReflectionTestUtils.setField(clientResource, "clientMapper", clientMapper);
        this.restClientMockMvc = MockMvcBuilders.standaloneSetup(clientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        client = new Client();
        client.setName(DEFAULT_NAME);
        client.setContactPerson(DEFAULT_CONTACT_PERSON);
        client.setAddress(DEFAULT_ADDRESS);
        client.setCity(DEFAULT_CITY);
        client.setState(DEFAULT_STATE);
        client.setZipcode(DEFAULT_ZIPCODE);
        client.setPf(DEFAULT_PF);
        client.setEsic(DEFAULT_ESIC);
        client.setVda(DEFAULT_VDA);
        client.setPartialTax(DEFAULT_PARTIAL_TAX);
        client.setWorkHours(DEFAULT_WORK_HOURS);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client
        ClientDTO clientDTO = clientMapper.clientToClientDTO(client);

        restClientMockMvc.perform(post("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
                .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clients = clientRepository.findAll();
        assertThat(clients).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clients.get(clients.size() - 1);
        assertThat(testClient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClient.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testClient.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testClient.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testClient.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testClient.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testClient.getPf()).isEqualTo(DEFAULT_PF);
        assertThat(testClient.getEsic()).isEqualTo(DEFAULT_ESIC);
        assertThat(testClient.getVda()).isEqualTo(DEFAULT_VDA);
        assertThat(testClient.getPartialTax()).isEqualTo(DEFAULT_PARTIAL_TAX);
        assertThat(testClient.getWorkHours()).isEqualTo(DEFAULT_WORK_HOURS);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clients
        restClientMockMvc.perform(get("/api/clients?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE.intValue())))
                .andExpect(jsonPath("$.[*].pf").value(hasItem(DEFAULT_PF.booleanValue())))
                .andExpect(jsonPath("$.[*].esic").value(hasItem(DEFAULT_ESIC.booleanValue())))
                .andExpect(jsonPath("$.[*].vda").value(hasItem(DEFAULT_VDA.booleanValue())))
                .andExpect(jsonPath("$.[*].partialTax").value(hasItem(DEFAULT_PARTIAL_TAX.booleanValue())))
                .andExpect(jsonPath("$.[*].workHours").value(hasItem(DEFAULT_WORK_HOURS)));
    }

    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.contactPerson").value(DEFAULT_CONTACT_PERSON.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE.intValue()))
            .andExpect(jsonPath("$.pf").value(DEFAULT_PF.booleanValue()))
            .andExpect(jsonPath("$.esic").value(DEFAULT_ESIC.booleanValue()))
            .andExpect(jsonPath("$.vda").value(DEFAULT_VDA.booleanValue()))
            .andExpect(jsonPath("$.partialTax").value(DEFAULT_PARTIAL_TAX.booleanValue()))
            .andExpect(jsonPath("$.workHours").value(DEFAULT_WORK_HOURS));
    }

    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

		int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        client.setName(UPDATED_NAME);
        client.setContactPerson(UPDATED_CONTACT_PERSON);
        client.setAddress(UPDATED_ADDRESS);
        client.setCity(UPDATED_CITY);
        client.setState(UPDATED_STATE);
        client.setZipcode(UPDATED_ZIPCODE);
        client.setPf(UPDATED_PF);
        client.setEsic(UPDATED_ESIC);
        client.setVda(UPDATED_VDA);
        client.setPartialTax(UPDATED_PARTIAL_TAX);
        client.setWorkHours(UPDATED_WORK_HOURS);
        ClientDTO clientDTO = clientMapper.clientToClientDTO(client);

        restClientMockMvc.perform(put("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
                .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clients = clientRepository.findAll();
        assertThat(clients).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clients.get(clients.size() - 1);
        assertThat(testClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClient.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testClient.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClient.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testClient.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testClient.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testClient.getPf()).isEqualTo(UPDATED_PF);
        assertThat(testClient.getEsic()).isEqualTo(UPDATED_ESIC);
        assertThat(testClient.getVda()).isEqualTo(UPDATED_VDA);
        assertThat(testClient.getPartialTax()).isEqualTo(UPDATED_PARTIAL_TAX);
        assertThat(testClient.getWorkHours()).isEqualTo(UPDATED_WORK_HOURS);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

		int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Get the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Client> clients = clientRepository.findAll();
        assertThat(clients).hasSize(databaseSizeBeforeDelete - 1);
    }
}
