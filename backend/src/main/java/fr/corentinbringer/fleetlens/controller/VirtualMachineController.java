package fr.corentinbringer.fleetlens.controller;

import fr.corentinbringer.fleetlens.model.virtualmachine.VmList;
import fr.corentinbringer.fleetlens.service.VirtualMachineService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vms")

@RequiredArgsConstructor
public class VirtualMachineController {

    private final VirtualMachineService virtualMachineService;

    @GetMapping
    public Page<VmList> list(@ParameterObject Pageable pageable) {
        return virtualMachineService.list(pageable);
    }
}
