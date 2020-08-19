package com.shyam.booking.web.rest;

import com.shyam.booking.BookingApp;
import com.shyam.booking.domain.FeedBack;
import com.shyam.booking.repository.FeedBackRepository;
import com.shyam.booking.service.FeedBackService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shyam.booking.domain.enumeration.FeedbackStatus;
/**
 * Integration tests for the {@link FeedBackResource} REST controller.
 */
@SpringBootTest(classes = BookingApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FeedBackResourceIT {

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final FeedbackStatus DEFAULT_FEEDBACK_STATUS = FeedbackStatus.GOOD;
    private static final FeedbackStatus UPDATED_FEEDBACK_STATUS = FeedbackStatus.BAD;

    @Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private FeedBackService feedBackService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedBackMockMvc;

    private FeedBack feedBack;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedBack createEntity(EntityManager em) {
        FeedBack feedBack = new FeedBack()
            .comments(DEFAULT_COMMENTS)
            .feedbackStatus(DEFAULT_FEEDBACK_STATUS);
        return feedBack;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedBack createUpdatedEntity(EntityManager em) {
        FeedBack feedBack = new FeedBack()
            .comments(UPDATED_COMMENTS)
            .feedbackStatus(UPDATED_FEEDBACK_STATUS);
        return feedBack;
    }

    @BeforeEach
    public void initTest() {
        feedBack = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeedBack() throws Exception {
        int databaseSizeBeforeCreate = feedBackRepository.findAll().size();
        // Create the FeedBack
        restFeedBackMockMvc.perform(post("/api/feed-backs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedBack)))
            .andExpect(status().isCreated());

        // Validate the FeedBack in the database
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeCreate + 1);
        FeedBack testFeedBack = feedBackList.get(feedBackList.size() - 1);
        assertThat(testFeedBack.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testFeedBack.getFeedbackStatus()).isEqualTo(DEFAULT_FEEDBACK_STATUS);
    }

    @Test
    @Transactional
    public void createFeedBackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feedBackRepository.findAll().size();

        // Create the FeedBack with an existing ID
        feedBack.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedBackMockMvc.perform(post("/api/feed-backs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedBack)))
            .andExpect(status().isBadRequest());

        // Validate the FeedBack in the database
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFeedBacks() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get all the feedBackList
        restFeedBackMockMvc.perform(get("/api/feed-backs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedBack.getId().intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].feedbackStatus").value(hasItem(DEFAULT_FEEDBACK_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getFeedBack() throws Exception {
        // Initialize the database
        feedBackRepository.saveAndFlush(feedBack);

        // Get the feedBack
        restFeedBackMockMvc.perform(get("/api/feed-backs/{id}", feedBack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedBack.getId().intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.feedbackStatus").value(DEFAULT_FEEDBACK_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFeedBack() throws Exception {
        // Get the feedBack
        restFeedBackMockMvc.perform(get("/api/feed-backs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeedBack() throws Exception {
        // Initialize the database
        feedBackService.save(feedBack);

        int databaseSizeBeforeUpdate = feedBackRepository.findAll().size();

        // Update the feedBack
        FeedBack updatedFeedBack = feedBackRepository.findById(feedBack.getId()).get();
        // Disconnect from session so that the updates on updatedFeedBack are not directly saved in db
        em.detach(updatedFeedBack);
        updatedFeedBack
            .comments(UPDATED_COMMENTS)
            .feedbackStatus(UPDATED_FEEDBACK_STATUS);

        restFeedBackMockMvc.perform(put("/api/feed-backs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeedBack)))
            .andExpect(status().isOk());

        // Validate the FeedBack in the database
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeUpdate);
        FeedBack testFeedBack = feedBackList.get(feedBackList.size() - 1);
        assertThat(testFeedBack.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testFeedBack.getFeedbackStatus()).isEqualTo(UPDATED_FEEDBACK_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingFeedBack() throws Exception {
        int databaseSizeBeforeUpdate = feedBackRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedBackMockMvc.perform(put("/api/feed-backs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedBack)))
            .andExpect(status().isBadRequest());

        // Validate the FeedBack in the database
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeedBack() throws Exception {
        // Initialize the database
        feedBackService.save(feedBack);

        int databaseSizeBeforeDelete = feedBackRepository.findAll().size();

        // Delete the feedBack
        restFeedBackMockMvc.perform(delete("/api/feed-backs/{id}", feedBack.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
