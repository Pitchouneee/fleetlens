package fr.corentinbringer.fleetlens.application.dto.software;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareListView {

    private UUID id;
    private String packageName;
    private String  packageVersion;
    private Long machineCount;
}
