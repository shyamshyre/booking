package com.shyam.booking.service.impl;

import com.shyam.booking.service.FinanceService;
import com.shyam.booking.domain.Finance;
import com.shyam.booking.repository.FinanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Finance}.
 */
@Service
@Transactional
public class FinanceServiceImpl implements FinanceService {

    private final Logger log = LoggerFactory.getLogger(FinanceServiceImpl.class);

    private final FinanceRepository financeRepository;

    public FinanceServiceImpl(FinanceRepository financeRepository) {
        this.financeRepository = financeRepository;
    }

    @Override
    public Finance save(Finance finance) {
        log.debug("Request to save Finance : {}", finance);
        return financeRepository.save(finance);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Finance> findAll(Pageable pageable) {
        log.debug("Request to get all Finances");
        return financeRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Finance> findOne(Long id) {
        log.debug("Request to get Finance : {}", id);
        return financeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Finance : {}", id);
        financeRepository.deleteById(id);
    }
}
