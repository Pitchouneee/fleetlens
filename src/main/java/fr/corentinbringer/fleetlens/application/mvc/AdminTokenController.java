package fr.corentinbringer.fleetlens.application.mvc;

import fr.corentinbringer.fleetlens.domain.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/tokens")
public class AdminTokenController {

    private final TokenService tokenService;

    @GetMapping
    public String getAllTokens(Model model) {
        model.addAttribute("tokens", tokenService.getAllToken());
        return "admin/tokens/index";
    }

    @PostMapping("/create")
    public String createToken() {
        tokenService.createNewToken();
        return "redirect:/admin/tokens";
    }

    @PostMapping("/{id}/delete")
    public String deleteToken(@PathVariable Long id) {
        tokenService.delete(id);
        return "redirect:/admin/tokens";
    }

    @PostMapping("/{id}/revoke")
    public String revokeToken(@PathVariable Long id) {
        tokenService.revokeToken(id);
        return "redirect:/admin/tokens";
    }
}
