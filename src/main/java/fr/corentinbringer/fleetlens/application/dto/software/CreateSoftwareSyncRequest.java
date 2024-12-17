package fr.corentinbringer.fleetlens.application.dto.software;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSoftwareSyncRequest {

    @NotBlank
    private String packageName;

    @NotBlank
    private String packageVersion;
}