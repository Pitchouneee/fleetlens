package fr.corentinbringer.fleetlens.application.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsView {

    private UUID id;
    private String username;
    private List<MachineView> machines;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MachineView {
        private String hostname;
        private List<String> systemGroups;
        private boolean isRoot;
    }
}
