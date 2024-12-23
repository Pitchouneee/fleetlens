package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.application.dto.account.CreateAccountSyncRequest;
import fr.corentinbringer.fleetlens.application.dto.software.CreateSoftwareSyncRequest;
import fr.corentinbringer.fleetlens.application.dto.synchronization.SyncRequest;
import fr.corentinbringer.fleetlens.domain.model.*;
import fr.corentinbringer.fleetlens.domain.model.Account;
import fr.corentinbringer.fleetlens.domain.model.AccountMachine;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
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

    private final AccountMachineService accountMachineService;
    private final SoftwareMachineService softwareMachineService;

    public void syncData(SyncRequest syncRequest) {
        Machine machine = machineService.findByHostname(syncRequest.getMachine().getHostname());
        machine.setHostname(syncRequest.getMachine().getHostname());
        machine.setArchitecture(syncRequest.getMachine().getArchitecture());
        machine.setIpAddressV4(syncRequest.getMachine().getIpAddressV4());
        machine.setOperatingSystem(syncRequest.getMachine().getOperatingSystem());
        machineService.save(machine);

        // Account synchronization
        Set<String> updatedUsernames = syncRequest.getAccounts().stream()
                .map(CreateAccountSyncRequest::getUsername)
                .collect(Collectors.toSet());

        // Deleting obsolete AccountMachines
        deleteObsoleteAccountMachines(machine, updatedUsernames);

        syncRequest.getAccounts().forEach(userDTO -> {
            Account account = accountService.findAccountByUsername(userDTO.getUsername());
            account.setUsername(userDTO.getUsername());
            accountService.save(account);

            AccountMachine accountMachine = account.getAccountMachines().stream()
                    .filter(am -> am.getMachine().equals(machine))
                    .findFirst()
                    .orElseGet(() -> {
                        AccountMachine newAm = new AccountMachine();
                        AccountMachineId newId = new AccountMachineId();
                        newId.setAccountId(account.getId());
                        newId.setMachineId(machine.getId());
                        newAm.setId(newId);
                        newAm.setAccount(account);
                        newAm.setMachine(machine);
                        account.getAccountMachines().add(newAm);
                        machine.getAccountMachines().add(newAm);
                        return newAm;
                    });

            accountMachine.setRoot(userDTO.isRoot());

            accountMachineService.save(accountMachine);
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

        // Software synchronization
        Set<String> updatedPackages = syncRequest.getSoftwares().stream()
                .map(CreateSoftwareSyncRequest::getPackageName)
                .collect(Collectors.toSet());

        // Deleting obsolete SoftwareMachines
        deleteObsoleteSoftwareMachines(machine, updatedPackages);

        syncRequest.getSoftwares().forEach(softwareDTO -> {
            Software software = softwareService.findSoftwareByPackageName(softwareDTO.getPackageName());
            software.setPackageName(softwareDTO.getPackageName());
            softwareService.save(software);

            SoftwareMachine softwareMachine = software.getSoftwareMachines().stream()
                    .filter(sm -> sm.getMachine().equals(machine))
                    .findFirst()
                    .orElseGet(() -> {
                        SoftwareMachine newSm = new SoftwareMachine();
                        SoftwareMachineId newId = new SoftwareMachineId();
                        newId.setSoftwareId(software.getId());
                        newId.setMachineId(machine.getId());
                        newSm.setId(newId);
                        newSm.setPackageVersion(softwareDTO.getPackageVersion());
                        newSm.setSoftware(software);
                        newSm.setMachine(machine);
                        software.getSoftwareMachines().add(newSm);
                        machine.getSoftwareMachines().add(newSm);
                        return newSm;
                    });

            softwareMachine.setPackageVersion(softwareMachine.getPackageVersion());

            softwareMachineService.save(softwareMachine);
            softwareService.save(software);
            machineService.save(machine);
        });
    }

    public void deleteObsoleteAccountMachines(Machine machine, Set<String> updatedUsernames) {
        Set<AccountMachine> existingAccountMachines = new HashSet<>(machine.getAccountMachines());

        existingAccountMachines.removeIf(accountMachine -> {
            boolean isObsolete = !updatedUsernames.contains(accountMachine.getAccount().getUsername());
            if (isObsolete) {
                Account account = accountMachine.getAccount();
                account.getAccountMachines().remove(accountMachine);

                machine.getAccountMachines().remove(accountMachine);

                accountMachineService.delete(accountMachine);
            }

            return isObsolete;
        });
    }

    public void deleteObsoleteSoftwareMachines(Machine machine, Set<String> updatedPackages) {
        Set<SoftwareMachine> existingSoftwareMachines = new HashSet<>(machine.getSoftwareMachines());

        existingSoftwareMachines.removeIf(softwareMachine -> {
            boolean isObsolete = !updatedPackages.contains(softwareMachine.getSoftware().getPackageName());
            if (isObsolete) {
                Software software = softwareMachine.getSoftware();
                software.getSoftwareMachines().remove(softwareMachine);

                machine.getSoftwareMachines().remove(softwareMachine);

                softwareMachineService.delete(softwareMachine);
            }

            return isObsolete;
        });
    }
}
