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

    private static final String DEFAULT_MONTH = "AAAAA";
    private static final String UPDATED_MONTH = "BBBBB";
    private static final String DEFAULT_YEAR = "AAAAA";
    private static final String UPDATED_YEAR = "BBBBB";

    private static final Integer DEFAULT_REGULAR_DAYS = 1;
    private static final Integer UPDATED_REGULAR_DAYS = 2;

    private static final Integer DEFAULT_DAYS_WORKED = 1;
    private static final Integer UPDATED_DAYS_WORKED = 2;

    private static final Integer DEFAULT_WEEKLY_OFF = 1;
    private static final Integer UPDATED_WEEKLY_OFF = 2;

    private static final Integer DEFAULT_COMP_OFF = 1;
    private static final Integer UPDATED_COMP_OFF = 2;

    private static final Integer DEFAULT_HOLIDAYS = 1;
    private static final Integer UPDATED_HOLIDAYS = 2;

    private static final Integer DEFAULT_OVERTIME = 1;
    private static final Integer UPDATED_OVERTIME = 2;

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
        paySheets.setMonth(DEFAULT_MONTH);
        paySheets.setYear(DEFAULT_YEAR);
        paySheets.setRegularDays(DEFAULT_REGULAR_DAYS);
        paySheets.setDaysWorked(DEFAULT_DAYS_WORKED);
        paySheets.setWeeklyOff(DEFAULT_WEEKLY_OFF);
        paySheets.setCompOff(DEFAULT_COMP_OFF);
        paySheets.setHolidays(DEFAULT_HOLIDAYS);
        paySheets.setOvertime(DEFAULT_OVERTIME);
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
        assertThat(testPaySheets.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testPaySheets.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testPaySheets.getRegularDays()).isEqualTo(DEFAULT_REGULAR_DAYS);
        assertThat(testPaySheets.getDaysWorked()).isEqualTo(DEFAULT_DAYS_WORKED);
        assertThat(testPaySheets.getWeeklyOff()).isEqualTo(DEFAULT_WEEKLY_OFF);
        assertThat(testPaySheets.getCompOff()).isEqualTo(DEFAULT_COMP_OFF);
        assertThat(testPaySheets.getHolidays()).isEqualTo(DEFAULT_HOLIDAYS);
        assertThat(testPaySheets.getOvertime()).isEqualTo(DEFAULT_OVERTIME);
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
                .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
                .andExpect(jsonPath("$.[*].regularDays").value(hasItem(DEFAULT_REGULAR_DAYS)))
                .andExpect(jsonPath("$.[*].daysWorked").value(hasItem(DEFAULT_DAYS_WORKED)))
                .andExpect(jsonPath("$.[*].weeklyOff").value(hasItem(DEFAULT_WEEKLY_OFF)))
                .andExpect(jsonPath("$.[*].compOff").value(hasItem(DEFAULT_COMP_OFF)))
                .andExpect(jsonPath("$.[*].holidays").value(hasItem(DEFAULT_HOLIDAYS)))
                .andExpect(jsonPath("$.[*].overtime").value(hasItem(DEFAULT_OVERTIME)));
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
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.regularDays").value(DEFAULT_REGULAR_DAYS))
            .andExpect(jsonPath("$.daysWorked").value(DEFAULT_DAYS_WORKED))
            .andExpect(jsonPath("$.weeklyOff").value(DEFAULT_WEEKLY_OFF))
            .andExpect(jsonPath("$.compOff").value(DEFAULT_COMP_OFF))
            .andExpect(jsonPath("$.holidays").value(DEFAULT_HOLIDAYS))
            .andExpect(jsonPath("$.overtime").value(DEFAULT_OVERTIME));
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
        paySheets.setMonth(UPDATED_MONTH);
        paySheets.setYear(UPDATED_YEAR);
        paySheets.setRegularDays(UPDATED_REGULAR_DAYS);
        paySheets.setDaysWorked(UPDATED_DAYS_WORKED);
        paySheets.setWeeklyOff(UPDATED_WEEKLY_OFF);
        paySheets.setCompOff(UPDATED_COMP_OFF);
        paySheets.setHolidays(UPDATED_HOLIDAYS);
        paySheets.setOvertime(UPDATED_OVERTIME);
        PaySheetsDTO paySheetsDTO = paySheetsMapper.paySheetsToPaySheetsDTO(paySheets);

        restPaySheetsMockMvc.perform(put("/api/paySheetss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(paySheetsDTO)))
                .andExpect(status().isOk());

        // Validate the PaySheets in the database
        List<PaySheets> paySheetss = paySheetsRepository.findAll();
        assertThat(paySheetss).hasSize(databaseSizeBeforeUpdate);
        PaySheets testPaySheets = paySheetss.get(paySheetss.size() - 1);
        assertThat(testPaySheets.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testPaySheets.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPaySheets.getRegularDays()).isEqualTo(UPDATED_REGULAR_DAYS);
        assertThat(testPaySheets.getDaysWorked()).isEqualTo(UPDATED_DAYS_WORKED);
        assertThat(testPaySheets.getWeeklyOff()).isEqualTo(UPDATED_WEEKLY_OFF);
        assertThat(testPaySheets.getCompOff()).isEqualTo(UPDATED_COMP_OFF);
        assertThat(testPaySheets.getHolidays()).isEqualTo(UPDATED_HOLIDAYS);
        assertThat(testPaySheets.getOvertime()).isEqualTo(UPDATED_OVERTIME);
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
