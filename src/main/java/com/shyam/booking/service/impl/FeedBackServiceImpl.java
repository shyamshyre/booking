package com.shyam.booking.service.impl;

import com.shyam.booking.service.FeedBackService;
import com.shyam.booking.domain.FeedBack;
import com.shyam.booking.repository.FeedBackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FeedBack}.
 */
@Service
@Transactional
public class FeedBackServiceImpl implements FeedBackService {

    private final Logger log = LoggerFactory.getLogger(FeedBackServiceImpl.class);

    private final FeedBackRepository feedBackRepository;

    public FeedBackServiceImpl(FeedBackRepository feedBackRepository) {
        this.feedBackRepository = feedBackRepository;
    }

    @Override
    public FeedBack save(FeedBack feedBack) {
        log.debug("Request to save FeedBack : {}", feedBack);
        return feedBackRepository.save(feedBack);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedBack> findAll(Pageable pageable) {
        log.debug("Request to get all FeedBacks");
        return feedBackRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<FeedBack> findOne(Long id) {
        log.debug("Request to get FeedBack : {}", id);
        return feedBackRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeedBack : {}", id);
        feedBackRepository.deleteById(id);
    }
}
