package fr.corentinbringer.fleetlens.domain.repository;

import fr.corentinbringer.fleetlens.application.dto.software.SoftwareDetailsView;
import fr.corentinbringer.fleetlens.application.dto.software.SoftwareListView;
import fr.corentinbringer.fleetlens.application.dto.software.SoftwareMachineDetailsView;
import fr.corentinbringer.fleetlens.domain.model.Software;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, UUID> {

    Optional<Software> findByPackageName(String packageName);

    @Query("""
            SELECT DISTINCT new fr.corentinbringer.fleetlens.application.dto.software.SoftwareListView(
                s.id, s.packageName, sm.packageVersion, COUNT(DISTINCT sm.machine.id)
            )
            FROM Software s
            JOIN s.softwareMachines sm
            WHERE (:searchTerm IS NULL OR
                   LOWER(s.packageName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
                   LOWER(sm.packageVersion) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
            )
            GROUP BY s.id, s.packageName, sm.packageVersion
            """)
    Page<SoftwareListView> findAllDistinctSoftwareWithVersion(Pageable pageable, String searchTerm);

    @Query("""
        SELECT DISTINCT new fr.corentinbringer.fleetlens.application.dto.software.SoftwareDetailsView(
            s.id,
            s.packageName,
            sm.packageVersion,
            null
        )
        FROM Software s
        JOIN s.softwareMachines sm
        WHERE s.id = :softwareId
        AND sm.packageVersion = :version
    """)
    Optional<SoftwareDetailsView> findSoftwareDetailsByIdAndVersion(UUID softwareId, String version);

    @Query("""
        SELECT new fr.corentinbringer.fleetlens.application.dto.software.SoftwareMachineDetailsView(
            m.id,
            m.hostname,
            m.ipAddressV4
        )
        FROM Software s
        JOIN s.softwareMachines sm
        JOIN sm.machine m
        WHERE s.id = :softwareId
        AND sm.packageVersion = :version
    """)
    List<SoftwareMachineDetailsView> findMachinesForSoftware(UUID softwareId, String version);
}
