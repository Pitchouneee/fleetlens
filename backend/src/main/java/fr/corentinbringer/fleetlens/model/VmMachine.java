package fr.corentinbringer.fleetlens.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "vm_machine", uniqueConstraints = @UniqueConstraint(
        name = "uq_vm_hostname", columnNames = "hostname")
)
public class VmMachine {

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

    public void replaceInterfaces(Collection<VmNetworkInterface> newOnes) {
        networkInterfaces.clear();
        newOnes.forEach(this::addInterface);
    }

    public void addInterface(VmNetworkInterface networkInterface) {
        networkInterface.setVm(this);
        networkInterfaces.add(networkInterface);
    }

    public void replaceOpenPorts(Collection<VmOpenPort> newOnes) {
        openPorts.clear();
        newOnes.forEach(this::addOpenPort);
    }

    public void addOpenPort(VmOpenPort openPort) {
        openPort.setVm(this);
        openPorts.add(openPort);
    }
}
