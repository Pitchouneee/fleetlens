package fr.corentinbringer.fleetlens.domain.repository;

import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.model.SystemGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SystemGroupRepository extends JpaRepository<SystemGroup, UUID> {

    Optional<SystemGroup> findByNameAndMachine(String name, Machine machine);
}
