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
@Table(name = "t_system_account")
public class Account {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private Set<AccountMachine> accountMachines = new HashSet<>();

    @ManyToMany(mappedBy = "members", cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH
    })
    private Set<SystemGroup> systemGroups = new HashSet<>();
}
