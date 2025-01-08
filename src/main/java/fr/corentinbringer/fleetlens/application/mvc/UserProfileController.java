package fr.corentinbringer.fleetlens.application.mvc;

import fr.corentinbringer.fleetlens.application.dto.profile.EditUserProfilRequest;
import fr.corentinbringer.fleetlens.application.dto.user.EditUserView;
import fr.corentinbringer.fleetlens.domain.model.User;
import fr.corentinbringer.fleetlens.domain.model.UserRole;
import fr.corentinbringer.fleetlens.domain.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserProfileController {

    private final ModelMapper modelMapper;
    private final ProfileService profileService;

    @GetMapping()
    public String profile(Principal principal, Model model) {
        User user = profileService.findUserByEmail(principal.getName());

        EditUserView editUser = modelMapper.map(user, EditUserView.class);

        model.addAttribute("user", editUser);
        model.addAttribute("roles", UserRole.values());

        return "profile/edit";
    }

    @PostMapping("/edit")
    public String editProfile(Principal principal, Model model, @Valid EditUserProfilRequest userRequest,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/profile";
        }

        User user = profileService.findUserByEmail(principal.getName());

        profileService.editUserProfile(user.getId(), userRequest);

        return "redirect:/profile";
    }

}
