package pw.wow.sbma.web.rest;

import com.codahale.metrics.annotation.Timed;
import pw.wow.sbma.domain.Provincia;

import pw.wow.sbma.repository.ProvinciaRepository;
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
 * REST controller for managing Provincia.
 */
@RestController
@RequestMapping("/api")
public class ProvinciaResource {

    private final Logger log = LoggerFactory.getLogger(ProvinciaResource.class);

    private static final String ENTITY_NAME = "provincia";

    private final ProvinciaRepository provinciaRepository;

    public ProvinciaResource(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    /**
     * POST  /provincias : Create a new provincia.
     *
     * @param provincia the provincia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new provincia, or with status 400 (Bad Request) if the provincia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/provincias")
    @Timed
    public ResponseEntity<Provincia> createProvincia(@Valid @RequestBody Provincia provincia) throws URISyntaxException {
        log.debug("REST request to save Provincia : {}", provincia);
        if (provincia.getId() != null) {
            throw new BadRequestAlertException("A new provincia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Provincia result = provinciaRepository.save(provincia);
        return ResponseEntity.created(new URI("/api/provincias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provincias : Updates an existing provincia.
     *
     * @param provincia the provincia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated provincia,
     * or with status 400 (Bad Request) if the provincia is not valid,
     * or with status 500 (Internal Server Error) if the provincia couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/provincias")
    @Timed
    public ResponseEntity<Provincia> updateProvincia(@Valid @RequestBody Provincia provincia) throws URISyntaxException {
        log.debug("REST request to update Provincia : {}", provincia);
        if (provincia.getId() == null) {
            return createProvincia(provincia);
        }
        Provincia result = provinciaRepository.save(provincia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, provincia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provincias : get all the provincias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of provincias in body
     */
    @GetMapping("/provincias")
    @Timed
    public ResponseEntity<List<Provincia>> getAllProvincias(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Provincias");
        Page<Provincia> page = provinciaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/provincias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /provincias/:id : get the "id" provincia.
     *
     * @param id the id of the provincia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the provincia, or with status 404 (Not Found)
     */
    @GetMapping("/provincias/{id}")
    @Timed
    public ResponseEntity<Provincia> getProvincia(@PathVariable Long id) {
        log.debug("REST request to get Provincia : {}", id);
        Provincia provincia = provinciaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(provincia));
    }

    /**
     * DELETE  /provincias/:id : delete the "id" provincia.
     *
     * @param id the id of the provincia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provincias/{id}")
    @Timed
    public ResponseEntity<Void> deleteProvincia(@PathVariable Long id) {
        log.debug("REST request to delete Provincia : {}", id);
        provinciaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
