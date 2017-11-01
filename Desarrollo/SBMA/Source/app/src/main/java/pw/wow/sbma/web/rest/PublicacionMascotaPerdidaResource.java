package pw.wow.sbma.web.rest;

import com.codahale.metrics.annotation.Timed;
import pw.wow.sbma.domain.PublicacionMascotaPerdida;

import pw.wow.sbma.repository.PublicacionMascotaPerdidaRepository;
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
 * REST controller for managing PublicacionMascotaPerdida.
 */
@RestController
@RequestMapping("/api")
public class PublicacionMascotaPerdidaResource {

    private final Logger log = LoggerFactory.getLogger(PublicacionMascotaPerdidaResource.class);

    private static final String ENTITY_NAME = "publicacionMascotaPerdida";

    private final PublicacionMascotaPerdidaRepository publicacionMascotaPerdidaRepository;

    public PublicacionMascotaPerdidaResource(PublicacionMascotaPerdidaRepository publicacionMascotaPerdidaRepository) {
        this.publicacionMascotaPerdidaRepository = publicacionMascotaPerdidaRepository;
    }

    /**
     * POST  /publicacion-mascota-perdidas : Create a new publicacionMascotaPerdida.
     *
     * @param publicacionMascotaPerdida the publicacionMascotaPerdida to create
     * @return the ResponseEntity with status 201 (Created) and with body the new publicacionMascotaPerdida, or with status 400 (Bad Request) if the publicacionMascotaPerdida has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/publicacion-mascota-perdidas")
    @Timed
    public ResponseEntity<PublicacionMascotaPerdida> createPublicacionMascotaPerdida(@Valid @RequestBody PublicacionMascotaPerdida publicacionMascotaPerdida) throws URISyntaxException {
        log.debug("REST request to save PublicacionMascotaPerdida : {}", publicacionMascotaPerdida);
        if (publicacionMascotaPerdida.getId() != null) {
            throw new BadRequestAlertException("A new publicacionMascotaPerdida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicacionMascotaPerdida result = publicacionMascotaPerdidaRepository.save(publicacionMascotaPerdida);
        return ResponseEntity.created(new URI("/api/publicacion-mascota-perdidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /publicacion-mascota-perdidas : Updates an existing publicacionMascotaPerdida.
     *
     * @param publicacionMascotaPerdida the publicacionMascotaPerdida to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated publicacionMascotaPerdida,
     * or with status 400 (Bad Request) if the publicacionMascotaPerdida is not valid,
     * or with status 500 (Internal Server Error) if the publicacionMascotaPerdida couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/publicacion-mascota-perdidas")
    @Timed
    public ResponseEntity<PublicacionMascotaPerdida> updatePublicacionMascotaPerdida(@Valid @RequestBody PublicacionMascotaPerdida publicacionMascotaPerdida) throws URISyntaxException {
        log.debug("REST request to update PublicacionMascotaPerdida : {}", publicacionMascotaPerdida);
        if (publicacionMascotaPerdida.getId() == null) {
            return createPublicacionMascotaPerdida(publicacionMascotaPerdida);
        }
        PublicacionMascotaPerdida result = publicacionMascotaPerdidaRepository.save(publicacionMascotaPerdida);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, publicacionMascotaPerdida.getId().toString()))
            .body(result);
    }

    /**
     * GET  /publicacion-mascota-perdidas : get all the publicacionMascotaPerdidas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of publicacionMascotaPerdidas in body
     */
    @GetMapping("/publicacion-mascota-perdidas")
    @Timed
    public ResponseEntity<List<PublicacionMascotaPerdida>> getAllPublicacionMascotaPerdidas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PublicacionMascotaPerdidas");
        Page<PublicacionMascotaPerdida> page = publicacionMascotaPerdidaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/publicacion-mascota-perdidas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /publicacion-mascota-perdidas/:id : get the "id" publicacionMascotaPerdida.
     *
     * @param id the id of the publicacionMascotaPerdida to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the publicacionMascotaPerdida, or with status 404 (Not Found)
     */
    @GetMapping("/publicacion-mascota-perdidas/{id}")
    @Timed
    public ResponseEntity<PublicacionMascotaPerdida> getPublicacionMascotaPerdida(@PathVariable Long id) {
        log.debug("REST request to get PublicacionMascotaPerdida : {}", id);
        PublicacionMascotaPerdida publicacionMascotaPerdida = publicacionMascotaPerdidaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(publicacionMascotaPerdida));
    }

    /**
     * DELETE  /publicacion-mascota-perdidas/:id : delete the "id" publicacionMascotaPerdida.
     *
     * @param id the id of the publicacionMascotaPerdida to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/publicacion-mascota-perdidas/{id}")
    @Timed
    public ResponseEntity<Void> deletePublicacionMascotaPerdida(@PathVariable Long id) {
        log.debug("REST request to delete PublicacionMascotaPerdida : {}", id);
        publicacionMascotaPerdidaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
