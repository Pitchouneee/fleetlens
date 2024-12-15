package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.domain.model.Account;
import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.model.Software;
import fr.corentinbringer.fleetlens.domain.model.SystemGroup;
import fr.corentinbringer.fleetlens.domain.service.AccountService;
import fr.corentinbringer.fleetlens.domain.service.MachineService;
import fr.corentinbringer.fleetlens.domain.service.SoftwareService;
import fr.corentinbringer.fleetlens.domain.service.SystemGroupService;
import fr.corentinbringer.fleetlens.application.dto.synchronization.SyncRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SyncService {

    private final MachineService machineService;
    private final AccountService accountService;
    private final SystemGroupService systemGroupService;
    private final SoftwareService softwareService;

    public void syncData(SyncRequest syncRequest) {
        Machine machine = machineService.findByHostname(syncRequest.getMachine().getHostname());
        machine.setHostname(syncRequest.getMachine().getHostname());
        machine.setArchitecture(syncRequest.getMachine().getArchitecture());
        machine.setIpAddressV4(syncRequest.getMachine().getIpAddressV4());
        machine.setOperatingSystem(syncRequest.getMachine().getOperatingSystem());
        machineService.save(machine);

        syncRequest.getAccounts().forEach(userDTO -> {
            Account user = accountService.findAccountByUsernameAndMachine(userDTO.getUsername(), machine);
            user.setUsername(userDTO.getUsername());
            user.setRoot(userDTO.isRoot());
            user.setMachine(machine);
            accountService.save(user);
        });

        syncRequest.getGroups().forEach(groupDTO -> {
            SystemGroup systemGroup = systemGroupService.findGroupByNameAndMachine(groupDTO.getName(), machine);
            systemGroup.setName(groupDTO.getName());
            systemGroup.setMachine(machine);

            Set<Account> members = groupDTO.getMembers().stream()
                    .flatMap(memberString -> Arrays.stream(memberString.split(",")))
                    .filter(username -> username != null && !username.trim().isEmpty())
                    .map(username -> accountService.findAccountByUsernameAndMachine(username, machine))
                    .collect(Collectors.toSet());
            systemGroup.setMembers(members);

            systemGroupService.save(systemGroup);
        });

        syncRequest.getSoftware().forEach(softwareDTO -> {
            Software software = softwareService.findSoftwareByNameAndMachine(softwareDTO.getPackageName(), machine);
            software.setPackageName(softwareDTO.getPackageName());
            software.setPackageVersion(softwareDTO.getPackageVersion());
            software.setMachine(machine);
            softwareService.save(software);
        });
    }
}
