package fr.corentinbringer.fleetlens.application.dto.profile;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserProfilRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    private String password = "";

    private String confirmPassword = "";

    @AssertTrue(message = "Password and Confirm Password should match")
    public boolean isPasswordsEqual() {
        return (password == null) ? false : password.equals(confirmPassword);
    }
}
