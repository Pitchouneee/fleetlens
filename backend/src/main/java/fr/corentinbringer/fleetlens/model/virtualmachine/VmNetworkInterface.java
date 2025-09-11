package fr.corentinbringer.fleetlens.model.virtualmachine;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "vm_network_interface", uniqueConstraints = @UniqueConstraint(
        name = "uq_vni_vm_name", columnNames = {"vm_id", "name"})
)
public class VmNetworkInterface {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vm_id", nullable = false)
    private VirtualMachine vm;

    @Column(nullable = false)
    private String name;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "mac_address")
    private String macAddress;
}
