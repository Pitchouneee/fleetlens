package fr.corentinbringer.fleetlens.application.dto.synchronization;

import fr.corentinbringer.fleetlens.application.dto.account.CreateAccountSyncRequest;
import fr.corentinbringer.fleetlens.application.dto.machine.CreateMachineSyncRequest;
import fr.corentinbringer.fleetlens.application.dto.software.CreateSoftwareSyncRequest;
import fr.corentinbringer.fleetlens.application.dto.systemgroup.CreateSystemGroupSyncRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SyncRequest {

    private CreateMachineSyncRequest machine;
    private List<CreateAccountSyncRequest> accounts;
    private List<CreateSystemGroupSyncRequest> groups;
    private List<CreateSoftwareSyncRequest> softwares;
}
