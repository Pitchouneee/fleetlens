package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.repository.MachineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;

    public Machine findById(UUID id) {
        Optional<Machine> machine = machineRepository.findById(id);
        return machine.orElseThrow(() -> new EntityNotFoundException("Machine with provided UUID not found"));
    }

    public Machine findByHostname(String hostname) {
        return machineRepository.findByHostname(hostname).orElse(new Machine());
    }

    public Page<Machine> findAll(Pageable pageable) {
        return machineRepository.findAll(pageable);
    }

    public void save(Machine machine) {
        machineRepository.save(machine);
    }
}
