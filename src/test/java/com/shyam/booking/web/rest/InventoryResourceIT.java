package com.shyam.booking.web.rest;

import com.shyam.booking.BookingApp;
import com.shyam.booking.domain.Inventory;
import com.shyam.booking.repository.InventoryRepository;
import com.shyam.booking.service.InventoryService;

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
 * Integration tests for the {@link InventoryResource} REST controller.
 */
@SpringBootTest(classes = BookingApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InventoryResourceIT {

    private static final Integer DEFAULT_PURCHASE_DATE = 1;
    private static final Integer UPDATED_PURCHASE_DATE = 2;

    private static final String DEFAULT_ITEMDESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ITEMDESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Integer DEFAULT_AVAILABLE = 1;
    private static final Integer UPDATED_AVAILABLE = 2;

    private static final String DEFAULT_ITEMCAT = "AAAAAAAAAA";
    private static final String UPDATED_ITEMCAT = "BBBBBBBBBB";

    private static final Integer DEFAULT_PURCHASE_AMOUNT = 1;
    private static final Integer UPDATED_PURCHASE_AMOUNT = 2;

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
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryMockMvc;

    private Inventory inventory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createEntity(EntityManager em) {
        Inventory inventory = new Inventory()
            .purchaseDate(DEFAULT_PURCHASE_DATE)
            .itemdescription(DEFAULT_ITEMDESCRIPTION)
            .quantity(DEFAULT_QUANTITY)
            .available(DEFAULT_AVAILABLE)
            .itemcat(DEFAULT_ITEMCAT)
            .purchaseAmount(DEFAULT_PURCHASE_AMOUNT)
            .purpose(DEFAULT_PURPOSE)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return inventory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createUpdatedEntity(EntityManager em) {
        Inventory inventory = new Inventory()
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .itemdescription(UPDATED_ITEMDESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .available(UPDATED_AVAILABLE)
            .itemcat(UPDATED_ITEMCAT)
            .purchaseAmount(UPDATED_PURCHASE_AMOUNT)
            .purpose(UPDATED_PURPOSE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return inventory;
    }

    @BeforeEach
    public void initTest() {
        inventory = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventory() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();
        // Create the Inventory
        restInventoryMockMvc.perform(post("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventory)))
            .andExpect(status().isCreated());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate + 1);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testInventory.getItemdescription()).isEqualTo(DEFAULT_ITEMDESCRIPTION);
        assertThat(testInventory.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testInventory.getAvailable()).isEqualTo(DEFAULT_AVAILABLE);
        assertThat(testInventory.getItemcat()).isEqualTo(DEFAULT_ITEMCAT);
        assertThat(testInventory.getPurchaseAmount()).isEqualTo(DEFAULT_PURCHASE_AMOUNT);
        assertThat(testInventory.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testInventory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testInventory.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testInventory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testInventory.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createInventoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();

        // Create the Inventory with an existing ID
        inventory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryMockMvc.perform(post("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventory)))
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventories() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList
        restInventoryMockMvc.perform(get("/api/inventories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE)))
            .andExpect(jsonPath("$.[*].itemdescription").value(hasItem(DEFAULT_ITEMDESCRIPTION)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE)))
            .andExpect(jsonPath("$.[*].itemcat").value(hasItem(DEFAULT_ITEMCAT)))
            .andExpect(jsonPath("$.[*].purchaseAmount").value(hasItem(DEFAULT_PURCHASE_AMOUNT)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())));
    }
    
    @Test
    @Transactional
    public void getInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get the inventory
        restInventoryMockMvc.perform(get("/api/inventories/{id}", inventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventory.getId().intValue()))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE))
            .andExpect(jsonPath("$.itemdescription").value(DEFAULT_ITEMDESCRIPTION))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE))
            .andExpect(jsonPath("$.itemcat").value(DEFAULT_ITEMCAT))
            .andExpect(jsonPath("$.purchaseAmount").value(DEFAULT_PURCHASE_AMOUNT))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingInventory() throws Exception {
        // Get the inventory
        restInventoryMockMvc.perform(get("/api/inventories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventory() throws Exception {
        // Initialize the database
        inventoryService.save(inventory);

        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Update the inventory
        Inventory updatedInventory = inventoryRepository.findById(inventory.getId()).get();
        // Disconnect from session so that the updates on updatedInventory are not directly saved in db
        em.detach(updatedInventory);
        updatedInventory
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .itemdescription(UPDATED_ITEMDESCRIPTION)
            .quantity(UPDATED_QUANTITY)
            .available(UPDATED_AVAILABLE)
            .itemcat(UPDATED_ITEMCAT)
            .purchaseAmount(UPDATED_PURCHASE_AMOUNT)
            .purpose(UPDATED_PURPOSE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restInventoryMockMvc.perform(put("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventory)))
            .andExpect(status().isOk());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testInventory.getItemdescription()).isEqualTo(UPDATED_ITEMDESCRIPTION);
        assertThat(testInventory.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testInventory.getAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testInventory.getItemcat()).isEqualTo(UPDATED_ITEMCAT);
        assertThat(testInventory.getPurchaseAmount()).isEqualTo(UPDATED_PURCHASE_AMOUNT);
        assertThat(testInventory.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testInventory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testInventory.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testInventory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testInventory.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryMockMvc.perform(put("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventory)))
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventory() throws Exception {
        // Initialize the database
        inventoryService.save(inventory);

        int databaseSizeBeforeDelete = inventoryRepository.findAll().size();

        // Delete the inventory
        restInventoryMockMvc.perform(delete("/api/inventories/{id}", inventory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
