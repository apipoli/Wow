package pe.wow.sbma.service;

import pe.wow.sbma.domain.Raza;
import pe.wow.sbma.repository.RazaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Raza.
 */
@Service
@Transactional
public class RazaService {

    private final Logger log = LoggerFactory.getLogger(RazaService.class);

    private final RazaRepository razaRepository;

    public RazaService(RazaRepository razaRepository) {
        this.razaRepository = razaRepository;
    }

    /**
     * Save a raza.
     *
     * @param raza the entity to save
     * @return the persisted entity
     */
    public Raza save(Raza raza) {
        log.debug("Request to save Raza : {}", raza);
        return razaRepository.save(raza);
    }

    /**
     *  Get all the razas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Raza> findAll(Pageable pageable) {
        log.debug("Request to get all Razas");
        return razaRepository.findAll(pageable);
    }

    /**
     *  Get one raza by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Raza findOne(Long id) {
        log.debug("Request to get Raza : {}", id);
        return razaRepository.findOne(id);
    }

    /**
     *  Delete the  raza by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Raza : {}", id);
        razaRepository.delete(id);
    }
}
