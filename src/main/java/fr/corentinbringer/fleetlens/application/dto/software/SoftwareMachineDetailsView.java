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
public class SoftwareMachineDetailsView {

    private UUID id;
    private String hostname;
    private String ipAddressV4;
}
