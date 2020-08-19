package com.shyam.booking.repository;

import com.shyam.booking.domain.FeedBack;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FeedBack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
}
