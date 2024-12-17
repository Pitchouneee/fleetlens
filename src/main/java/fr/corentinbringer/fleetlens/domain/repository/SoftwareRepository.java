package fr.corentinbringer.fleetlens.domain.repository;

import fr.corentinbringer.fleetlens.application.dto.software.ListSoftwareProjection;
import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.model.Software;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, UUID> {

    @Query("""
                SELECT DISTINCT s.packageName AS packageName, s.packageVersion AS packageVersion
                FROM Software s
            """)
    Page<ListSoftwareProjection> findDistinctSoftware(Pageable pageable);

    Optional<Software> findByPackageNameAndMachine(String packageName, Machine machine);
}
