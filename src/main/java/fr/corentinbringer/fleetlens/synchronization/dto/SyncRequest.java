package fr.corentinbringer.fleetlens.synchronization.dto;

import fr.corentinbringer.fleetlens.application.dto.account.SyncAccountDTO;
import fr.corentinbringer.fleetlens.application.dto.machine.SyncMachineDTO;
import fr.corentinbringer.fleetlens.application.dto.software.SyncSoftwareDTO;
import fr.corentinbringer.fleetlens.application.dto.systemgroup.SyncSystemGroupDTO;
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

    private SyncMachineDTO machine;
    private List<SyncAccountDTO> accounts;
    private List<SyncSystemGroupDTO> groups;
    private List<SyncSoftwareDTO> software;
}
