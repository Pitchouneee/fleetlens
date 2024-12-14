package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.model.Software;
import fr.corentinbringer.fleetlens.domain.repository.SoftwareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftwareService {

    private final SoftwareRepository softwareRepository;

    public Software findSoftwareByNameAndMachine(String name, Machine machine) {
        return softwareRepository.findByPackageNameAndMachine(name, machine).orElse(new Software());
    }

    public void save(Software software) {
        softwareRepository.save(software);
    }
}
