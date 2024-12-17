package fr.corentinbringer.fleetlens.application.mvc;

import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/machines")
public class MachineController {

    private final MachineService machineService;

    @GetMapping
    public String getAllMachines(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "12") int size,
                                 Model model) {
        Page<Machine> machinePage = machineService.findAll(page, size);

        model.addAttribute("machines", machinePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", machinePage.getTotalPages());
        model.addAttribute("nextPage", page + 1 < machinePage.getTotalPages() ? page + 1 : page);
        model.addAttribute("prevPage", page - 1 >= 0 ? page - 1 : page);

        return "machines/list";
    }

    @GetMapping("/{machineId}")
    public String getMachineDetails(@PathVariable UUID machineId, Model model) {
        Machine machine = machineService.findById(machineId);
        model.addAttribute("machine", machine);
        model.addAttribute("accounts", machine.getAccounts());
        model.addAttribute("systemGroups", machine.getSystemGroups());
        model.addAttribute("softwares", machine.getSoftwares());
        return "machines/details";
    }
}
