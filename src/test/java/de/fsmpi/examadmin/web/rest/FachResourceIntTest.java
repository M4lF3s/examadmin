package de.fsmpi.examadmin.web.rest;

import de.fsmpi.examadmin.ExamadminApp;
import de.fsmpi.examadmin.domain.Fach;
import de.fsmpi.examadmin.repository.FachRepository;

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
 * Test class for the FachResource REST controller.
 *
 * @see FachResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExamadminApp.class)
@WebAppConfiguration
@IntegrationTest
public class FachResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_F_ID = 1;
    private static final Integer UPDATED_F_ID = 2;

    @Inject
    private FachRepository fachRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFachMockMvc;

    private Fach fach;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FachResource fachResource = new FachResource();
        ReflectionTestUtils.setField(fachResource, "fachRepository", fachRepository);
        this.restFachMockMvc = MockMvcBuilders.standaloneSetup(fachResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fach = new Fach();
        fach.setName(DEFAULT_NAME);
        fach.setfID(DEFAULT_F_ID);
    }

    @Test
    @Transactional
    public void createFach() throws Exception {
        int databaseSizeBeforeCreate = fachRepository.findAll().size();

        // Create the Fach

        restFachMockMvc.perform(post("/api/faches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fach)))
                .andExpect(status().isCreated());

        // Validate the Fach in the database
        List<Fach> faches = fachRepository.findAll();
        assertThat(faches).hasSize(databaseSizeBeforeCreate + 1);
        Fach testFach = faches.get(faches.size() - 1);
        assertThat(testFach.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFach.getfID()).isEqualTo(DEFAULT_F_ID);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fachRepository.findAll().size();
        // set the field null
        fach.setName(null);

        // Create the Fach, which fails.

        restFachMockMvc.perform(post("/api/faches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fach)))
                .andExpect(status().isBadRequest());

        List<Fach> faches = fachRepository.findAll();
        assertThat(faches).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkfIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = fachRepository.findAll().size();
        // set the field null
        fach.setfID(null);

        // Create the Fach, which fails.

        restFachMockMvc.perform(post("/api/faches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fach)))
                .andExpect(status().isBadRequest());

        List<Fach> faches = fachRepository.findAll();
        assertThat(faches).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFaches() throws Exception {
        // Initialize the database
        fachRepository.saveAndFlush(fach);

        // Get all the faches
        restFachMockMvc.perform(get("/api/faches?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fach.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].fID").value(hasItem(DEFAULT_F_ID)));
    }

    @Test
    @Transactional
    public void getFach() throws Exception {
        // Initialize the database
        fachRepository.saveAndFlush(fach);

        // Get the fach
        restFachMockMvc.perform(get("/api/faches/{id}", fach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fach.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fID").value(DEFAULT_F_ID));
    }

    @Test
    @Transactional
    public void getNonExistingFach() throws Exception {
        // Get the fach
        restFachMockMvc.perform(get("/api/faches/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFach() throws Exception {
        // Initialize the database
        fachRepository.saveAndFlush(fach);
        int databaseSizeBeforeUpdate = fachRepository.findAll().size();

        // Update the fach
        Fach updatedFach = new Fach();
        updatedFach.setId(fach.getId());
        updatedFach.setName(UPDATED_NAME);
        updatedFach.setfID(UPDATED_F_ID);

        restFachMockMvc.perform(put("/api/faches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFach)))
                .andExpect(status().isOk());

        // Validate the Fach in the database
        List<Fach> faches = fachRepository.findAll();
        assertThat(faches).hasSize(databaseSizeBeforeUpdate);
        Fach testFach = faches.get(faches.size() - 1);
        assertThat(testFach.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFach.getfID()).isEqualTo(UPDATED_F_ID);
    }

    @Test
    @Transactional
    public void deleteFach() throws Exception {
        // Initialize the database
        fachRepository.saveAndFlush(fach);
        int databaseSizeBeforeDelete = fachRepository.findAll().size();

        // Get the fach
        restFachMockMvc.perform(delete("/api/faches/{id}", fach.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fach> faches = fachRepository.findAll();
        assertThat(faches).hasSize(databaseSizeBeforeDelete - 1);
    }
}
