package fr.corentinbringer.fleetlens.domain.repository;

import fr.corentinbringer.fleetlens.domain.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MachineRepository extends JpaRepository<Machine, UUID> {

    Optional<Machine> findByHostname(String hostname);

    @Query("SELECT m.operatingSystem, COUNT(m) FROM Machine m GROUP BY m.operatingSystem")
    List<Object[]> countByOperatingSystem();
}
