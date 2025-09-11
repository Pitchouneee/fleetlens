package fr.corentinbringer.fleetlens.model.virtualmachine;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "vm_machine", uniqueConstraints = @UniqueConstraint(
        name = "uq_vm_hostname", columnNames = "hostname")
)
public class VirtualMachine {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String hostname;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "os_type")
    private String osType;

    private String uptime;

    @Column(name = "ram_total")
    private long ramTotal;

    @Column(name = "ram_used")
    private long ramUsed;

    @Column(name = "cpu_cores")
    private int cpuCores;

    @Column(name = "cpu_usage")
    private double cpuUsage;

    @Column(name = "disk_total")
    private long diskTotal;

    @Column(name = "disk_used")
    private long diskUsed;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "vm", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<VmNetworkInterface> networkInterfaces = new HashSet<>();

    @OneToMany(mappedBy = "vm", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<VmOpenPort> openPorts = new HashSet<>();

    @PrePersist
    void onCreate() {
        createdAt = updatedAt = Instant.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    public void replaceInterfaces(Collection<VmNetworkInterface> incoming) {
        var existingByName = this.networkInterfaces.stream()
                .collect(Collectors.toMap(VmNetworkInterface::getName, ni -> ni));

        var namesFromIncoming = incoming.stream().map(VmNetworkInterface::getName).collect(Collectors.toSet());

        this.networkInterfaces.removeIf(ni -> !namesFromIncoming.contains(ni.getName()));

        for (var niNew : incoming) {
            var current = existingByName.get(niNew.getName());
            if (current == null) {
                niNew.setVm(this);
                this.networkInterfaces.add(niNew);
            } else {
                current.setIpAddress(niNew.getIpAddress());
                current.setMacAddress(niNew.getMacAddress());
            }
        }
    }

    public void replaceOpenPorts(Collection<VmOpenPort> incoming) {
        var existingByKey = this.openPorts.stream()
                .collect(Collectors.toMap(op -> op.getPort() + "|" + op.getProtocol().name(), op -> op));

        var keysFromIncoming = incoming.stream()
                .map(op -> op.getPort() + "|" + op.getProtocol().name())
                .collect(Collectors.toSet());

        this.openPorts.removeIf(op -> !keysFromIncoming.contains(op.getPort() + "|" + op.getProtocol().name()));

        for (var opNew : incoming) {
            var key = opNew.getPort() + "|" + opNew.getProtocol().name();
            var current = existingByKey.get(key);
            if (current == null) {
                opNew.setVm(this);
                this.openPorts.add(opNew);
            }
        }
    }
}
