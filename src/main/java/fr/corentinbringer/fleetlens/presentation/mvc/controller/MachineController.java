package fr.corentinbringer.fleetlens.presentation.mvc.controller;

import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/machines")
public class MachineController {

    private final MachineService machineService;

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
