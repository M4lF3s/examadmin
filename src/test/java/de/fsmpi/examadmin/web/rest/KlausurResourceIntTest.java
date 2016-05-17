package de.fsmpi.examadmin.web.rest;

import de.fsmpi.examadmin.ExamadminApp;
import de.fsmpi.examadmin.domain.Klausur;
import de.fsmpi.examadmin.repository.KlausurRepository;

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

import de.fsmpi.examadmin.domain.enumeration.Art;
import de.fsmpi.examadmin.domain.enumeration.Art;

/**
 * Test class for the KlausurResource REST controller.
 *
 * @see KlausurResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExamadminApp.class)
@WebAppConfiguration
@IntegrationTest
public class KlausurResourceIntTest {

    private static final String DEFAULT_PFAD = "AAAAA";
    private static final String UPDATED_PFAD = "BBBBB";

    private static final String DEFAULT_PRUEFUNGSFORM = "AAAAA";
    private static final String UPDATED_PRUEFUNGSFORM = "BBBBB";

    private static final Art DEFAULT_ART = Art.MUENDLICH;
    private static final Art UPDATED_ART = Art.SCHRIFTLICH;

    private static final Integer DEFAULT_K_ID = 1;
    private static final Integer UPDATED_K_ID = 2;
    private static final String DEFAULT_SEMESTER = "AAAAA";
    private static final String UPDATED_SEMESTER = "BBBBB";

    @Inject
    private KlausurRepository klausurRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKlausurMockMvc;

    private Klausur klausur;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KlausurResource klausurResource = new KlausurResource();
        ReflectionTestUtils.setField(klausurResource, "klausurRepository", klausurRepository);
        this.restKlausurMockMvc = MockMvcBuilders.standaloneSetup(klausurResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        klausur = new Klausur();
        klausur.setPfad(DEFAULT_PFAD);
        klausur.setArt(DEFAULT_ART);
        klausur.setPruefungsform(DEFAULT_PRUEFUNGSFORM);
        klausur.setArt(DEFAULT_ART);
        klausur.setkID(DEFAULT_K_ID);
        klausur.setSemester(DEFAULT_SEMESTER);
    }

    @Test
    @Transactional
    public void createKlausur() throws Exception {
        int databaseSizeBeforeCreate = klausurRepository.findAll().size();

        // Create the Klausur

        restKlausurMockMvc.perform(post("/api/klausurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(klausur)))
                .andExpect(status().isCreated());

        // Validate the Klausur in the database
        List<Klausur> klausurs = klausurRepository.findAll();
        assertThat(klausurs).hasSize(databaseSizeBeforeCreate + 1);
        Klausur testKlausur = klausurs.get(klausurs.size() - 1);
        assertThat(testKlausur.getPfad()).isEqualTo(DEFAULT_PFAD);
        assertThat(testKlausur.getArt()).isEqualTo(DEFAULT_ART);
        assertThat(testKlausur.getPruefungsform()).isEqualTo(DEFAULT_PRUEFUNGSFORM);
        assertThat(testKlausur.getArt()).isEqualTo(DEFAULT_ART);
        assertThat(testKlausur.getkID()).isEqualTo(DEFAULT_K_ID);
        assertThat(testKlausur.getSemester()).isEqualTo(DEFAULT_SEMESTER);
    }

    @Test
    @Transactional
    public void checkPfadIsRequired() throws Exception {
        int databaseSizeBeforeTest = klausurRepository.findAll().size();
        // set the field null
        klausur.setPfad(null);

        // Create the Klausur, which fails.

        restKlausurMockMvc.perform(post("/api/klausurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(klausur)))
                .andExpect(status().isBadRequest());

        List<Klausur> klausurs = klausurRepository.findAll();
        assertThat(klausurs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPruefungsformIsRequired() throws Exception {
        int databaseSizeBeforeTest = klausurRepository.findAll().size();
        // set the field null
        klausur.setPruefungsform(null);

        // Create the Klausur, which fails.

        restKlausurMockMvc.perform(post("/api/klausurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(klausur)))
                .andExpect(status().isBadRequest());

        List<Klausur> klausurs = klausurRepository.findAll();
        assertThat(klausurs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArtIsRequired() throws Exception {
        int databaseSizeBeforeTest = klausurRepository.findAll().size();
        // set the field null
        klausur.setArt(null);

        // Create the Klausur, which fails.

        restKlausurMockMvc.perform(post("/api/klausurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(klausur)))
                .andExpect(status().isBadRequest());

        List<Klausur> klausurs = klausurRepository.findAll();
        assertThat(klausurs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkkIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = klausurRepository.findAll().size();
        // set the field null
        klausur.setkID(null);

        // Create the Klausur, which fails.

        restKlausurMockMvc.perform(post("/api/klausurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(klausur)))
                .andExpect(status().isBadRequest());

        List<Klausur> klausurs = klausurRepository.findAll();
        assertThat(klausurs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSemesterIsRequired() throws Exception {
        int databaseSizeBeforeTest = klausurRepository.findAll().size();
        // set the field null
        klausur.setSemester(null);

        // Create the Klausur, which fails.

        restKlausurMockMvc.perform(post("/api/klausurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(klausur)))
                .andExpect(status().isBadRequest());

        List<Klausur> klausurs = klausurRepository.findAll();
        assertThat(klausurs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKlausurs() throws Exception {
        // Initialize the database
        klausurRepository.saveAndFlush(klausur);

        // Get all the klausurs
        restKlausurMockMvc.perform(get("/api/klausurs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(klausur.getId().intValue())))
                .andExpect(jsonPath("$.[*].pfad").value(hasItem(DEFAULT_PFAD.toString())))
                .andExpect(jsonPath("$.[*].art").value(hasItem(DEFAULT_ART.toString())))
                .andExpect(jsonPath("$.[*].pruefungsform").value(hasItem(DEFAULT_PRUEFUNGSFORM.toString())))
                .andExpect(jsonPath("$.[*].art").value(hasItem(DEFAULT_ART.toString())))
                .andExpect(jsonPath("$.[*].kID").value(hasItem(DEFAULT_K_ID)))
                .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER.toString())));
    }

    @Test
    @Transactional
    public void getKlausur() throws Exception {
        // Initialize the database
        klausurRepository.saveAndFlush(klausur);

        // Get the klausur
        restKlausurMockMvc.perform(get("/api/klausurs/{id}", klausur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(klausur.getId().intValue()))
            .andExpect(jsonPath("$.pfad").value(DEFAULT_PFAD.toString()))
            .andExpect(jsonPath("$.art").value(DEFAULT_ART.toString()))
            .andExpect(jsonPath("$.pruefungsform").value(DEFAULT_PRUEFUNGSFORM.toString()))
            .andExpect(jsonPath("$.art").value(DEFAULT_ART.toString()))
            .andExpect(jsonPath("$.kID").value(DEFAULT_K_ID))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKlausur() throws Exception {
        // Get the klausur
        restKlausurMockMvc.perform(get("/api/klausurs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKlausur() throws Exception {
        // Initialize the database
        klausurRepository.saveAndFlush(klausur);
        int databaseSizeBeforeUpdate = klausurRepository.findAll().size();

        // Update the klausur
        Klausur updatedKlausur = new Klausur();
        updatedKlausur.setId(klausur.getId());
        updatedKlausur.setPfad(UPDATED_PFAD);
        updatedKlausur.setArt(UPDATED_ART);
        updatedKlausur.setPruefungsform(UPDATED_PRUEFUNGSFORM);
        updatedKlausur.setArt(UPDATED_ART);
        updatedKlausur.setkID(UPDATED_K_ID);
        updatedKlausur.setSemester(UPDATED_SEMESTER);

        restKlausurMockMvc.perform(put("/api/klausurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedKlausur)))
                .andExpect(status().isOk());

        // Validate the Klausur in the database
        List<Klausur> klausurs = klausurRepository.findAll();
        assertThat(klausurs).hasSize(databaseSizeBeforeUpdate);
        Klausur testKlausur = klausurs.get(klausurs.size() - 1);
        assertThat(testKlausur.getPfad()).isEqualTo(UPDATED_PFAD);
        assertThat(testKlausur.getArt()).isEqualTo(UPDATED_ART);
        assertThat(testKlausur.getPruefungsform()).isEqualTo(UPDATED_PRUEFUNGSFORM);
        assertThat(testKlausur.getArt()).isEqualTo(UPDATED_ART);
        assertThat(testKlausur.getkID()).isEqualTo(UPDATED_K_ID);
        assertThat(testKlausur.getSemester()).isEqualTo(UPDATED_SEMESTER);
    }

    @Test
    @Transactional
    public void deleteKlausur() throws Exception {
        // Initialize the database
        klausurRepository.saveAndFlush(klausur);
        int databaseSizeBeforeDelete = klausurRepository.findAll().size();

        // Get the klausur
        restKlausurMockMvc.perform(delete("/api/klausurs/{id}", klausur.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Klausur> klausurs = klausurRepository.findAll();
        assertThat(klausurs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
