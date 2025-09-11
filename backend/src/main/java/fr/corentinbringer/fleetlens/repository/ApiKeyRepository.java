package fr.corentinbringer.fleetlens.repository;

import fr.corentinbringer.fleetlens.model.apikey.ApiKey;
import fr.corentinbringer.fleetlens.model.apikey.ApiKeyList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {

    Optional<ApiKey> findByKeyHash(String keyHash);

    @Query("""
        SELECT new fr.corentinbringer.fleetlens.model.apikey.ApiKeyList(
            a.id,
            a.name,
            a.createdAt,
            a.expiresAt,
            a.enabled,
            a.lastUsedAt,
            CASE 
              WHEN a.keyPrefix IS NULL OR a.keySuffix IS NULL THEN '…'
              ELSE CONCAT(a.keyPrefix, '...', a.keySuffix)
            END
        )
        FROM ApiKey a
        """)
    Page<ApiKeyList> findBy(Pageable pageable);
}
