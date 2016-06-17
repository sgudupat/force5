package com.force.five.app.web.rest;

import com.force.five.app.Application;
import com.force.five.app.domain.PaySheets;
import com.force.five.app.repository.PaySheetsRepository;
import com.force.five.app.service.PaySheetsService;
import com.force.five.app.web.rest.dto.PaySheetsDTO;
import com.force.five.app.web.rest.mapper.PaySheetsMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PaySheetsResource REST controller.
 *
 * @see PaySheetsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PaySheetsResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final Integer DEFAULT_REGULAR_DAYS = 1;
    private static final Integer UPDATED_REGULAR_DAYS = 2;

    private static final Integer DEFAULT_OVERTIME = 1;
    private static final Integer UPDATED_OVERTIME = 2;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.format(DEFAULT_END_DATE);

    @Inject
    private PaySheetsRepository paySheetsRepository;

    @Inject
    private PaySheetsMapper paySheetsMapper;

    @Inject
    private PaySheetsService paySheetsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPaySheetsMockMvc;

    private PaySheets paySheets;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PaySheetsResource paySheetsResource = new PaySheetsResource();
        ReflectionTestUtils.setField(paySheetsResource, "paySheetsService", paySheetsService);
        ReflectionTestUtils.setField(paySheetsResource, "paySheetsMapper", paySheetsMapper);
        this.restPaySheetsMockMvc = MockMvcBuilders.standaloneSetup(paySheetsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        paySheets = new PaySheets();
        paySheets.setRegularDays(DEFAULT_REGULAR_DAYS);
        paySheets.setOvertime(DEFAULT_OVERTIME);
        paySheets.setStartDate(DEFAULT_START_DATE);
        paySheets.setEndDate(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createPaySheets() throws Exception {
        int databaseSizeBeforeCreate = paySheetsRepository.findAll().size();

        // Create the PaySheets
        PaySheetsDTO paySheetsDTO = paySheetsMapper.paySheetsToPaySheetsDTO(paySheets);

        restPaySheetsMockMvc.perform(post("/api/paySheetss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(paySheetsDTO)))
                .andExpect(status().isCreated());

        // Validate the PaySheets in the database
        List<PaySheets> paySheetss = paySheetsRepository.findAll();
        assertThat(paySheetss).hasSize(databaseSizeBeforeCreate + 1);
        PaySheets testPaySheets = paySheetss.get(paySheetss.size() - 1);
        assertThat(testPaySheets.getRegularDays()).isEqualTo(DEFAULT_REGULAR_DAYS);
        assertThat(testPaySheets.getOvertime()).isEqualTo(DEFAULT_OVERTIME);
        assertThat(testPaySheets.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPaySheets.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllPaySheetss() throws Exception {
        // Initialize the database
        paySheetsRepository.saveAndFlush(paySheets);

        // Get all the paySheetss
        restPaySheetsMockMvc.perform(get("/api/paySheetss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(paySheets.getId().intValue())))
                .andExpect(jsonPath("$.[*].regularDays").value(hasItem(DEFAULT_REGULAR_DAYS)))
                .andExpect(jsonPath("$.[*].overtime").value(hasItem(DEFAULT_OVERTIME)))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)));
    }

    @Test
    @Transactional
    public void getPaySheets() throws Exception {
        // Initialize the database
        paySheetsRepository.saveAndFlush(paySheets);

        // Get the paySheets
        restPaySheetsMockMvc.perform(get("/api/paySheetss/{id}", paySheets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(paySheets.getId().intValue()))
            .andExpect(jsonPath("$.regularDays").value(DEFAULT_REGULAR_DAYS))
            .andExpect(jsonPath("$.overtime").value(DEFAULT_OVERTIME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingPaySheets() throws Exception {
        // Get the paySheets
        restPaySheetsMockMvc.perform(get("/api/paySheetss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaySheets() throws Exception {
        // Initialize the database
        paySheetsRepository.saveAndFlush(paySheets);

		int databaseSizeBeforeUpdate = paySheetsRepository.findAll().size();

        // Update the paySheets
        paySheets.setRegularDays(UPDATED_REGULAR_DAYS);
        paySheets.setOvertime(UPDATED_OVERTIME);
        paySheets.setStartDate(UPDATED_START_DATE);
        paySheets.setEndDate(UPDATED_END_DATE);
        PaySheetsDTO paySheetsDTO = paySheetsMapper.paySheetsToPaySheetsDTO(paySheets);

        restPaySheetsMockMvc.perform(put("/api/paySheetss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(paySheetsDTO)))
                .andExpect(status().isOk());

        // Validate the PaySheets in the database
        List<PaySheets> paySheetss = paySheetsRepository.findAll();
        assertThat(paySheetss).hasSize(databaseSizeBeforeUpdate);
        PaySheets testPaySheets = paySheetss.get(paySheetss.size() - 1);
        assertThat(testPaySheets.getRegularDays()).isEqualTo(UPDATED_REGULAR_DAYS);
        assertThat(testPaySheets.getOvertime()).isEqualTo(UPDATED_OVERTIME);
        assertThat(testPaySheets.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPaySheets.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void deletePaySheets() throws Exception {
        // Initialize the database
        paySheetsRepository.saveAndFlush(paySheets);

		int databaseSizeBeforeDelete = paySheetsRepository.findAll().size();

        // Get the paySheets
        restPaySheetsMockMvc.perform(delete("/api/paySheetss/{id}", paySheets.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PaySheets> paySheetss = paySheetsRepository.findAll();
        assertThat(paySheetss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
