package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.application.dto.machine.MachineFilterRequest;
import fr.corentinbringer.fleetlens.application.dto.machine.MachineListView;
import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.repository.MachineRepository;
import fr.corentinbringer.fleetlens.domain.specification.MachineSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;
    private final ModelMapper modelMapper;

    public Machine findById(UUID id) {
        Optional<Machine> machine = machineRepository.findById(id);
        return machine.orElseThrow(() -> new EntityNotFoundException("Machine with provided UUID not found"));
    }

    public Machine findByHostname(String hostname) {
        return machineRepository.findByHostname(hostname).orElse(new Machine());
    }

    public Page<MachineListView> findAll(int page, int size, MachineFilterRequest filterRequest) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Machine> specification = MachineSpecification.filterBy(filterRequest);

        return machineRepository.findAll(specification, pageable).map(machine ->
                modelMapper.map(machine, MachineListView.class)
        );
    }

    public void save(Machine machine) {
        machineRepository.save(machine);
    }

    public long totalMachines() {
        return machineRepository.count();
    }

    public Map<String, Long> countByOperatingSystem() {
        List<Object[]> results = machineRepository.countByOperatingSystem();

        return results.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0], // operatingSystem (key)
                        obj -> (Long) obj[1]   // count (value)
                ));
    }
}
