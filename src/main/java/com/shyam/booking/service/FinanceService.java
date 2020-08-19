package com.shyam.booking.service;

import com.shyam.booking.domain.Finance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Finance}.
 */
public interface FinanceService {

    /**
     * Save a finance.
     *
     * @param finance the entity to save.
     * @return the persisted entity.
     */
    Finance save(Finance finance);

    /**
     * Get all the finances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Finance> findAll(Pageable pageable);


    /**
     * Get the "id" finance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Finance> findOne(Long id);

    /**
     * Delete the "id" finance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
