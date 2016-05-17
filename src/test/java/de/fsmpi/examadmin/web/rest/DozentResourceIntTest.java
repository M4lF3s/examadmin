package de.fsmpi.examadmin.web.rest;

import de.fsmpi.examadmin.ExamadminApp;
import de.fsmpi.examadmin.domain.Dozent;
import de.fsmpi.examadmin.repository.DozentRepository;

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
 * Test class for the DozentResource REST controller.
 *
 * @see DozentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExamadminApp.class)
@WebAppConfiguration
@IntegrationTest
public class DozentResourceIntTest {

    private static final String DEFAULT_VORNAME = "AAAAA";
    private static final String UPDATED_VORNAME = "BBBBB";
    private static final String DEFAULT_NACHNAME = "AAAAA";
    private static final String UPDATED_NACHNAME = "BBBBB";

    private static final Integer DEFAULT_D_ID = 1;
    private static final Integer UPDATED_D_ID = 2;

    @Inject
    private DozentRepository dozentRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDozentMockMvc;

    private Dozent dozent;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DozentResource dozentResource = new DozentResource();
        ReflectionTestUtils.setField(dozentResource, "dozentRepository", dozentRepository);
        this.restDozentMockMvc = MockMvcBuilders.standaloneSetup(dozentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dozent = new Dozent();
        dozent.setVorname(DEFAULT_VORNAME);
        dozent.setNachname(DEFAULT_NACHNAME);
        dozent.setdID(DEFAULT_D_ID);
    }

    @Test
    @Transactional
    public void createDozent() throws Exception {
        int databaseSizeBeforeCreate = dozentRepository.findAll().size();

        // Create the Dozent

        restDozentMockMvc.perform(post("/api/dozents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dozent)))
                .andExpect(status().isCreated());

        // Validate the Dozent in the database
        List<Dozent> dozents = dozentRepository.findAll();
        assertThat(dozents).hasSize(databaseSizeBeforeCreate + 1);
        Dozent testDozent = dozents.get(dozents.size() - 1);
        assertThat(testDozent.getVorname()).isEqualTo(DEFAULT_VORNAME);
        assertThat(testDozent.getNachname()).isEqualTo(DEFAULT_NACHNAME);
        assertThat(testDozent.getdID()).isEqualTo(DEFAULT_D_ID);
    }

    @Test
    @Transactional
    public void checkVornameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dozentRepository.findAll().size();
        // set the field null
        dozent.setVorname(null);

        // Create the Dozent, which fails.

        restDozentMockMvc.perform(post("/api/dozents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dozent)))
                .andExpect(status().isBadRequest());

        List<Dozent> dozents = dozentRepository.findAll();
        assertThat(dozents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNachnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dozentRepository.findAll().size();
        // set the field null
        dozent.setNachname(null);

        // Create the Dozent, which fails.

        restDozentMockMvc.perform(post("/api/dozents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dozent)))
                .andExpect(status().isBadRequest());

        List<Dozent> dozents = dozentRepository.findAll();
        assertThat(dozents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkdIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = dozentRepository.findAll().size();
        // set the field null
        dozent.setdID(null);

        // Create the Dozent, which fails.

        restDozentMockMvc.perform(post("/api/dozents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dozent)))
                .andExpect(status().isBadRequest());

        List<Dozent> dozents = dozentRepository.findAll();
        assertThat(dozents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDozents() throws Exception {
        // Initialize the database
        dozentRepository.saveAndFlush(dozent);

        // Get all the dozents
        restDozentMockMvc.perform(get("/api/dozents?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dozent.getId().intValue())))
                .andExpect(jsonPath("$.[*].vorname").value(hasItem(DEFAULT_VORNAME.toString())))
                .andExpect(jsonPath("$.[*].nachname").value(hasItem(DEFAULT_NACHNAME.toString())))
                .andExpect(jsonPath("$.[*].dID").value(hasItem(DEFAULT_D_ID)));
    }

    @Test
    @Transactional
    public void getDozent() throws Exception {
        // Initialize the database
        dozentRepository.saveAndFlush(dozent);

        // Get the dozent
        restDozentMockMvc.perform(get("/api/dozents/{id}", dozent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dozent.getId().intValue()))
            .andExpect(jsonPath("$.vorname").value(DEFAULT_VORNAME.toString()))
            .andExpect(jsonPath("$.nachname").value(DEFAULT_NACHNAME.toString()))
            .andExpect(jsonPath("$.dID").value(DEFAULT_D_ID));
    }

    @Test
    @Transactional
    public void getNonExistingDozent() throws Exception {
        // Get the dozent
        restDozentMockMvc.perform(get("/api/dozents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDozent() throws Exception {
        // Initialize the database
        dozentRepository.saveAndFlush(dozent);
        int databaseSizeBeforeUpdate = dozentRepository.findAll().size();

        // Update the dozent
        Dozent updatedDozent = new Dozent();
        updatedDozent.setId(dozent.getId());
        updatedDozent.setVorname(UPDATED_VORNAME);
        updatedDozent.setNachname(UPDATED_NACHNAME);
        updatedDozent.setdID(UPDATED_D_ID);

        restDozentMockMvc.perform(put("/api/dozents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDozent)))
                .andExpect(status().isOk());

        // Validate the Dozent in the database
        List<Dozent> dozents = dozentRepository.findAll();
        assertThat(dozents).hasSize(databaseSizeBeforeUpdate);
        Dozent testDozent = dozents.get(dozents.size() - 1);
        assertThat(testDozent.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testDozent.getNachname()).isEqualTo(UPDATED_NACHNAME);
        assertThat(testDozent.getdID()).isEqualTo(UPDATED_D_ID);
    }

    @Test
    @Transactional
    public void deleteDozent() throws Exception {
        // Initialize the database
        dozentRepository.saveAndFlush(dozent);
        int databaseSizeBeforeDelete = dozentRepository.findAll().size();

        // Get the dozent
        restDozentMockMvc.perform(delete("/api/dozents/{id}", dozent.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Dozent> dozents = dozentRepository.findAll();
        assertThat(dozents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
