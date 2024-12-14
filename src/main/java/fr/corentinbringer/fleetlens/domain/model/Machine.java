package fr.corentinbringer.fleetlens.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "t_machine")
public class Machine {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String hostname;

    @Column
    private String ipAddressV4;

    @Column
    private String operatingSystem;

    @Column
    private String architecture;

    @OneToMany(mappedBy = "machine")
    private Set<Account> accounts;

    @OneToMany(mappedBy = "machine")
    private Set<SystemGroup> systemGroups;

    @OneToMany(mappedBy = "machine")
    private Set<Software> softwares;
}
