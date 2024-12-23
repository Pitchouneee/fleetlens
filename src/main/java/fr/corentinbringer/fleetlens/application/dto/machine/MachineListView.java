package fr.corentinbringer.fleetlens.application.dto.machine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MachineListView {

    private UUID id;
    private String hostname;
    private String operatingSystem;
    private String ipAddressV4;
}
