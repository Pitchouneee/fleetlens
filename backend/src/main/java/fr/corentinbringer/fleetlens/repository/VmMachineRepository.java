package fr.corentinbringer.fleetlens.repository;

import fr.corentinbringer.fleetlens.model.VmMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VmMachineRepository extends JpaRepository<VmMachine, UUID> {

    Optional<VmMachine> findByHostname(String hostname);
}
