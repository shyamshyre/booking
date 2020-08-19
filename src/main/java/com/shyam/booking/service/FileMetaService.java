package com.shyam.booking.service;

import com.shyam.booking.domain.FileMeta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link FileMeta}.
 */
public interface FileMetaService {

    /**
     * Save a fileMeta.
     *
     * @param fileMeta the entity to save.
     * @return the persisted entity.
     */
    FileMeta save(FileMeta fileMeta);

    /**
     * Get all the fileMetas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FileMeta> findAll(Pageable pageable);


    /**
     * Get the "id" fileMeta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileMeta> findOne(Long id);

    /**
     * Delete the "id" fileMeta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
