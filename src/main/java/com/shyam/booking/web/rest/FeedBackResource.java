package com.shyam.booking.web.rest;

import com.shyam.booking.domain.FeedBack;
import com.shyam.booking.service.FeedBackService;
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
 * REST controller for managing {@link com.shyam.booking.domain.FeedBack}.
 */
@RestController
@RequestMapping("/api")
public class FeedBackResource {

    private final Logger log = LoggerFactory.getLogger(FeedBackResource.class);

    private static final String ENTITY_NAME = "feedBack";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedBackService feedBackService;

    public FeedBackResource(FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }

    /**
     * {@code POST  /feed-backs} : Create a new feedBack.
     *
     * @param feedBack the feedBack to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedBack, or with status {@code 400 (Bad Request)} if the feedBack has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feed-backs")
    public ResponseEntity<FeedBack> createFeedBack(@RequestBody FeedBack feedBack) throws URISyntaxException {
        log.debug("REST request to save FeedBack : {}", feedBack);
        if (feedBack.getId() != null) {
            throw new BadRequestAlertException("A new feedBack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedBack result = feedBackService.save(feedBack);
        return ResponseEntity.created(new URI("/api/feed-backs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feed-backs} : Updates an existing feedBack.
     *
     * @param feedBack the feedBack to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedBack,
     * or with status {@code 400 (Bad Request)} if the feedBack is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedBack couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feed-backs")
    public ResponseEntity<FeedBack> updateFeedBack(@RequestBody FeedBack feedBack) throws URISyntaxException {
        log.debug("REST request to update FeedBack : {}", feedBack);
        if (feedBack.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeedBack result = feedBackService.save(feedBack);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feedBack.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /feed-backs} : get all the feedBacks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedBacks in body.
     */
    @GetMapping("/feed-backs")
    public ResponseEntity<List<FeedBack>> getAllFeedBacks(Pageable pageable) {
        log.debug("REST request to get a page of FeedBacks");
        Page<FeedBack> page = feedBackService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feed-backs/:id} : get the "id" feedBack.
     *
     * @param id the id of the feedBack to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedBack, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feed-backs/{id}")
    public ResponseEntity<FeedBack> getFeedBack(@PathVariable Long id) {
        log.debug("REST request to get FeedBack : {}", id);
        Optional<FeedBack> feedBack = feedBackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedBack);
    }

    /**
     * {@code DELETE  /feed-backs/:id} : delete the "id" feedBack.
     *
     * @param id the id of the feedBack to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feed-backs/{id}")
    public ResponseEntity<Void> deleteFeedBack(@PathVariable Long id) {
        log.debug("REST request to delete FeedBack : {}", id);
        feedBackService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
