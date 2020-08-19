package com.shyam.booking.service.impl;

import com.shyam.booking.service.FileMetaService;
import com.shyam.booking.domain.FileMeta;
import com.shyam.booking.repository.FileMetaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FileMeta}.
 */
@Service
@Transactional
public class FileMetaServiceImpl implements FileMetaService {

    private final Logger log = LoggerFactory.getLogger(FileMetaServiceImpl.class);

    private final FileMetaRepository fileMetaRepository;

    public FileMetaServiceImpl(FileMetaRepository fileMetaRepository) {
        this.fileMetaRepository = fileMetaRepository;
    }

    @Override
    public FileMeta save(FileMeta fileMeta) {
        log.debug("Request to save FileMeta : {}", fileMeta);
        return fileMetaRepository.save(fileMeta);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FileMeta> findAll(Pageable pageable) {
        log.debug("Request to get all FileMetas");
        return fileMetaRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<FileMeta> findOne(Long id) {
        log.debug("Request to get FileMeta : {}", id);
        return fileMetaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileMeta : {}", id);
        fileMetaRepository.deleteById(id);
    }
}
