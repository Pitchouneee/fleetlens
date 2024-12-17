package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.application.dto.account.AccountDetailsView;
import fr.corentinbringer.fleetlens.application.dto.account.AccountFilterRequest;
import fr.corentinbringer.fleetlens.application.dto.account.ListAccountProjection;
import fr.corentinbringer.fleetlens.domain.model.Account;
import fr.corentinbringer.fleetlens.domain.model.SystemGroup;
import fr.corentinbringer.fleetlens.domain.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username).orElse(new Account());
    }

    public Page<ListAccountProjection> findAll(int page, int size, AccountFilterRequest filterRequest) {
        Pageable pageable = PageRequest.of(page, size);

        return accountRepository.findAllProjectedBy(pageable);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public long totalUniqueAccount() {
        return accountRepository.countDistinctUsernames();
    }

    public AccountDetailsView findAccountWithDetails(UUID id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account with provided UUID not found"));

        List<AccountDetailsView.MachineDTO> machines = account.getMachines().stream()
                .map(machine -> {
                    // Filter groups on the machine where the user is a member
                    List<String> relevantSystemGroups = machine.getSystemGroups().stream()
                            .filter(systemGroup -> systemGroup.getMembers().contains(account))
                            .map(SystemGroup::getName)
                            .toList();

                    // Map the machine and add filtered groups
                    AccountDetailsView.MachineDTO machineDTO = modelMapper.map(machine, AccountDetailsView.MachineDTO.class);
                    machineDTO.setSystemGroups(relevantSystemGroups);

                    return machineDTO;
                })
                .toList();

        AccountDetailsView accountDetailsView = modelMapper.map(account, AccountDetailsView.class);
        accountDetailsView.setMachines(machines);
        return accountDetailsView;
    }
}
