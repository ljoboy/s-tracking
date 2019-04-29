package dev.eazylife.s_tracking.repository;

import dev.eazylife.s_tracking.domain.Voitures;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Voitures entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoituresRepository extends JpaRepository<Voitures, Long> {
    Page<Voitures> getAllByDevicesClientsId(Long id, Pageable pageable);

}
