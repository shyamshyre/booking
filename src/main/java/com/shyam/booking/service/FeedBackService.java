package com.shyam.booking.service;

import com.shyam.booking.domain.FeedBack;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link FeedBack}.
 */
public interface FeedBackService {

    /**
     * Save a feedBack.
     *
     * @param feedBack the entity to save.
     * @return the persisted entity.
     */
    FeedBack save(FeedBack feedBack);

    /**
     * Get all the feedBacks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeedBack> findAll(Pageable pageable);


    /**
     * Get the "id" feedBack.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FeedBack> findOne(Long id);

    /**
     * Delete the "id" feedBack.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
