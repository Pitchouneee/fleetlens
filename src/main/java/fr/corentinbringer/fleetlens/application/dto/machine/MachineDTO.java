package fr.corentinbringer.fleetlens.application.dto.machine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MachineDTO {

    @NotBlank
    private String hostname;

    @NotBlank
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")
    private String ipAddressV4;

    private String operatingSystem;

    private String architecture;
}
