package pw.wow.sbma.web.rest;

import com.codahale.metrics.annotation.Timed;
import pw.wow.sbma.domain.Raza;

import pw.wow.sbma.repository.RazaRepository;
import pw.wow.sbma.web.rest.errors.BadRequestAlertException;
import pw.wow.sbma.web.rest.util.HeaderUtil;
import pw.wow.sbma.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Raza.
 */
@RestController
@RequestMapping("/api")
public class RazaResource {

    private final Logger log = LoggerFactory.getLogger(RazaResource.class);

    private static final String ENTITY_NAME = "raza";

    private final RazaRepository razaRepository;

    public RazaResource(RazaRepository razaRepository) {
        this.razaRepository = razaRepository;
    }

    /**
     * POST  /razas : Create a new raza.
     *
     * @param raza the raza to create
     * @return the ResponseEntity with status 201 (Created) and with body the new raza, or with status 400 (Bad Request) if the raza has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/razas")
    @Timed
    public ResponseEntity<Raza> createRaza(@Valid @RequestBody Raza raza) throws URISyntaxException {
        log.debug("REST request to save Raza : {}", raza);
        if (raza.getId() != null) {
            throw new BadRequestAlertException("A new raza cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Raza result = razaRepository.save(raza);
        return ResponseEntity.created(new URI("/api/razas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /razas : Updates an existing raza.
     *
     * @param raza the raza to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated raza,
     * or with status 400 (Bad Request) if the raza is not valid,
     * or with status 500 (Internal Server Error) if the raza couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/razas")
    @Timed
    public ResponseEntity<Raza> updateRaza(@Valid @RequestBody Raza raza) throws URISyntaxException {
        log.debug("REST request to update Raza : {}", raza);
        if (raza.getId() == null) {
            return createRaza(raza);
        }
        Raza result = razaRepository.save(raza);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, raza.getId().toString()))
            .body(result);
    }

    /**
     * GET  /razas : get all the razas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of razas in body
     */
    @GetMapping("/razas")
    @Timed
    public ResponseEntity<List<Raza>> getAllRazas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Razas");
        Page<Raza> page = razaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/razas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /razas/:id : get the "id" raza.
     *
     * @param id the id of the raza to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the raza, or with status 404 (Not Found)
     */
    @GetMapping("/razas/{id}")
    @Timed
    public ResponseEntity<Raza> getRaza(@PathVariable Long id) {
        log.debug("REST request to get Raza : {}", id);
        Raza raza = razaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(raza));
    }

    /**
     * DELETE  /razas/:id : delete the "id" raza.
     *
     * @param id the id of the raza to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/razas/{id}")
    @Timed
    public ResponseEntity<Void> deleteRaza(@PathVariable Long id) {
        log.debug("REST request to delete Raza : {}", id);
        razaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
