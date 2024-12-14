package fr.corentinbringer.fleetlens.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "t_system_software")
public class Software {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    private UUID id;

    @Column(nullable = false)
    private String packageName;

    @Column(nullable = false)
    private String packageVersion;

    @ManyToOne
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;
}
