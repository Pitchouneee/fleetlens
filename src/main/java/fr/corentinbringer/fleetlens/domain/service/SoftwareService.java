package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.application.dto.software.SoftwareDetailsView;
import fr.corentinbringer.fleetlens.application.dto.software.SoftwareFilterRequest;
import fr.corentinbringer.fleetlens.application.dto.software.SoftwareListView;
import fr.corentinbringer.fleetlens.application.dto.software.SoftwareMachineDetailsView;
import fr.corentinbringer.fleetlens.domain.model.Software;
import fr.corentinbringer.fleetlens.domain.repository.SoftwareRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SoftwareService {

    private final SoftwareRepository softwareRepository;
    private final ModelMapper modelMapper;

    public Software findSoftwareByPackageName(String packageName) {
        return softwareRepository.findByPackageName(packageName).orElse(null);
    }

    public void save(Software software) {
        softwareRepository.save(software);
    }

    public Page<SoftwareListView> getAllSoftwareWithVersion(int page, int size, SoftwareFilterRequest filterRequest) {
        Pageable pageable = PageRequest.of(page, size);

        return softwareRepository.findAllDistinctSoftwareWithVersion(
                pageable,
                filterRequest.getSearchTerm() != null ? filterRequest.getSearchTerm() : ""
        );
    }

    @Transactional
    public SoftwareDetailsView findSoftwareWithMachinesByIdAndVersion(UUID softwareId, String version) {
        SoftwareDetailsView softwareDetails = softwareRepository
                .findSoftwareDetailsByIdAndVersion(softwareId, version)
                .orElseThrow(() -> new EntityNotFoundException("Software with provided UUID not found"));

        List<SoftwareMachineDetailsView> machines = softwareRepository.findMachinesForSoftware(softwareId, version);
        softwareDetails.setMachines(machines);

        return softwareDetails;
    }
}
