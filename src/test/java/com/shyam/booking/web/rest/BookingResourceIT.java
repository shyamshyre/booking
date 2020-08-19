package com.shyam.booking.web.rest;

import com.shyam.booking.BookingApp;
import com.shyam.booking.domain.Booking;
import com.shyam.booking.repository.BookingRepository;
import com.shyam.booking.service.BookingService;

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

import com.shyam.booking.domain.enumeration.BookingType;
import com.shyam.booking.domain.enumeration.RoomType;
/**
 * Integration tests for the {@link BookingResource} REST controller.
 */
@SpringBootTest(classes = BookingApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BookingResourceIT {

    private static final BookingType DEFAULT_BOOKING_TYPE = BookingType.ONLINE;
    private static final BookingType UPDATED_BOOKING_TYPE = BookingType.OFFLINE;

    private static final Integer DEFAULT_NOOF_PEOPLE = 1;
    private static final Integer UPDATED_NOOF_PEOPLE = 2;

    private static final String DEFAULT_COMING_FROM = "AAAAAAAAAA";
    private static final String UPDATED_COMING_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_VISIT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_VISIT_PURPOSE = "BBBBBBBBBB";

    private static final RoomType DEFAULT_ROOM_CATEGORY = RoomType.AC;
    private static final RoomType UPDATED_ROOM_CATEGORY = RoomType.NONAC;

    private static final Integer DEFAULT_TOTAL_AMOUNT = 1;
    private static final Integer UPDATED_TOTAL_AMOUNT = 2;

    private static final Integer DEFAULT_ADVANCE = 1;
    private static final Integer UPDATED_ADVANCE = 2;

    private static final Integer DEFAULT_BALANCE_AMOUNT = 1;
    private static final Integer UPDATED_BALANCE_AMOUNT = 2;

    private static final Float DEFAULT_GST = 1F;
    private static final Float UPDATED_GST = 2F;

    private static final Instant DEFAULT_BOOKING_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOOKING_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BOOKING_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOOKING_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookingMockMvc;

    private Booking booking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Booking createEntity(EntityManager em) {
        Booking booking = new Booking()
            .bookingType(DEFAULT_BOOKING_TYPE)
            .noofPeople(DEFAULT_NOOF_PEOPLE)
            .comingFrom(DEFAULT_COMING_FROM)
            .visitPurpose(DEFAULT_VISIT_PURPOSE)
            .roomCategory(DEFAULT_ROOM_CATEGORY)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .advance(DEFAULT_ADVANCE)
            .balanceAmount(DEFAULT_BALANCE_AMOUNT)
            .gst(DEFAULT_GST)
            .bookingFrom(DEFAULT_BOOKING_FROM)
            .bookingTo(DEFAULT_BOOKING_TO)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return booking;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Booking createUpdatedEntity(EntityManager em) {
        Booking booking = new Booking()
            .bookingType(UPDATED_BOOKING_TYPE)
            .noofPeople(UPDATED_NOOF_PEOPLE)
            .comingFrom(UPDATED_COMING_FROM)
            .visitPurpose(UPDATED_VISIT_PURPOSE)
            .roomCategory(UPDATED_ROOM_CATEGORY)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .advance(UPDATED_ADVANCE)
            .balanceAmount(UPDATED_BALANCE_AMOUNT)
            .gst(UPDATED_GST)
            .bookingFrom(UPDATED_BOOKING_FROM)
            .bookingTo(UPDATED_BOOKING_TO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return booking;
    }

    @BeforeEach
    public void initTest() {
        booking = createEntity(em);
    }

    @Test
    @Transactional
    public void createBooking() throws Exception {
        int databaseSizeBeforeCreate = bookingRepository.findAll().size();
        // Create the Booking
        restBookingMockMvc.perform(post("/api/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isCreated());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeCreate + 1);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getBookingType()).isEqualTo(DEFAULT_BOOKING_TYPE);
        assertThat(testBooking.getNoofPeople()).isEqualTo(DEFAULT_NOOF_PEOPLE);
        assertThat(testBooking.getComingFrom()).isEqualTo(DEFAULT_COMING_FROM);
        assertThat(testBooking.getVisitPurpose()).isEqualTo(DEFAULT_VISIT_PURPOSE);
        assertThat(testBooking.getRoomCategory()).isEqualTo(DEFAULT_ROOM_CATEGORY);
        assertThat(testBooking.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testBooking.getAdvance()).isEqualTo(DEFAULT_ADVANCE);
        assertThat(testBooking.getBalanceAmount()).isEqualTo(DEFAULT_BALANCE_AMOUNT);
        assertThat(testBooking.getGst()).isEqualTo(DEFAULT_GST);
        assertThat(testBooking.getBookingFrom()).isEqualTo(DEFAULT_BOOKING_FROM);
        assertThat(testBooking.getBookingTo()).isEqualTo(DEFAULT_BOOKING_TO);
        assertThat(testBooking.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBooking.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testBooking.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBooking.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createBookingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookingRepository.findAll().size();

        // Create the Booking with an existing ID
        booking.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingMockMvc.perform(post("/api/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBookings() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList
        restBookingMockMvc.perform(get("/api/bookings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(booking.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookingType").value(hasItem(DEFAULT_BOOKING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].noofPeople").value(hasItem(DEFAULT_NOOF_PEOPLE)))
            .andExpect(jsonPath("$.[*].comingFrom").value(hasItem(DEFAULT_COMING_FROM)))
            .andExpect(jsonPath("$.[*].visitPurpose").value(hasItem(DEFAULT_VISIT_PURPOSE)))
            .andExpect(jsonPath("$.[*].roomCategory").value(hasItem(DEFAULT_ROOM_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT)))
            .andExpect(jsonPath("$.[*].advance").value(hasItem(DEFAULT_ADVANCE)))
            .andExpect(jsonPath("$.[*].balanceAmount").value(hasItem(DEFAULT_BALANCE_AMOUNT)))
            .andExpect(jsonPath("$.[*].gst").value(hasItem(DEFAULT_GST.doubleValue())))
            .andExpect(jsonPath("$.[*].bookingFrom").value(hasItem(DEFAULT_BOOKING_FROM.toString())))
            .andExpect(jsonPath("$.[*].bookingTo").value(hasItem(DEFAULT_BOOKING_TO.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())));
    }
    
    @Test
    @Transactional
    public void getBooking() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get the booking
        restBookingMockMvc.perform(get("/api/bookings/{id}", booking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(booking.getId().intValue()))
            .andExpect(jsonPath("$.bookingType").value(DEFAULT_BOOKING_TYPE.toString()))
            .andExpect(jsonPath("$.noofPeople").value(DEFAULT_NOOF_PEOPLE))
            .andExpect(jsonPath("$.comingFrom").value(DEFAULT_COMING_FROM))
            .andExpect(jsonPath("$.visitPurpose").value(DEFAULT_VISIT_PURPOSE))
            .andExpect(jsonPath("$.roomCategory").value(DEFAULT_ROOM_CATEGORY.toString()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT))
            .andExpect(jsonPath("$.advance").value(DEFAULT_ADVANCE))
            .andExpect(jsonPath("$.balanceAmount").value(DEFAULT_BALANCE_AMOUNT))
            .andExpect(jsonPath("$.gst").value(DEFAULT_GST.doubleValue()))
            .andExpect(jsonPath("$.bookingFrom").value(DEFAULT_BOOKING_FROM.toString()))
            .andExpect(jsonPath("$.bookingTo").value(DEFAULT_BOOKING_TO.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingBooking() throws Exception {
        // Get the booking
        restBookingMockMvc.perform(get("/api/bookings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBooking() throws Exception {
        // Initialize the database
        bookingService.save(booking);

        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();

        // Update the booking
        Booking updatedBooking = bookingRepository.findById(booking.getId()).get();
        // Disconnect from session so that the updates on updatedBooking are not directly saved in db
        em.detach(updatedBooking);
        updatedBooking
            .bookingType(UPDATED_BOOKING_TYPE)
            .noofPeople(UPDATED_NOOF_PEOPLE)
            .comingFrom(UPDATED_COMING_FROM)
            .visitPurpose(UPDATED_VISIT_PURPOSE)
            .roomCategory(UPDATED_ROOM_CATEGORY)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .advance(UPDATED_ADVANCE)
            .balanceAmount(UPDATED_BALANCE_AMOUNT)
            .gst(UPDATED_GST)
            .bookingFrom(UPDATED_BOOKING_FROM)
            .bookingTo(UPDATED_BOOKING_TO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restBookingMockMvc.perform(put("/api/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBooking)))
            .andExpect(status().isOk());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getBookingType()).isEqualTo(UPDATED_BOOKING_TYPE);
        assertThat(testBooking.getNoofPeople()).isEqualTo(UPDATED_NOOF_PEOPLE);
        assertThat(testBooking.getComingFrom()).isEqualTo(UPDATED_COMING_FROM);
        assertThat(testBooking.getVisitPurpose()).isEqualTo(UPDATED_VISIT_PURPOSE);
        assertThat(testBooking.getRoomCategory()).isEqualTo(UPDATED_ROOM_CATEGORY);
        assertThat(testBooking.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testBooking.getAdvance()).isEqualTo(UPDATED_ADVANCE);
        assertThat(testBooking.getBalanceAmount()).isEqualTo(UPDATED_BALANCE_AMOUNT);
        assertThat(testBooking.getGst()).isEqualTo(UPDATED_GST);
        assertThat(testBooking.getBookingFrom()).isEqualTo(UPDATED_BOOKING_FROM);
        assertThat(testBooking.getBookingTo()).isEqualTo(UPDATED_BOOKING_TO);
        assertThat(testBooking.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testBooking.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testBooking.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBooking.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingMockMvc.perform(put("/api/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBooking() throws Exception {
        // Initialize the database
        bookingService.save(booking);

        int databaseSizeBeforeDelete = bookingRepository.findAll().size();

        // Delete the booking
        restBookingMockMvc.perform(delete("/api/bookings/{id}", booking.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
