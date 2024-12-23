package fr.corentinbringer.fleetlens.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Embeddable
public class SoftwareMachineId {

    @Column(name = "software_id")
    private UUID softwareId;

    @Column(name = "machine_id")
    private UUID machineId;
}
