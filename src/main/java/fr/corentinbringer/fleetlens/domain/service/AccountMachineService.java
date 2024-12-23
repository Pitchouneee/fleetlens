package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.domain.model.AccountMachine;
import fr.corentinbringer.fleetlens.domain.repository.AccountMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountMachineService {

    private final AccountMachineRepository accountMachineRepository;

    public void delete(AccountMachine accountMachine) {
        accountMachineRepository.delete(accountMachine);
    }

    public void save(AccountMachine accountMachine) {
        accountMachineRepository.save(accountMachine);
    }
}
