package com.shyam.booking.web.rest;

import com.shyam.booking.BookingApp;
import com.shyam.booking.domain.FileMeta;
import com.shyam.booking.repository.FileMetaRepository;
import com.shyam.booking.service.FileMetaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FileMetaResource} REST controller.
 */
@SpringBootTest(classes = BookingApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FileMetaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    @Autowired
    private FileMetaRepository fileMetaRepository;

    @Autowired
    private FileMetaService fileMetaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileMetaMockMvc;

    private FileMeta fileMeta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileMeta createEntity(EntityManager em) {
        FileMeta fileMeta = new FileMeta()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .size(DEFAULT_SIZE)
            .path(DEFAULT_PATH)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return fileMeta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileMeta createUpdatedEntity(EntityManager em) {
        FileMeta fileMeta = new FileMeta()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .size(UPDATED_SIZE)
            .path(UPDATED_PATH)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return fileMeta;
    }

    @BeforeEach
    public void initTest() {
        fileMeta = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileMeta() throws Exception {
        int databaseSizeBeforeCreate = fileMetaRepository.findAll().size();
        // Create the FileMeta
        restFileMetaMockMvc.perform(post("/api/file-metas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileMeta)))
            .andExpect(status().isCreated());

        // Validate the FileMeta in the database
        List<FileMeta> fileMetaList = fileMetaRepository.findAll();
        assertThat(fileMetaList).hasSize(databaseSizeBeforeCreate + 1);
        FileMeta testFileMeta = fileMetaList.get(fileMetaList.size() - 1);
        assertThat(testFileMeta.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFileMeta.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFileMeta.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testFileMeta.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testFileMeta.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFileMeta.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testFileMeta.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFileMeta.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createFileMetaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileMetaRepository.findAll().size();

        // Create the FileMeta with an existing ID
        fileMeta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileMetaMockMvc.perform(post("/api/file-metas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileMeta)))
            .andExpect(status().isBadRequest());

        // Validate the FileMeta in the database
        List<FileMeta> fileMetaList = fileMetaRepository.findAll();
        assertThat(fileMetaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFileMetas() throws Exception {
        // Initialize the database
        fileMetaRepository.saveAndFlush(fileMeta);

        // Get all the fileMetaList
        restFileMetaMockMvc.perform(get("/api/file-metas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileMeta.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())));
    }
    
    @Test
    @Transactional
    public void getFileMeta() throws Exception {
        // Initialize the database
        fileMetaRepository.saveAndFlush(fileMeta);

        // Get the fileMeta
        restFileMetaMockMvc.perform(get("/api/file-metas/{id}", fileMeta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileMeta.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFileMeta() throws Exception {
        // Get the fileMeta
        restFileMetaMockMvc.perform(get("/api/file-metas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileMeta() throws Exception {
        // Initialize the database
        fileMetaService.save(fileMeta);

        int databaseSizeBeforeUpdate = fileMetaRepository.findAll().size();

        // Update the fileMeta
        FileMeta updatedFileMeta = fileMetaRepository.findById(fileMeta.getId()).get();
        // Disconnect from session so that the updates on updatedFileMeta are not directly saved in db
        em.detach(updatedFileMeta);
        updatedFileMeta
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .size(UPDATED_SIZE)
            .path(UPDATED_PATH)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restFileMetaMockMvc.perform(put("/api/file-metas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFileMeta)))
            .andExpect(status().isOk());

        // Validate the FileMeta in the database
        List<FileMeta> fileMetaList = fileMetaRepository.findAll();
        assertThat(fileMetaList).hasSize(databaseSizeBeforeUpdate);
        FileMeta testFileMeta = fileMetaList.get(fileMetaList.size() - 1);
        assertThat(testFileMeta.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFileMeta.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFileMeta.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testFileMeta.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testFileMeta.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFileMeta.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testFileMeta.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFileMeta.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingFileMeta() throws Exception {
        int databaseSizeBeforeUpdate = fileMetaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileMetaMockMvc.perform(put("/api/file-metas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileMeta)))
            .andExpect(status().isBadRequest());

        // Validate the FileMeta in the database
        List<FileMeta> fileMetaList = fileMetaRepository.findAll();
        assertThat(fileMetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFileMeta() throws Exception {
        // Initialize the database
        fileMetaService.save(fileMeta);

        int databaseSizeBeforeDelete = fileMetaRepository.findAll().size();

        // Delete the fileMeta
        restFileMetaMockMvc.perform(delete("/api/file-metas/{id}", fileMeta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileMeta> fileMetaList = fileMetaRepository.findAll();
        assertThat(fileMetaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
