package com.shyam.booking.service.impl;

import com.shyam.booking.service.CustomerInfoService;
import com.shyam.booking.domain.CustomerInfo;
import com.shyam.booking.repository.CustomerInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CustomerInfo}.
 */
@Service
@Transactional
public class CustomerInfoServiceImpl implements CustomerInfoService {

    private final Logger log = LoggerFactory.getLogger(CustomerInfoServiceImpl.class);

    private final CustomerInfoRepository customerInfoRepository;

    public CustomerInfoServiceImpl(CustomerInfoRepository customerInfoRepository) {
        this.customerInfoRepository = customerInfoRepository;
    }

    @Override
    public CustomerInfo save(CustomerInfo customerInfo) {
        log.debug("Request to save CustomerInfo : {}", customerInfo);
        return customerInfoRepository.save(customerInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerInfo> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerInfos");
        return customerInfoRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerInfo> findOne(Long id) {
        log.debug("Request to get CustomerInfo : {}", id);
        return customerInfoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerInfo : {}", id);
        customerInfoRepository.deleteById(id);
    }
}
