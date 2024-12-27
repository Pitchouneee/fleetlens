package fr.corentinbringer.fleetlens.application.mvc.admin;

import fr.corentinbringer.fleetlens.application.dto.token.CreateTokenRequest;
import fr.corentinbringer.fleetlens.application.dto.token.TokenListView;
import fr.corentinbringer.fleetlens.domain.model.Token;
import fr.corentinbringer.fleetlens.domain.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/tokens")
public class TokenController {

    private final TokenService tokenService;

    @GetMapping
    public String getAllTokens(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "12") int size,
                               Model model) {
        Page<TokenListView> tokenPage = tokenService.findAll(page, size);

        model.addAttribute("tokens", tokenPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tokenPage.getTotalPages());
        model.addAttribute("nextPage", page + 1 < tokenPage.getTotalPages() ? page + 1 : page);
        model.addAttribute("prevPage", page - 1 >= 0 ? page - 1 : page);

        return "admin/tokens/index";
    }

    @PostMapping("/create")
    public String createToken(@ModelAttribute("token") @Valid CreateTokenRequest tokenRequest,
                              RedirectAttributes redirectAttributes) {
        Token createdToken = tokenService.createNewToken(tokenRequest);
        redirectAttributes.addFlashAttribute("createdToken", createdToken.getToken());
        redirectAttributes.addFlashAttribute("message", "Token created successfully. This token is only visible now.");

        return "redirect:/admin/tokens";
    }

    @PostMapping("/{id}/delete")
    public String deleteToken(@PathVariable UUID id) {
        tokenService.delete(id);
        return "redirect:/admin/tokens";
    }

    @PostMapping("/{id}/revoke")
    public String revokeToken(@PathVariable UUID id) {
        tokenService.revokeToken(id);
        return "redirect:/admin/tokens";
    }
}
