package pe.wow.sbma.service;

import pe.wow.sbma.domain.Provincia;
import pe.wow.sbma.repository.ProvinciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Provincia.
 */
@Service
@Transactional
public class ProvinciaService {

    private final Logger log = LoggerFactory.getLogger(ProvinciaService.class);

    private final ProvinciaRepository provinciaRepository;

    public ProvinciaService(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    /**
     * Save a provincia.
     *
     * @param provincia the entity to save
     * @return the persisted entity
     */
    public Provincia save(Provincia provincia) {
        log.debug("Request to save Provincia : {}", provincia);
        return provinciaRepository.save(provincia);
    }

    /**
     *  Get all the provincias.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Provincia> findAll(Pageable pageable) {
        log.debug("Request to get all Provincias");
        return provinciaRepository.findAll(pageable);
    }

    /**
     *  Get one provincia by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Provincia findOne(Long id) {
        log.debug("Request to get Provincia : {}", id);
        return provinciaRepository.findOne(id);
    }

    /**
     *  Delete the  provincia by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Provincia : {}", id);
        provinciaRepository.delete(id);
    }
}
