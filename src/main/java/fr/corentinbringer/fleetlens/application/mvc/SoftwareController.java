package fr.corentinbringer.fleetlens.application.mvc;

import fr.corentinbringer.fleetlens.application.dto.software.SoftwareDetailsView;
import fr.corentinbringer.fleetlens.application.dto.software.SoftwareFilterRequest;
import fr.corentinbringer.fleetlens.application.dto.software.SoftwareListView;
import fr.corentinbringer.fleetlens.domain.service.SoftwareService;
import jakarta.validation.Valid;
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
@RequestMapping("/softwares")
public class SoftwareController {

    private final SoftwareService softwareService;

    @GetMapping
    public String getAllSoftware(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "12") int size,
                                 @Valid SoftwareFilterRequest filterRequest,
                                 Model model) {
        Page<SoftwareListView> softwarePage = softwareService.getAllSoftwareWithVersion(page, size, filterRequest);

        model.addAttribute("softwares", softwarePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", softwarePage.getTotalPages());
        model.addAttribute("nextPage", page + 1 < softwarePage.getTotalPages() ? page + 1 : page);
        model.addAttribute("prevPage", page - 1 >= 0 ? page - 1 : page);
        model.addAttribute("searchTerm", filterRequest.getSearchTerm());

        return "softwares/list";
    }

    @GetMapping("/{softwareId}")
    public String getSoftwareDetails(@PathVariable UUID softwareId,
                                     @RequestParam String version,
                                     Model model) {
        SoftwareDetailsView software = softwareService.findSoftwareWithMachinesByIdAndVersion(softwareId, version);

        model.addAttribute("software", software);

        return "softwares/details";
    }
}
