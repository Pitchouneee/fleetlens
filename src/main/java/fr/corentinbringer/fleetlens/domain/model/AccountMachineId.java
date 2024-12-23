package fr.corentinbringer.fleetlens.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class AccountMachineId implements Serializable {

    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "machine_id")
    private UUID machineId;
}
