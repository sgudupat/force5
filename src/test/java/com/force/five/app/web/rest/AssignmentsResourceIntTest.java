package com.force.five.app.web.rest;

import com.force.five.app.Application;
import com.force.five.app.domain.Assignments;
import com.force.five.app.repository.AssignmentsRepository;
import com.force.five.app.service.AssignmentsService;
import com.force.five.app.web.rest.dto.AssignmentsDTO;
import com.force.five.app.web.rest.mapper.AssignmentsMapper;

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
 * Test class for the AssignmentsResource REST controller.
 *
 * @see AssignmentsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AssignmentsResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.format(DEFAULT_END_DATE);

    @Inject
    private AssignmentsRepository assignmentsRepository;

    @Inject
    private AssignmentsMapper assignmentsMapper;

    @Inject
    private AssignmentsService assignmentsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAssignmentsMockMvc;

    private Assignments assignments;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AssignmentsResource assignmentsResource = new AssignmentsResource();
        ReflectionTestUtils.setField(assignmentsResource, "assignmentsService", assignmentsService);
        ReflectionTestUtils.setField(assignmentsResource, "assignmentsMapper", assignmentsMapper);
        this.restAssignmentsMockMvc = MockMvcBuilders.standaloneSetup(assignmentsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        assignments = new Assignments();
        assignments.setStartDate(DEFAULT_START_DATE);
        assignments.setEndDate(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createAssignments() throws Exception {
        int databaseSizeBeforeCreate = assignmentsRepository.findAll().size();

        // Create the Assignments
        AssignmentsDTO assignmentsDTO = assignmentsMapper.assignmentsToAssignmentsDTO(assignments);

        restAssignmentsMockMvc.perform(post("/api/assignmentss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assignmentsDTO)))
                .andExpect(status().isCreated());

        // Validate the Assignments in the database
        List<Assignments> assignmentss = assignmentsRepository.findAll();
        assertThat(assignmentss).hasSize(databaseSizeBeforeCreate + 1);
        Assignments testAssignments = assignmentss.get(assignmentss.size() - 1);
        assertThat(testAssignments.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testAssignments.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAssignmentss() throws Exception {
        // Initialize the database
        assignmentsRepository.saveAndFlush(assignments);

        // Get all the assignmentss
        restAssignmentsMockMvc.perform(get("/api/assignmentss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(assignments.getId().intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)));
    }

    @Test
    @Transactional
    public void getAssignments() throws Exception {
        // Initialize the database
        assignmentsRepository.saveAndFlush(assignments);

        // Get the assignments
        restAssignmentsMockMvc.perform(get("/api/assignmentss/{id}", assignments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(assignments.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingAssignments() throws Exception {
        // Get the assignments
        restAssignmentsMockMvc.perform(get("/api/assignmentss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssignments() throws Exception {
        // Initialize the database
        assignmentsRepository.saveAndFlush(assignments);

		int databaseSizeBeforeUpdate = assignmentsRepository.findAll().size();

        // Update the assignments
        assignments.setStartDate(UPDATED_START_DATE);
        assignments.setEndDate(UPDATED_END_DATE);
        AssignmentsDTO assignmentsDTO = assignmentsMapper.assignmentsToAssignmentsDTO(assignments);

        restAssignmentsMockMvc.perform(put("/api/assignmentss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(assignmentsDTO)))
                .andExpect(status().isOk());

        // Validate the Assignments in the database
        List<Assignments> assignmentss = assignmentsRepository.findAll();
        assertThat(assignmentss).hasSize(databaseSizeBeforeUpdate);
        Assignments testAssignments = assignmentss.get(assignmentss.size() - 1);
        assertThat(testAssignments.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testAssignments.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void deleteAssignments() throws Exception {
        // Initialize the database
        assignmentsRepository.saveAndFlush(assignments);

		int databaseSizeBeforeDelete = assignmentsRepository.findAll().size();

        // Get the assignments
        restAssignmentsMockMvc.perform(delete("/api/assignmentss/{id}", assignments.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Assignments> assignmentss = assignmentsRepository.findAll();
        assertThat(assignmentss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
