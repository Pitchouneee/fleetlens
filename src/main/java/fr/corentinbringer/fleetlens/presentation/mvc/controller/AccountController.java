package fr.corentinbringer.fleetlens.presentation.mvc.controller;

import fr.corentinbringer.fleetlens.application.dto.account.ListAccountDTO;
import fr.corentinbringer.fleetlens.application.dto.account.ListAccountProjection;
import fr.corentinbringer.fleetlens.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public String getAllAccounts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "12") int size,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ListAccountProjection> accountPage = accountService.findAll(pageable);
//        Page<ListAccountDTO> accountPage = accountService.findAll(pageable);

        model.addAttribute("accounts", accountPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", accountPage.getTotalPages());
        model.addAttribute("nextPage", page + 1 < accountPage.getTotalPages() ? page + 1 : page);
        model.addAttribute("prevPage", page - 1 >= 0 ? page - 1 : page);

        return "accounts/list";
    }
}
