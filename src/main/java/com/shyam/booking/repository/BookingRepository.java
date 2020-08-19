package com.shyam.booking.repository;

import com.shyam.booking.domain.Booking;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Booking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select booking from Booking booking where booking.user.login = ?#{principal.username}")
    List<Booking> findByUserIsCurrentUser();
}
