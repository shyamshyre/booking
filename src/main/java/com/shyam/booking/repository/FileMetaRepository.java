package com.shyam.booking.repository;

import com.shyam.booking.domain.FileMeta;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the FileMeta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileMetaRepository extends JpaRepository<FileMeta, Long> {

    @Query("select fileMeta from FileMeta fileMeta where fileMeta.user.login = ?#{principal.username}")
    List<FileMeta> findByUserIsCurrentUser();
}
