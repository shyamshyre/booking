package com.shyam.booking.repository;

import com.shyam.booking.domain.Inventory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Inventory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("select inventory from Inventory inventory where inventory.user.login = ?#{principal.username}")
    List<Inventory> findByUserIsCurrentUser();
}
