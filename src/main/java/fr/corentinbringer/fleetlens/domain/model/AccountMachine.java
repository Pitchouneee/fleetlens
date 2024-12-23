package fr.corentinbringer.fleetlens.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tj_account_machine")
public class AccountMachine {

    @EmbeddedId
    private AccountMachineId id;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @MapsId("machineId")
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @Column(nullable = false)
    private boolean isRoot = false;
}
