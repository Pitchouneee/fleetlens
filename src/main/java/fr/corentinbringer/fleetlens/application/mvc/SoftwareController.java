package fr.corentinbringer.fleetlens.application.mvc;

import fr.corentinbringer.fleetlens.application.dto.software.ListSoftwareProjection;
import fr.corentinbringer.fleetlens.domain.service.SoftwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/softwares")
public class SoftwareController {

    private final SoftwareService softwareService;

    @GetMapping
    public String getAllSoftware(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "12") int size,
                                 Model model) {
        Page<ListSoftwareProjection> softwarePage = softwareService.findDistinctSoftware(page, size);

        model.addAttribute("softwares", softwarePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", softwarePage.getTotalPages());
        model.addAttribute("nextPage", page + 1 < softwarePage.getTotalPages() ? page + 1 : page);
        model.addAttribute("prevPage", page - 1 >= 0 ? page - 1 : page);

        return "softwares/list";
    }
}
