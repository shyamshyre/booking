package com.shyam.booking.web.rest;

import com.shyam.booking.BookingApp;
import com.shyam.booking.domain.Finance;
import com.shyam.booking.repository.FinanceRepository;
import com.shyam.booking.service.FinanceService;

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
 * Integration tests for the {@link FinanceResource} REST controller.
 */
@SpringBootTest(classes = BookingApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FinanceResourceIT {

    private static final Integer DEFAULT_CREDITED = 1;
    private static final Integer UPDATED_CREDITED = 2;

    private static final Integer DEFAULT_DEBITED = 1;
    private static final Integer UPDATED_DEBITED = 2;

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    @Autowired
    private FinanceRepository financeRepository;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFinanceMockMvc;

    private Finance finance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Finance createEntity(EntityManager em) {
        Finance finance = new Finance()
            .credited(DEFAULT_CREDITED)
            .debited(DEFAULT_DEBITED)
            .purpose(DEFAULT_PURPOSE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return finance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Finance createUpdatedEntity(EntityManager em) {
        Finance finance = new Finance()
            .credited(UPDATED_CREDITED)
            .debited(UPDATED_DEBITED)
            .purpose(UPDATED_PURPOSE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return finance;
    }

    @BeforeEach
    public void initTest() {
        finance = createEntity(em);
    }

    @Test
    @Transactional
    public void createFinance() throws Exception {
        int databaseSizeBeforeCreate = financeRepository.findAll().size();
        // Create the Finance
        restFinanceMockMvc.perform(post("/api/finances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finance)))
            .andExpect(status().isCreated());

        // Validate the Finance in the database
        List<Finance> financeList = financeRepository.findAll();
        assertThat(financeList).hasSize(databaseSizeBeforeCreate + 1);
        Finance testFinance = financeList.get(financeList.size() - 1);
        assertThat(testFinance.getCredited()).isEqualTo(DEFAULT_CREDITED);
        assertThat(testFinance.getDebited()).isEqualTo(DEFAULT_DEBITED);
        assertThat(testFinance.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testFinance.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFinance.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testFinance.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFinance.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createFinanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = financeRepository.findAll().size();

        // Create the Finance with an existing ID
        finance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinanceMockMvc.perform(post("/api/finances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finance)))
            .andExpect(status().isBadRequest());

        // Validate the Finance in the database
        List<Finance> financeList = financeRepository.findAll();
        assertThat(financeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFinances() throws Exception {
        // Initialize the database
        financeRepository.saveAndFlush(finance);

        // Get all the financeList
        restFinanceMockMvc.perform(get("/api/finances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finance.getId().intValue())))
            .andExpect(jsonPath("$.[*].credited").value(hasItem(DEFAULT_CREDITED)))
            .andExpect(jsonPath("$.[*].debited").value(hasItem(DEFAULT_DEBITED)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())));
    }
    
    @Test
    @Transactional
    public void getFinance() throws Exception {
        // Initialize the database
        financeRepository.saveAndFlush(finance);

        // Get the finance
        restFinanceMockMvc.perform(get("/api/finances/{id}", finance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(finance.getId().intValue()))
            .andExpect(jsonPath("$.credited").value(DEFAULT_CREDITED))
            .andExpect(jsonPath("$.debited").value(DEFAULT_DEBITED))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFinance() throws Exception {
        // Get the finance
        restFinanceMockMvc.perform(get("/api/finances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFinance() throws Exception {
        // Initialize the database
        financeService.save(finance);

        int databaseSizeBeforeUpdate = financeRepository.findAll().size();

        // Update the finance
        Finance updatedFinance = financeRepository.findById(finance.getId()).get();
        // Disconnect from session so that the updates on updatedFinance are not directly saved in db
        em.detach(updatedFinance);
        updatedFinance
            .credited(UPDATED_CREDITED)
            .debited(UPDATED_DEBITED)
            .purpose(UPDATED_PURPOSE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restFinanceMockMvc.perform(put("/api/finances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFinance)))
            .andExpect(status().isOk());

        // Validate the Finance in the database
        List<Finance> financeList = financeRepository.findAll();
        assertThat(financeList).hasSize(databaseSizeBeforeUpdate);
        Finance testFinance = financeList.get(financeList.size() - 1);
        assertThat(testFinance.getCredited()).isEqualTo(UPDATED_CREDITED);
        assertThat(testFinance.getDebited()).isEqualTo(UPDATED_DEBITED);
        assertThat(testFinance.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testFinance.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFinance.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testFinance.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFinance.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingFinance() throws Exception {
        int databaseSizeBeforeUpdate = financeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinanceMockMvc.perform(put("/api/finances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finance)))
            .andExpect(status().isBadRequest());

        // Validate the Finance in the database
        List<Finance> financeList = financeRepository.findAll();
        assertThat(financeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFinance() throws Exception {
        // Initialize the database
        financeService.save(finance);

        int databaseSizeBeforeDelete = financeRepository.findAll().size();

        // Delete the finance
        restFinanceMockMvc.perform(delete("/api/finances/{id}", finance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Finance> financeList = financeRepository.findAll();
        assertThat(financeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
