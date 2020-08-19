package com.shyam.booking.web.rest;

import com.shyam.booking.BookingApp;
import com.shyam.booking.domain.EmployeeInfo;
import com.shyam.booking.repository.EmployeeInfoRepository;
import com.shyam.booking.service.EmployeeInfoService;

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

import com.shyam.booking.domain.enumeration.GenderType;
import com.shyam.booking.domain.enumeration.Status;
/**
 * Integration tests for the {@link EmployeeInfoResource} REST controller.
 */
@SpringBootTest(classes = BookingApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeInfoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final GenderType DEFAULT_GENDER = GenderType.MALE;
    private static final GenderType UPDATED_GENDER = GenderType.FEMALE;

    private static final String DEFAULT_ADDRESSPROOF = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSPROOF = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DOJ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DOJ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EDUCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDUCATION = "BBBBBBBBBB";

    private static final String DEFAULT_REFERREDBY = "AAAAAAAAAA";
    private static final String UPDATED_REFERREDBY = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeInfoMockMvc;

    private EmployeeInfo employeeInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeInfo createEntity(EntityManager em) {
        EmployeeInfo employeeInfo = new EmployeeInfo()
            .name(DEFAULT_NAME)
            .age(DEFAULT_AGE)
            .gender(DEFAULT_GENDER)
            .addressproof(DEFAULT_ADDRESSPROOF)
            .photo(DEFAULT_PHOTO)
            .doj(DEFAULT_DOJ)
            .education(DEFAULT_EDUCATION)
            .referredby(DEFAULT_REFERREDBY)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return employeeInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeInfo createUpdatedEntity(EntityManager em) {
        EmployeeInfo employeeInfo = new EmployeeInfo()
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .addressproof(UPDATED_ADDRESSPROOF)
            .photo(UPDATED_PHOTO)
            .doj(UPDATED_DOJ)
            .education(UPDATED_EDUCATION)
            .referredby(UPDATED_REFERREDBY)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return employeeInfo;
    }

    @BeforeEach
    public void initTest() {
        employeeInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeInfo() throws Exception {
        int databaseSizeBeforeCreate = employeeInfoRepository.findAll().size();
        // Create the EmployeeInfo
        restEmployeeInfoMockMvc.perform(post("/api/employee-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeInfo)))
            .andExpect(status().isCreated());

        // Validate the EmployeeInfo in the database
        List<EmployeeInfo> employeeInfoList = employeeInfoRepository.findAll();
        assertThat(employeeInfoList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeInfo testEmployeeInfo = employeeInfoList.get(employeeInfoList.size() - 1);
        assertThat(testEmployeeInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmployeeInfo.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testEmployeeInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testEmployeeInfo.getAddressproof()).isEqualTo(DEFAULT_ADDRESSPROOF);
        assertThat(testEmployeeInfo.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testEmployeeInfo.getDoj()).isEqualTo(DEFAULT_DOJ);
        assertThat(testEmployeeInfo.getEducation()).isEqualTo(DEFAULT_EDUCATION);
        assertThat(testEmployeeInfo.getReferredby()).isEqualTo(DEFAULT_REFERREDBY);
        assertThat(testEmployeeInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployeeInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmployeeInfo.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testEmployeeInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmployeeInfo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createEmployeeInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeInfoRepository.findAll().size();

        // Create the EmployeeInfo with an existing ID
        employeeInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeInfoMockMvc.perform(post("/api/employee-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeInfo)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeInfo in the database
        List<EmployeeInfo> employeeInfoList = employeeInfoRepository.findAll();
        assertThat(employeeInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeInfos() throws Exception {
        // Initialize the database
        employeeInfoRepository.saveAndFlush(employeeInfo);

        // Get all the employeeInfoList
        restEmployeeInfoMockMvc.perform(get("/api/employee-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].addressproof").value(hasItem(DEFAULT_ADDRESSPROOF)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].doj").value(hasItem(DEFAULT_DOJ.toString())))
            .andExpect(jsonPath("$.[*].education").value(hasItem(DEFAULT_EDUCATION)))
            .andExpect(jsonPath("$.[*].referredby").value(hasItem(DEFAULT_REFERREDBY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())));
    }
    
    @Test
    @Transactional
    public void getEmployeeInfo() throws Exception {
        // Initialize the database
        employeeInfoRepository.saveAndFlush(employeeInfo);

        // Get the employeeInfo
        restEmployeeInfoMockMvc.perform(get("/api/employee-infos/{id}", employeeInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.addressproof").value(DEFAULT_ADDRESSPROOF))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO))
            .andExpect(jsonPath("$.doj").value(DEFAULT_DOJ.toString()))
            .andExpect(jsonPath("$.education").value(DEFAULT_EDUCATION))
            .andExpect(jsonPath("$.referredby").value(DEFAULT_REFERREDBY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingEmployeeInfo() throws Exception {
        // Get the employeeInfo
        restEmployeeInfoMockMvc.perform(get("/api/employee-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeInfo() throws Exception {
        // Initialize the database
        employeeInfoService.save(employeeInfo);

        int databaseSizeBeforeUpdate = employeeInfoRepository.findAll().size();

        // Update the employeeInfo
        EmployeeInfo updatedEmployeeInfo = employeeInfoRepository.findById(employeeInfo.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeInfo are not directly saved in db
        em.detach(updatedEmployeeInfo);
        updatedEmployeeInfo
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .addressproof(UPDATED_ADDRESSPROOF)
            .photo(UPDATED_PHOTO)
            .doj(UPDATED_DOJ)
            .education(UPDATED_EDUCATION)
            .referredby(UPDATED_REFERREDBY)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restEmployeeInfoMockMvc.perform(put("/api/employee-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeInfo)))
            .andExpect(status().isOk());

        // Validate the EmployeeInfo in the database
        List<EmployeeInfo> employeeInfoList = employeeInfoRepository.findAll();
        assertThat(employeeInfoList).hasSize(databaseSizeBeforeUpdate);
        EmployeeInfo testEmployeeInfo = employeeInfoList.get(employeeInfoList.size() - 1);
        assertThat(testEmployeeInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmployeeInfo.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testEmployeeInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testEmployeeInfo.getAddressproof()).isEqualTo(UPDATED_ADDRESSPROOF);
        assertThat(testEmployeeInfo.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testEmployeeInfo.getDoj()).isEqualTo(UPDATED_DOJ);
        assertThat(testEmployeeInfo.getEducation()).isEqualTo(UPDATED_EDUCATION);
        assertThat(testEmployeeInfo.getReferredby()).isEqualTo(UPDATED_REFERREDBY);
        assertThat(testEmployeeInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmployeeInfo.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testEmployeeInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployeeInfo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeInfo() throws Exception {
        int databaseSizeBeforeUpdate = employeeInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeInfoMockMvc.perform(put("/api/employee-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeInfo)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeInfo in the database
        List<EmployeeInfo> employeeInfoList = employeeInfoRepository.findAll();
        assertThat(employeeInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeInfo() throws Exception {
        // Initialize the database
        employeeInfoService.save(employeeInfo);

        int databaseSizeBeforeDelete = employeeInfoRepository.findAll().size();

        // Delete the employeeInfo
        restEmployeeInfoMockMvc.perform(delete("/api/employee-infos/{id}", employeeInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeInfo> employeeInfoList = employeeInfoRepository.findAll();
        assertThat(employeeInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
