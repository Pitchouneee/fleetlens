package fr.corentinbringer.fleetlens.model.virtualmachine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VmIngestRequest(

        @NotBlank String hostname,
        @NotBlank String ipAddress,
        String osType,
        String uptime,

        @NotNull Long ramTotal,
        @NotNull Long ramUsed,

        @NotNull Integer cpuCores,
        @NotNull Double cpuUsage,

        @NotNull Long diskTotal,
        @NotNull Long diskUsed,

        List<NetworkInterfaceRequest> networkInterfaces,
        List<OpenPortRequest> openPorts
) {

    public record NetworkInterfaceRequest(
            @NotBlank String name,
            String ipAddress,
            String macAddress
    ) {
    }

    public record OpenPortRequest(
            @NotNull Integer port,
            @NotBlank String protocol
    ) {
    }
}
