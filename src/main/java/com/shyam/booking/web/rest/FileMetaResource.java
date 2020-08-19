package com.shyam.booking.web.rest;

import com.shyam.booking.domain.FileMeta;
import com.shyam.booking.service.FileMetaService;
import com.shyam.booking.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.shyam.booking.domain.FileMeta}.
 */
@RestController
@RequestMapping("/api")
public class FileMetaResource {

    private final Logger log = LoggerFactory.getLogger(FileMetaResource.class);

    private static final String ENTITY_NAME = "fileMeta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileMetaService fileMetaService;

    public FileMetaResource(FileMetaService fileMetaService) {
        this.fileMetaService = fileMetaService;
    }

    /**
     * {@code POST  /file-metas} : Create a new fileMeta.
     *
     * @param fileMeta the fileMeta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileMeta, or with status {@code 400 (Bad Request)} if the fileMeta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-metas")
    public ResponseEntity<FileMeta> createFileMeta(@RequestBody FileMeta fileMeta) throws URISyntaxException {
        log.debug("REST request to save FileMeta : {}", fileMeta);
        if (fileMeta.getId() != null) {
            throw new BadRequestAlertException("A new fileMeta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileMeta result = fileMetaService.save(fileMeta);
        return ResponseEntity.created(new URI("/api/file-metas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-metas} : Updates an existing fileMeta.
     *
     * @param fileMeta the fileMeta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileMeta,
     * or with status {@code 400 (Bad Request)} if the fileMeta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileMeta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-metas")
    public ResponseEntity<FileMeta> updateFileMeta(@RequestBody FileMeta fileMeta) throws URISyntaxException {
        log.debug("REST request to update FileMeta : {}", fileMeta);
        if (fileMeta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FileMeta result = fileMetaService.save(fileMeta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileMeta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /file-metas} : get all the fileMetas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileMetas in body.
     */
    @GetMapping("/file-metas")
    public ResponseEntity<List<FileMeta>> getAllFileMetas(Pageable pageable) {
        log.debug("REST request to get a page of FileMetas");
        Page<FileMeta> page = fileMetaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /file-metas/:id} : get the "id" fileMeta.
     *
     * @param id the id of the fileMeta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileMeta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-metas/{id}")
    public ResponseEntity<FileMeta> getFileMeta(@PathVariable Long id) {
        log.debug("REST request to get FileMeta : {}", id);
        Optional<FileMeta> fileMeta = fileMetaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileMeta);
    }

    /**
     * {@code DELETE  /file-metas/:id} : delete the "id" fileMeta.
     *
     * @param id the id of the fileMeta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-metas/{id}")
    public ResponseEntity<Void> deleteFileMeta(@PathVariable Long id) {
        log.debug("REST request to delete FileMeta : {}", id);
        fileMetaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
