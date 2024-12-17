package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.application.dto.software.ListSoftwareProjection;
import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.model.Software;
import fr.corentinbringer.fleetlens.domain.repository.SoftwareRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftwareService {

    private final SoftwareRepository softwareRepository;
    private final ModelMapper modelMapper;

    public Software findSoftwareByNameAndMachine(String name, Machine machine) {
        return softwareRepository.findByPackageNameAndMachine(name, machine).orElse(new Software());
    }

    public void save(Software software) {
        softwareRepository.save(software);
    }

    public Page<ListSoftwareProjection> findDistinctSoftware(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return softwareRepository.findDistinctSoftware(pageable);
    }
}
