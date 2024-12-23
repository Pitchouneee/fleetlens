package fr.corentinbringer.fleetlens.application.dto.machine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MachineFilterRequest {

    private String searchTerm; // Hostname or ipAddressV4
}
