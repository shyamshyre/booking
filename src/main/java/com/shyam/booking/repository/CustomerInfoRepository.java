package com.shyam.booking.repository;

import com.shyam.booking.domain.CustomerInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CustomerInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {

    @Query("select customerInfo from CustomerInfo customerInfo where customerInfo.user.login = ?#{principal.username}")
    List<CustomerInfo> findByUserIsCurrentUser();
}
