package pe.wow.sbma.repository;

import pe.wow.sbma.domain.Raza;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Raza entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RazaRepository extends JpaRepository<Raza, Long> {

}
