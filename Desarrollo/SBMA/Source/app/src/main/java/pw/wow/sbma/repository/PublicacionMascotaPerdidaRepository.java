package pw.wow.sbma.repository;

import pw.wow.sbma.domain.PublicacionMascotaPerdida;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the PublicacionMascotaPerdida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicacionMascotaPerdidaRepository extends JpaRepository<PublicacionMascotaPerdida, Long> {

    @Query("select publicacion_mascota_perdida from PublicacionMascotaPerdida publicacion_mascota_perdida where publicacion_mascota_perdida.dueno.login = ?#{principal.username}")
    List<PublicacionMascotaPerdida> findByDuenoIsCurrentUser();

}
