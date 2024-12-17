package fr.corentinbringer.fleetlens.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final MachineService machineService;
    private final AccountService accountService;

    public long getTotalAccounts() {
        return accountService.totalUniqueAccount();
    }

    public long getTotalMachines() {
        return machineService.totalMachines();
    }

    public Map<String, Long> getOSDistribution() {
        return machineService.countByOperatingSystem();
    }
}
