package fr.corentinbringer.fleetlens.domain.repository;

import fr.corentinbringer.fleetlens.domain.model.SoftwareMachine;
import fr.corentinbringer.fleetlens.domain.model.SoftwareMachineId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareMachineRepository extends JpaRepository<SoftwareMachine, SoftwareMachineId> {

}
