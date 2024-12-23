package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.domain.model.SoftwareMachine;
import fr.corentinbringer.fleetlens.domain.repository.SoftwareMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftwareMachineService {

    private final SoftwareMachineRepository softwareMachineRepository;

    public void delete(SoftwareMachine softwareMachine) {
        softwareMachineRepository.delete(softwareMachine);
    }

    public void save(SoftwareMachine softwareMachine) {
        softwareMachineRepository.save(softwareMachine);
    }
}
