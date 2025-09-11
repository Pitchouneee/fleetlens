package fr.corentinbringer.fleetlens.repository;

import fr.corentinbringer.fleetlens.model.virtualmachine.VirtualMachine;
import fr.corentinbringer.fleetlens.model.virtualmachine.VmList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, UUID> {

    Optional<VirtualMachine> findByHostname(String hostname);

    Page<VmList> findBy(Pageable pageable);
}
