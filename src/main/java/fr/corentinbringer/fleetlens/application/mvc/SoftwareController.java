package fr.corentinbringer.fleetlens.application.mvc;

import fr.corentinbringer.fleetlens.domain.service.SoftwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/softwares")
public class SoftwareController {

    private final SoftwareService softwareService;
}
