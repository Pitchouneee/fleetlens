package fr.corentinbringer.fleetlens.application.mvc;

import fr.corentinbringer.fleetlens.application.dto.account.AccountDetailsView;
import fr.corentinbringer.fleetlens.application.dto.account.AccountFilterRequest;
import fr.corentinbringer.fleetlens.application.dto.account.AccountListView;
import fr.corentinbringer.fleetlens.domain.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public String getAllAccounts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "12") int size,
                                 @Valid AccountFilterRequest filterRequest,
                                 Model model) {
        Page<AccountListView> accountPage = accountService.findAll(page, size, filterRequest);

        model.addAttribute("accounts", accountPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", accountPage.getTotalPages());
        model.addAttribute("nextPage", page + 1 < accountPage.getTotalPages() ? page + 1 : page);
        model.addAttribute("prevPage", page - 1 >= 0 ? page - 1 : page);
        model.addAttribute("username", filterRequest.getUsername());

        return "accounts/list";
    }

    @GetMapping("/{accountId}")
    public String getAccountDetails(@PathVariable UUID accountId, Model model) {
        AccountDetailsView account = accountService.findAccountWithDetails(accountId);
        model.addAttribute("account", account);
        return "accounts/details";
    }
}
