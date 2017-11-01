package pe.wow.sbma.service;

import pe.wow.sbma.domain.Mascota;
import pe.wow.sbma.repository.MascotaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing Mascota.
 */
@Service
@Transactional
public class MascotaService {

    private final Logger log = LoggerFactory.getLogger(MascotaService.class);

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    /**
     * Save a mascota.
     *
     * @param mascota the entity to save
     * @return the persisted entity
     */
    public Mascota save(Mascota mascota) {
        log.debug("Request to save Mascota : {}", mascota);
        return mascotaRepository.save(mascota);
    }

    /**
     *  Get all the mascotas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Mascota> findAll(Pageable pageable) {
        log.debug("Request to get all Mascotas");
        return mascotaRepository.findAll(pageable);
    }

    /**
     *  Get all the mascotas by the current user.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Mascota> findAllByDuenoIsCurrentUser() {
        log.debug("Request to get all Mascotas findByDuenoIsCurrentUser");
        return mascotaRepository.findByDuenoIsCurrentUser();
    }

    /**
     *  Get one mascota by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Mascota findOne(Long id) {
        log.debug("Request to get Mascota : {}", id);
        return mascotaRepository.findOne(id);
    }

    /**
     *  Delete the  mascota by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Mascota : {}", id);
        mascotaRepository.delete(id);
    }
}
