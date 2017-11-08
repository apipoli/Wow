package pe.wow.sbma.service;

import pe.wow.sbma.repository.PublicacionMascotaPerdidaRepository;
import pe.wow.sbma.domain.PublicacionMascotaPerdida;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PublicacionMascotaPerdida.
 */
@Service
@Transactional
public class PublicacionMascotaPerdidaService {

    private final Logger log = LoggerFactory.getLogger(PublicacionMascotaPerdidaService.class);

    private final PublicacionMascotaPerdidaRepository publicacionMascotaPerdidaRepository;

    public PublicacionMascotaPerdidaService(PublicacionMascotaPerdidaRepository publicacionMascotaPerdidaRepository) {
        this.publicacionMascotaPerdidaRepository = publicacionMascotaPerdidaRepository;
    }

    /**
     * Save a publicacionMascotaPerdida.
     *
     * @param publicacionMascotaPerdida the entity to save
     * @return the persisted entity
     */
    public PublicacionMascotaPerdida save(PublicacionMascotaPerdida publicacionMascotaPerdida) {
        log.debug("Request to save PublicacionMascotaPerdida : {}", publicacionMascotaPerdida);
        return publicacionMascotaPerdidaRepository.save(publicacionMascotaPerdida);
    }

    /**
     *  Get all the publicacionMascotaPerdidas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PublicacionMascotaPerdida> findAll(Pageable pageable) {
        log.debug("Request to get all PublicacionMascotaPerdidas");
        return publicacionMascotaPerdidaRepository.findAll(pageable);
    }

    /**
     *  Get one publicacionMascotaPerdida by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PublicacionMascotaPerdida findOne(Long id) {
        log.debug("Request to get PublicacionMascotaPerdida : {}", id);
        return publicacionMascotaPerdidaRepository.findOne(id);
    }

    /**
     *  Delete the  publicacionMascotaPerdida by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PublicacionMascotaPerdida : {}", id);
        publicacionMascotaPerdidaRepository.delete(id);
    }
}
