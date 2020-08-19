package com.shyam.booking.web.rest;

import com.shyam.booking.BookingApp;
import com.shyam.booking.domain.CustomerInfo;
import com.shyam.booking.repository.CustomerInfoRepository;
import com.shyam.booking.service.CustomerInfoService;

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
import com.shyam.booking.domain.enumeration.MStatus;
/**
 * Integration tests for the {@link CustomerInfoResource} REST controller.
 */
@SpringBootTest(classes = BookingApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerInfoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final Long DEFAULT_PHNO = 1L;
    private static final Long UPDATED_PHNO = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final GenderType DEFAULT_GENDER = GenderType.MALE;
    private static final GenderType UPDATED_GENDER = GenderType.FEMALE;

    private static final MStatus DEFAULT_STATUS = MStatus.SINGLE;
    private static final MStatus UPDATED_STATUS = MStatus.MARRIED;

    private static final String DEFAULT_ADDRESSPROOF = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSPROOF = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private CustomerInfoService customerInfoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerInfoMockMvc;

    private CustomerInfo customerInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerInfo createEntity(EntityManager em) {
        CustomerInfo customerInfo = new CustomerInfo()
            .name(DEFAULT_NAME)
            .photo(DEFAULT_PHOTO)
            .age(DEFAULT_AGE)
            .phno(DEFAULT_PHNO)
            .email(DEFAULT_EMAIL)
            .gender(DEFAULT_GENDER)
            .status(DEFAULT_STATUS)
            .addressproof(DEFAULT_ADDRESSPROOF)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return customerInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerInfo createUpdatedEntity(EntityManager em) {
        CustomerInfo customerInfo = new CustomerInfo()
            .name(UPDATED_NAME)
            .photo(UPDATED_PHOTO)
            .age(UPDATED_AGE)
            .phno(UPDATED_PHNO)
            .email(UPDATED_EMAIL)
            .gender(UPDATED_GENDER)
            .status(UPDATED_STATUS)
            .addressproof(UPDATED_ADDRESSPROOF)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return customerInfo;
    }

    @BeforeEach
    public void initTest() {
        customerInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerInfo() throws Exception {
        int databaseSizeBeforeCreate = customerInfoRepository.findAll().size();
        // Create the CustomerInfo
        restCustomerInfoMockMvc.perform(post("/api/customer-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerInfo)))
            .andExpect(status().isCreated());

        // Validate the CustomerInfo in the database
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerInfo testCustomerInfo = customerInfoList.get(customerInfoList.size() - 1);
        assertThat(testCustomerInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerInfo.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testCustomerInfo.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testCustomerInfo.getPhno()).isEqualTo(DEFAULT_PHNO);
        assertThat(testCustomerInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomerInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testCustomerInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustomerInfo.getAddressproof()).isEqualTo(DEFAULT_ADDRESSPROOF);
        assertThat(testCustomerInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCustomerInfo.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testCustomerInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCustomerInfo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createCustomerInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerInfoRepository.findAll().size();

        // Create the CustomerInfo with an existing ID
        customerInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerInfoMockMvc.perform(post("/api/customer-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerInfo)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerInfo in the database
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomerInfos() throws Exception {
        // Initialize the database
        customerInfoRepository.saveAndFlush(customerInfo);

        // Get all the customerInfoList
        restCustomerInfoMockMvc.perform(get("/api/customer-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].phno").value(hasItem(DEFAULT_PHNO.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].addressproof").value(hasItem(DEFAULT_ADDRESSPROOF)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())));
    }
    
    @Test
    @Transactional
    public void getCustomerInfo() throws Exception {
        // Initialize the database
        customerInfoRepository.saveAndFlush(customerInfo);

        // Get the customerInfo
        restCustomerInfoMockMvc.perform(get("/api/customer-infos/{id}", customerInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.phno").value(DEFAULT_PHNO.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.addressproof").value(DEFAULT_ADDRESSPROOF))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCustomerInfo() throws Exception {
        // Get the customerInfo
        restCustomerInfoMockMvc.perform(get("/api/customer-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerInfo() throws Exception {
        // Initialize the database
        customerInfoService.save(customerInfo);

        int databaseSizeBeforeUpdate = customerInfoRepository.findAll().size();

        // Update the customerInfo
        CustomerInfo updatedCustomerInfo = customerInfoRepository.findById(customerInfo.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerInfo are not directly saved in db
        em.detach(updatedCustomerInfo);
        updatedCustomerInfo
            .name(UPDATED_NAME)
            .photo(UPDATED_PHOTO)
            .age(UPDATED_AGE)
            .phno(UPDATED_PHNO)
            .email(UPDATED_EMAIL)
            .gender(UPDATED_GENDER)
            .status(UPDATED_STATUS)
            .addressproof(UPDATED_ADDRESSPROOF)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restCustomerInfoMockMvc.perform(put("/api/customer-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerInfo)))
            .andExpect(status().isOk());

        // Validate the CustomerInfo in the database
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeUpdate);
        CustomerInfo testCustomerInfo = customerInfoList.get(customerInfoList.size() - 1);
        assertThat(testCustomerInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerInfo.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCustomerInfo.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testCustomerInfo.getPhno()).isEqualTo(UPDATED_PHNO);
        assertThat(testCustomerInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testCustomerInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomerInfo.getAddressproof()).isEqualTo(UPDATED_ADDRESSPROOF);
        assertThat(testCustomerInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCustomerInfo.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testCustomerInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCustomerInfo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerInfo() throws Exception {
        int databaseSizeBeforeUpdate = customerInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerInfoMockMvc.perform(put("/api/customer-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerInfo)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerInfo in the database
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerInfo() throws Exception {
        // Initialize the database
        customerInfoService.save(customerInfo);

        int databaseSizeBeforeDelete = customerInfoRepository.findAll().size();

        // Delete the customerInfo
        restCustomerInfoMockMvc.perform(delete("/api/customer-infos/{id}", customerInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
