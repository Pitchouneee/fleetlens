package fr.corentinbringer.fleetlens.synchronization.dto;

import fr.corentinbringer.fleetlens.application.dto.account.AccountDTO;
import fr.corentinbringer.fleetlens.application.dto.machine.MachineDTO;
import fr.corentinbringer.fleetlens.application.dto.software.SoftwareDTO;
import fr.corentinbringer.fleetlens.application.dto.systemgroup.SystemGroupDTO;
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

    private MachineDTO machine;
    private List<AccountDTO> accounts;
    private List<SystemGroupDTO> groups;
    private List<SoftwareDTO> software;
}
