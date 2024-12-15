package fr.corentinbringer.fleetlens.application.dto.systemgroup;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SyncSystemGroupDTO {

    @NotBlank
    private String name;

    private List<String> members;
}