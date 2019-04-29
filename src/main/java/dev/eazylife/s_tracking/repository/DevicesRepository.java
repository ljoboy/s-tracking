package dev.eazylife.s_tracking.repository;

import dev.eazylife.s_tracking.domain.Devices;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Devices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DevicesRepository extends JpaRepository<Devices, Long> {

}
