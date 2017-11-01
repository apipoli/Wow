package pw.wow.sbma.repository;

import pw.wow.sbma.domain.Mascota;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Mascota entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    @Query("select mascota from Mascota mascota where mascota.dueno.login = ?#{principal.username}")
    List<Mascota> findByDuenoIsCurrentUser();

}
