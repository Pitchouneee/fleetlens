package fr.corentinbringer.fleetlens.domain.repository;

import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.model.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, UUID> {

    Optional<Software> findByPackageNameAndMachine(String packageName, Machine machine);
}
