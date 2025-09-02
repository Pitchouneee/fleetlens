package fr.corentinbringer.fleetlens.application.dto.user;

import fr.corentinbringer.fleetlens.domain.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditUserView {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
}
