package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.domain.model.Account;
import fr.corentinbringer.fleetlens.domain.repository.AccountRepository;
import fr.corentinbringer.fleetlens.domain.model.Machine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account findAccountByUsernameAndMachine(String username, Machine machine) {
        return accountRepository.findByUsernameAndMachine(username, machine).orElse(new Account());
    }

    public void save(Account account) {
        accountRepository.save(account);
    }
}
