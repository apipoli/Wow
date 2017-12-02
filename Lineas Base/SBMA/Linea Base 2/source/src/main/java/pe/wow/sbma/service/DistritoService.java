package pe.wow.sbma.service;

import pe.wow.sbma.domain.Distrito;
import pe.wow.sbma.repository.DistritoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Distrito.
 */
@Service
@Transactional
public class DistritoService {

    private final Logger log = LoggerFactory.getLogger(DistritoService.class);

    private final DistritoRepository distritoRepository;

    public DistritoService(DistritoRepository distritoRepository) {
        this.distritoRepository = distritoRepository;
    }

    /**
     * Save a distrito.
     *
     * @param distrito the entity to save
     * @return the persisted entity
     */
    public Distrito save(Distrito distrito) {
        log.debug("Request to save Distrito : {}", distrito);
        return distritoRepository.save(distrito);
    }

    /**
     *  Get all the distritos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Distrito> findAll(Pageable pageable) {
        log.debug("Request to get all Distritos");
        return distritoRepository.findAll(pageable);
    }

    /**
     *  Get one distrito by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Distrito findOne(Long id) {
        log.debug("Request to get Distrito : {}", id);
        return distritoRepository.findOne(id);
    }

    /**
     *  Delete the  distrito by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Distrito : {}", id);
        distritoRepository.delete(id);
    }
}
