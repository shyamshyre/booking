package com.shyam.booking.repository;

import com.shyam.booking.domain.Finance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Finance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinanceRepository extends JpaRepository<Finance, Long> {

    @Query("select finance from Finance finance where finance.user.login = ?#{principal.username}")
    List<Finance> findByUserIsCurrentUser();
}
