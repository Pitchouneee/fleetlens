package fr.corentinbringer.fleetlens.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Table(name = "vm_open_port", uniqueConstraints = @UniqueConstraint(
        name = "uq_vop_vm_port_proto", columnNames = {"vm_id", "port", "protocol"})
)
public class VmOpenPort {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vm_id", nullable = false)
    private VmMachine vm;

    private int port;

    @Enumerated(EnumType.STRING)
    private Protocol protocol;

    public enum Protocol {TCP, UDP}
}
