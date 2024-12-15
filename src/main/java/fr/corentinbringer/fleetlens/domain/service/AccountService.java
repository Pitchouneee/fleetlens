package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.application.dto.account.ListAccountDTO;
import fr.corentinbringer.fleetlens.application.dto.account.ListAccountProjection;
import fr.corentinbringer.fleetlens.domain.model.Account;
import fr.corentinbringer.fleetlens.domain.repository.AccountRepository;
import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.specification.AccountSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account findAccountByUsernameAndMachine(String username, Machine machine) {
        return accountRepository.findByUsernameAndMachine(username, machine).orElse(new Account());
    }

//    public Page<ListAccountProjection> findAll(Pageable pageable) {
    public Page<ListAccountProjection> findAll(Pageable pageable) {
//        Specification<Account> spec = AccountSpecifications.distinctByUsername();
//
//        return accountRepository.findAll(spec, pageable)
//                .map(account -> account::getUsername);

        return accountRepository.findAllProjectedBy(pageable);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }
}
