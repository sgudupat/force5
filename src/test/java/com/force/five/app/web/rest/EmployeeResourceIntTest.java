package com.force.five.app.web.rest;

import com.force.five.app.Application;
import com.force.five.app.domain.Employee;
import com.force.five.app.repository.EmployeeRepository;
import com.force.five.app.service.EmployeeService;
import com.force.five.app.web.rest.dto.EmployeeDTO;
import com.force.five.app.web.rest.mapper.EmployeeMapper;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EmployeeResource REST controller.
 *
 * @see EmployeeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EmployeeResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_CATEGORY = "AAAAA";
    private static final String UPDATED_CATEGORY = "BBBBB";

    private static final BigDecimal DEFAULT_BASIC = new BigDecimal(1);
    private static final BigDecimal UPDATED_BASIC = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ALLOWANCES = new BigDecimal(1);
    private static final BigDecimal UPDATED_ALLOWANCES = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.format(DEFAULT_END_DATE);

    private static final Long DEFAULT_MOBILE = 1L;
    private static final Long UPDATED_MOBILE = 2L;

    private static final Long DEFAULT_MOBILE2 = 1L;
    private static final Long UPDATED_MOBILE2 = 2L;
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";
    private static final String DEFAULT_STATE = "AAAAA";
    private static final String UPDATED_STATE = "BBBBB";

    private static final Long DEFAULT_ZIPCODE = 1L;
    private static final Long UPDATED_ZIPCODE = 2L;

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private EmployeeMapper employeeMapper;

    @Inject
    private EmployeeService employeeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeResource employeeResource = new EmployeeResource();
        ReflectionTestUtils.setField(employeeResource, "employeeService", employeeService);
        ReflectionTestUtils.setField(employeeResource, "employeeMapper", employeeMapper);
        this.restEmployeeMockMvc = MockMvcBuilders.standaloneSetup(employeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        employee = new Employee();
        employee.setName(DEFAULT_NAME);
        employee.setCategory(DEFAULT_CATEGORY);
        employee.setBasic(DEFAULT_BASIC);
        employee.setAllowances(DEFAULT_ALLOWANCES);
        employee.setStartDate(DEFAULT_START_DATE);
        employee.setEndDate(DEFAULT_END_DATE);
        employee.setMobile(DEFAULT_MOBILE);
        employee.setMobile2(DEFAULT_MOBILE2);
        employee.setAddress(DEFAULT_ADDRESS);
        employee.setCity(DEFAULT_CITY);
        employee.setState(DEFAULT_STATE);
        employee.setZipcode(DEFAULT_ZIPCODE);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
                .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employees.get(employees.size() - 1);
        assertThat(testEmployee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployee.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testEmployee.getBasic()).isEqualTo(DEFAULT_BASIC);
        assertThat(testEmployee.getAllowances()).isEqualTo(DEFAULT_ALLOWANCES);
        assertThat(testEmployee.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEmployee.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEmployee.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testEmployee.getMobile2()).isEqualTo(DEFAULT_MOBILE2);
        assertThat(testEmployee.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEmployee.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testEmployee.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testEmployee.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employees
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].basic").value(hasItem(DEFAULT_BASIC.intValue())))
                .andExpect(jsonPath("$.[*].allowances").value(hasItem(DEFAULT_ALLOWANCES.intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.intValue())))
                .andExpect(jsonPath("$.[*].mobile2").value(hasItem(DEFAULT_MOBILE2.intValue())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE.intValue())));
    }

    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.basic").value(DEFAULT_BASIC.intValue()))
            .andExpect(jsonPath("$.allowances").value(DEFAULT_ALLOWANCES.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.intValue()))
            .andExpect(jsonPath("$.mobile2").value(DEFAULT_MOBILE2.intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

		int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        employee.setName(UPDATED_NAME);
        employee.setCategory(UPDATED_CATEGORY);
        employee.setBasic(UPDATED_BASIC);
        employee.setAllowances(UPDATED_ALLOWANCES);
        employee.setStartDate(UPDATED_START_DATE);
        employee.setEndDate(UPDATED_END_DATE);
        employee.setMobile(UPDATED_MOBILE);
        employee.setMobile2(UPDATED_MOBILE2);
        employee.setAddress(UPDATED_ADDRESS);
        employee.setCity(UPDATED_CITY);
        employee.setState(UPDATED_STATE);
        employee.setZipcode(UPDATED_ZIPCODE);
        EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(employee);

        restEmployeeMockMvc.perform(put("/api/employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
                .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employees.get(employees.size() - 1);
        assertThat(testEmployee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployee.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testEmployee.getBasic()).isEqualTo(UPDATED_BASIC);
        assertThat(testEmployee.getAllowances()).isEqualTo(UPDATED_ALLOWANCES);
        assertThat(testEmployee.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEmployee.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEmployee.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testEmployee.getMobile2()).isEqualTo(UPDATED_MOBILE2);
        assertThat(testEmployee.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEmployee.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEmployee.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testEmployee.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

		int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Get the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
