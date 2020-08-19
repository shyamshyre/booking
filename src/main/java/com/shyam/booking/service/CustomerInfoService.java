package com.shyam.booking.service;

import com.shyam.booking.domain.CustomerInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CustomerInfo}.
 */
public interface CustomerInfoService {

    /**
     * Save a customerInfo.
     *
     * @param customerInfo the entity to save.
     * @return the persisted entity.
     */
    CustomerInfo save(CustomerInfo customerInfo);

    /**
     * Get all the customerInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CustomerInfo> findAll(Pageable pageable);


    /**
     * Get the "id" customerInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerInfo> findOne(Long id);

    /**
     * Delete the "id" customerInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
