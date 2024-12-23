package fr.corentinbringer.fleetlens.domain.repository;

import fr.corentinbringer.fleetlens.domain.model.AccountMachine;
import fr.corentinbringer.fleetlens.domain.model.AccountMachineId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMachineRepository extends JpaRepository<AccountMachine, AccountMachineId> {

}
