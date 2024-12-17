package fr.corentinbringer.fleetlens.domain.repository;

import fr.corentinbringer.fleetlens.application.dto.account.ListAccountProjection;
import fr.corentinbringer.fleetlens.domain.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {

    @Query("SELECT a.id AS id, a.username AS username FROM Account a")
    Page<ListAccountProjection> findAllProjectedBy(Pageable pageable);

    @Query("SELECT COUNT(DISTINCT a.username) FROM Account a")
    long countDistinctUsernames();

    Optional<Account> findByUsername(String username);
}
