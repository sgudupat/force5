package com.force.five.app.web.rest;

import com.force.five.app.Application;
import com.force.five.app.domain.TripleSet;
import com.force.five.app.repository.TripleSetRepository;
import com.force.five.app.service.TripleSetService;
import com.force.five.app.web.rest.dto.TripleSetDTO;
import com.force.five.app.web.rest.mapper.TripleSetMapper;

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
 * Test class for the TripleSetResource REST controller.
 *
 * @see TripleSetResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TripleSetResourceIntTest {

    private static final String DEFAULT_CONTROL = "AAAAA";
    private static final String UPDATED_CONTROL = "BBBBB";
    private static final String DEFAULT_PARENT = "AAAAA";
    private static final String UPDATED_PARENT = "BBBBB";
    private static final String DEFAULT_CHILD = "AAAAA";
    private static final String UPDATED_CHILD = "BBBBB";
    private static final String DEFAULT_CONFIG = "AAAAA";
    private static final String UPDATED_CONFIG = "BBBBB";

    @Inject
    private TripleSetRepository tripleSetRepository;

    @Inject
    private TripleSetMapper tripleSetMapper;

    @Inject
    private TripleSetService tripleSetService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTripleSetMockMvc;

    private TripleSet tripleSet;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TripleSetResource tripleSetResource = new TripleSetResource();
        ReflectionTestUtils.setField(tripleSetResource, "tripleSetService", tripleSetService);
        ReflectionTestUtils.setField(tripleSetResource, "tripleSetMapper", tripleSetMapper);
        this.restTripleSetMockMvc = MockMvcBuilders.standaloneSetup(tripleSetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tripleSet = new TripleSet();
        tripleSet.setControl(DEFAULT_CONTROL);
        tripleSet.setParent(DEFAULT_PARENT);
        tripleSet.setChild(DEFAULT_CHILD);
        tripleSet.setConfig(DEFAULT_CONFIG);
    }

    @Test
    @Transactional
    public void createTripleSet() throws Exception {
        int databaseSizeBeforeCreate = tripleSetRepository.findAll().size();

        // Create the TripleSet
        TripleSetDTO tripleSetDTO = tripleSetMapper.tripleSetToTripleSetDTO(tripleSet);

        restTripleSetMockMvc.perform(post("/api/tripleSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tripleSetDTO)))
                .andExpect(status().isCreated());

        // Validate the TripleSet in the database
        List<TripleSet> tripleSets = tripleSetRepository.findAll();
        assertThat(tripleSets).hasSize(databaseSizeBeforeCreate + 1);
        TripleSet testTripleSet = tripleSets.get(tripleSets.size() - 1);
        assertThat(testTripleSet.getControl()).isEqualTo(DEFAULT_CONTROL);
        assertThat(testTripleSet.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testTripleSet.getChild()).isEqualTo(DEFAULT_CHILD);
        assertThat(testTripleSet.getConfig()).isEqualTo(DEFAULT_CONFIG);
    }

    @Test
    @Transactional
    public void getAllTripleSets() throws Exception {
        // Initialize the database
        tripleSetRepository.saveAndFlush(tripleSet);

        // Get all the tripleSets
        restTripleSetMockMvc.perform(get("/api/tripleSets?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tripleSet.getId().intValue())))
                .andExpect(jsonPath("$.[*].control").value(hasItem(DEFAULT_CONTROL.toString())))
                .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT.toString())))
                .andExpect(jsonPath("$.[*].child").value(hasItem(DEFAULT_CHILD.toString())))
                .andExpect(jsonPath("$.[*].config").value(hasItem(DEFAULT_CONFIG.toString())));
    }

    @Test
    @Transactional
    public void getTripleSet() throws Exception {
        // Initialize the database
        tripleSetRepository.saveAndFlush(tripleSet);

        // Get the tripleSet
        restTripleSetMockMvc.perform(get("/api/tripleSets/{id}", tripleSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tripleSet.getId().intValue()))
            .andExpect(jsonPath("$.control").value(DEFAULT_CONTROL.toString()))
            .andExpect(jsonPath("$.parent").value(DEFAULT_PARENT.toString()))
            .andExpect(jsonPath("$.child").value(DEFAULT_CHILD.toString()))
            .andExpect(jsonPath("$.config").value(DEFAULT_CONFIG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTripleSet() throws Exception {
        // Get the tripleSet
        restTripleSetMockMvc.perform(get("/api/tripleSets/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTripleSet() throws Exception {
        // Initialize the database
        tripleSetRepository.saveAndFlush(tripleSet);

		int databaseSizeBeforeUpdate = tripleSetRepository.findAll().size();

        // Update the tripleSet
        tripleSet.setControl(UPDATED_CONTROL);
        tripleSet.setParent(UPDATED_PARENT);
        tripleSet.setChild(UPDATED_CHILD);
        tripleSet.setConfig(UPDATED_CONFIG);
        TripleSetDTO tripleSetDTO = tripleSetMapper.tripleSetToTripleSetDTO(tripleSet);

        restTripleSetMockMvc.perform(put("/api/tripleSets")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tripleSetDTO)))
                .andExpect(status().isOk());

        // Validate the TripleSet in the database
        List<TripleSet> tripleSets = tripleSetRepository.findAll();
        assertThat(tripleSets).hasSize(databaseSizeBeforeUpdate);
        TripleSet testTripleSet = tripleSets.get(tripleSets.size() - 1);
        assertThat(testTripleSet.getControl()).isEqualTo(UPDATED_CONTROL);
        assertThat(testTripleSet.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testTripleSet.getChild()).isEqualTo(UPDATED_CHILD);
        assertThat(testTripleSet.getConfig()).isEqualTo(UPDATED_CONFIG);
    }

    @Test
    @Transactional
    public void deleteTripleSet() throws Exception {
        // Initialize the database
        tripleSetRepository.saveAndFlush(tripleSet);

		int databaseSizeBeforeDelete = tripleSetRepository.findAll().size();

        // Get the tripleSet
        restTripleSetMockMvc.perform(delete("/api/tripleSets/{id}", tripleSet.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TripleSet> tripleSets = tripleSetRepository.findAll();
        assertThat(tripleSets).hasSize(databaseSizeBeforeDelete - 1);
    }
}
