package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.application.dto.synchronization.SyncRequest;
import fr.corentinbringer.fleetlens.domain.model.Account;
import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.model.Software;
import fr.corentinbringer.fleetlens.domain.model.SystemGroup;
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
            Account account = accountService.findAccountByUsername(userDTO.getUsername());
            account.setUsername(userDTO.getUsername());
            account.setRoot(userDTO.isRoot());

            account.getMachines().add(machine);
            machine.getAccounts().add(account);

            accountService.save(account);
            machineService.save(machine);
        });

        syncRequest.getGroups().forEach(groupDTO -> {
            SystemGroup systemGroup = systemGroupService.findGroupByNameAndMachine(groupDTO.getName(), machine);
            systemGroup.setName(groupDTO.getName());
            systemGroup.setMachine(machine);

            Set<Account> members = groupDTO.getMembers().stream()
                    .flatMap(memberString -> Arrays.stream(memberString.split(",")))
                    .filter(username -> username != null && !username.trim().isEmpty())
                    .map(accountService::findAccountByUsername)
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
