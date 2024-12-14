package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.domain.model.SystemGroup;
import fr.corentinbringer.fleetlens.domain.repository.SystemGroupRepository;
import fr.corentinbringer.fleetlens.domain.model.Machine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemGroupService {

    private final SystemGroupRepository systemGroupRepository;

    public SystemGroup findGroupByNameAndMachine(String name, Machine machine) {
        return systemGroupRepository.findByNameAndMachine(name, machine).orElse(new SystemGroup());
    }

    public void save(SystemGroup systemGroup) {
        systemGroupRepository.save(systemGroup);
    }
}
