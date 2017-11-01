package pe.wow.sbma.repository;

import pe.wow.sbma.domain.Distrito;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Distrito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistritoRepository extends JpaRepository<Distrito, Long> {

}
