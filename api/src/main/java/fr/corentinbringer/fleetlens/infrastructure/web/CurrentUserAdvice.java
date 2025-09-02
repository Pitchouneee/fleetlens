package fr.corentinbringer.fleetlens.infrastructure.web;

import fr.corentinbringer.fleetlens.domain.model.User;
import fr.corentinbringer.fleetlens.infrastructure.model.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserAdvice {

    @ModelAttribute("currentUser")
    public User getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return (userDetails != null) ? userDetails.getUser() : null;
    }
}
