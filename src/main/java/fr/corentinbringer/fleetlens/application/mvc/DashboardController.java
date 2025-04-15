package fr.corentinbringer.fleetlens.application.mvc;

import fr.corentinbringer.fleetlens.domain.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalAccounts", dashboardService.getTotalAccounts());
        model.addAttribute("totalMachines", dashboardService.getTotalMachines());
        model.addAttribute("totalSoftwares", dashboardService.getTotalSoftwares());
        model.addAttribute("osDistribution", dashboardService.getOSDistribution());

        return "dashboard/index";
    }
}
