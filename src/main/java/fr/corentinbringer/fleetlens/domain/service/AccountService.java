package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.application.dto.account.AccountDetailsView;
import fr.corentinbringer.fleetlens.application.dto.account.AccountFilterRequest;
import fr.corentinbringer.fleetlens.application.dto.account.AccountListView;
import fr.corentinbringer.fleetlens.domain.model.Account;
import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.model.SystemGroup;
import fr.corentinbringer.fleetlens.domain.repository.AccountRepository;
import fr.corentinbringer.fleetlens.domain.specification.AccountSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<AccountListView> findAll(int page, int size, AccountFilterRequest filterRequest) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Account> specification = AccountSpecification.filterBy(filterRequest);

        return accountRepository.findAll(specification, pageable).map(account ->
                modelMapper.map(account, AccountListView.class)
        );
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

        List<AccountDetailsView.MachineView> machines = account.getAccountMachines().stream()
                .map(accountMachine -> {
                    Machine machine = accountMachine.getMachine();

                    // Filter groups on the machine where the user is a member
                    List<String> relevantSystemGroups = machine.getSystemGroups().stream()
                            .filter(systemGroup -> systemGroup.getMembers().contains(account))
                            .map(SystemGroup::getName)
                            .toList();

                    // Map the machine and add filtered groups
                    AccountDetailsView.MachineView machineView = modelMapper.map(machine, AccountDetailsView.MachineView.class);
                    machineView.setSystemGroups(relevantSystemGroups);
                    machineView.setRoot(accountMachine.isRoot());

                    return machineView;
                })
                .toList();

        AccountDetailsView accountDetailsView = modelMapper.map(account, AccountDetailsView.class);
        accountDetailsView.setMachines(machines);
        return accountDetailsView;
    }
}
