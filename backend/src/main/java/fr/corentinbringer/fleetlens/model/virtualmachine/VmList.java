package fr.corentinbringer.fleetlens.model.virtualmachine;

import java.util.UUID;

public record VmList(
        UUID id,
        String hostname,
        String ipAddress,
        String osType,

        Long ramTotal,
        Integer cpuCores,
        Long diskTotal,

        String uptime
) {
}
