package fr.corentinbringer.fleetlens.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tj_software_machine")
public class SoftwareMachine {

    @EmbeddedId
    private SoftwareMachineId id;

    @ManyToOne
    @MapsId("softwareId")
    @JoinColumn(name = "software_id")
    private Software software;

    @ManyToOne
    @MapsId("machineId")
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @Column(nullable = false)
    private String packageVersion;
}