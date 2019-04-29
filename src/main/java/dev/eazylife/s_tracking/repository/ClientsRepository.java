package dev.eazylife.s_tracking.repository;

import dev.eazylife.s_tracking.domain.Clients;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Clients entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {
    Optional<Clients> findByTelephoneAndPassword(Long telephone, String password);
}
