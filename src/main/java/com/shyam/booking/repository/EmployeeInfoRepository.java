package com.shyam.booking.repository;

import com.shyam.booking.domain.EmployeeInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the EmployeeInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Long> {

    @Query("select employeeInfo from EmployeeInfo employeeInfo where employeeInfo.user.login = ?#{principal.username}")
    List<EmployeeInfo> findByUserIsCurrentUser();
}
