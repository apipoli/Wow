package pw.wow.sbma.web.rest;

import com.codahale.metrics.annotation.Timed;
import pw.wow.sbma.domain.Mascota;

import pw.wow.sbma.repository.MascotaRepository;
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
 * REST controller for managing Mascota.
 */
@RestController
@RequestMapping("/api")
public class MascotaResource {

    private final Logger log = LoggerFactory.getLogger(MascotaResource.class);

    private static final String ENTITY_NAME = "mascota";

    private final MascotaRepository mascotaRepository;

    public MascotaResource(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    /**
     * POST  /mascotas : Create a new mascota.
     *
     * @param mascota the mascota to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mascota, or with status 400 (Bad Request) if the mascota has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mascotas")
    @Timed
    public ResponseEntity<Mascota> createMascota(@Valid @RequestBody Mascota mascota) throws URISyntaxException {
        log.debug("REST request to save Mascota : {}", mascota);
        if (mascota.getId() != null) {
            throw new BadRequestAlertException("A new mascota cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mascota result = mascotaRepository.save(mascota);
        return ResponseEntity.created(new URI("/api/mascotas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mascotas : Updates an existing mascota.
     *
     * @param mascota the mascota to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mascota,
     * or with status 400 (Bad Request) if the mascota is not valid,
     * or with status 500 (Internal Server Error) if the mascota couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mascotas")
    @Timed
    public ResponseEntity<Mascota> updateMascota(@Valid @RequestBody Mascota mascota) throws URISyntaxException {
        log.debug("REST request to update Mascota : {}", mascota);
        if (mascota.getId() == null) {
            return createMascota(mascota);
        }
        Mascota result = mascotaRepository.save(mascota);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mascota.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mascotas : get all the mascotas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mascotas in body
     */
    @GetMapping("/mascotas")
    @Timed
    public ResponseEntity<List<Mascota>> getAllMascotas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Mascotas");
        Page<Mascota> page = mascotaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mascotas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mascotas/:id : get the "id" mascota.
     *
     * @param id the id of the mascota to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mascota, or with status 404 (Not Found)
     */
    @GetMapping("/mascotas/{id}")
    @Timed
    public ResponseEntity<Mascota> getMascota(@PathVariable Long id) {
        log.debug("REST request to get Mascota : {}", id);
        Mascota mascota = mascotaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mascota));
    }

    /**
     * DELETE  /mascotas/:id : delete the "id" mascota.
     *
     * @param id the id of the mascota to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mascotas/{id}")
    @Timed
    public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {
        log.debug("REST request to delete Mascota : {}", id);
        mascotaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
