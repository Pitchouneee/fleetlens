package fr.corentinbringer.fleetlens.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "t_system_group")
public class SystemGroup {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    @ManyToMany
    @JoinTable(
            name = "tj_system_group_user",
            joinColumns = @JoinColumn(name = "system_group_id"),
            inverseJoinColumns = @JoinColumn(name = "system_user_id")
    )
    private Set<Account> members = new HashSet<>();
}