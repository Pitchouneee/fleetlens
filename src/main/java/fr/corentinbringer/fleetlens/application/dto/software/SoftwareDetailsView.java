package fr.corentinbringer.fleetlens.application.dto.software;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareDetailsView {

    private UUID id;
    private String packageName;
    private String packageVersion;
    List<SoftwareMachineDetailsView> machines;
}
