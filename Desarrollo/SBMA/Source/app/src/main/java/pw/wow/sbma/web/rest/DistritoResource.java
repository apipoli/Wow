package pw.wow.sbma.web.rest;

import com.codahale.metrics.annotation.Timed;
import pw.wow.sbma.domain.Distrito;

import pw.wow.sbma.repository.DistritoRepository;
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
 * REST controller for managing Distrito.
 */
@RestController
@RequestMapping("/api")
public class DistritoResource {

    private final Logger log = LoggerFactory.getLogger(DistritoResource.class);

    private static final String ENTITY_NAME = "distrito";

    private final DistritoRepository distritoRepository;

    public DistritoResource(DistritoRepository distritoRepository) {
        this.distritoRepository = distritoRepository;
    }

    /**
     * POST  /distritos : Create a new distrito.
     *
     * @param distrito the distrito to create
     * @return the ResponseEntity with status 201 (Created) and with body the new distrito, or with status 400 (Bad Request) if the distrito has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/distritos")
    @Timed
    public ResponseEntity<Distrito> createDistrito(@Valid @RequestBody Distrito distrito) throws URISyntaxException {
        log.debug("REST request to save Distrito : {}", distrito);
        if (distrito.getId() != null) {
            throw new BadRequestAlertException("A new distrito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Distrito result = distritoRepository.save(distrito);
        return ResponseEntity.created(new URI("/api/distritos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /distritos : Updates an existing distrito.
     *
     * @param distrito the distrito to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated distrito,
     * or with status 400 (Bad Request) if the distrito is not valid,
     * or with status 500 (Internal Server Error) if the distrito couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/distritos")
    @Timed
    public ResponseEntity<Distrito> updateDistrito(@Valid @RequestBody Distrito distrito) throws URISyntaxException {
        log.debug("REST request to update Distrito : {}", distrito);
        if (distrito.getId() == null) {
            return createDistrito(distrito);
        }
        Distrito result = distritoRepository.save(distrito);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, distrito.getId().toString()))
            .body(result);
    }

    /**
     * GET  /distritos : get all the distritos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of distritos in body
     */
    @GetMapping("/distritos")
    @Timed
    public ResponseEntity<List<Distrito>> getAllDistritos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Distritos");
        Page<Distrito> page = distritoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/distritos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /distritos/:id : get the "id" distrito.
     *
     * @param id the id of the distrito to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the distrito, or with status 404 (Not Found)
     */
    @GetMapping("/distritos/{id}")
    @Timed
    public ResponseEntity<Distrito> getDistrito(@PathVariable Long id) {
        log.debug("REST request to get Distrito : {}", id);
        Distrito distrito = distritoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(distrito));
    }

    /**
     * DELETE  /distritos/:id : delete the "id" distrito.
     *
     * @param id the id of the distrito to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/distritos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDistrito(@PathVariable Long id) {
        log.debug("REST request to delete Distrito : {}", id);
        distritoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
