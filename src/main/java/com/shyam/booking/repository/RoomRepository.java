package com.shyam.booking.repository;

import com.shyam.booking.domain.Room;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Room entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select room from Room room where room.user.login = ?#{principal.username}")
    List<Room> findByUserIsCurrentUser();
}
