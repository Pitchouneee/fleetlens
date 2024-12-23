package fr.corentinbringer.fleetlens.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "t_system_software")
public class Software {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String packageName;

    @OneToMany(mappedBy = "software")
    private Set<SoftwareMachine> softwareMachines = new HashSet<>();
}
