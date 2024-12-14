package fr.corentinbringer.fleetlens.presentation.mvc.controller;

import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final MachineService machineService;

    @GetMapping
    public String dashboard(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "12") int size,
                            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Machine> machinePage = machineService.findAll(pageable);

        model.addAttribute("machines", machinePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", machinePage.getTotalPages());
        model.addAttribute("nextPage", page + 1 < machinePage.getTotalPages() ? page + 1 : page);
        model.addAttribute("prevPage", page - 1 >= 0 ? page - 1 : page);

        return "dashboard/index";
    }
}
