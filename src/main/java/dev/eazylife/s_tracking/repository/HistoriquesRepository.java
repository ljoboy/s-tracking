package dev.eazylife.s_tracking.repository;

import dev.eazylife.s_tracking.domain.Historiques;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Historiques entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriquesRepository extends JpaRepository<Historiques, Long> {

}
