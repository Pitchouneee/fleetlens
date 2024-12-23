package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.application.dto.software.SoftwareFilterRequest;
import fr.corentinbringer.fleetlens.application.dto.software.SoftwareListView;
import fr.corentinbringer.fleetlens.domain.model.Software;
import fr.corentinbringer.fleetlens.domain.repository.SoftwareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SoftwareService {

    private final SoftwareRepository softwareRepository;

    public Software findSoftwareByPackageName(String packageName) {
        return softwareRepository.findByPackageName(packageName).orElse(new Software());
    }

    public void save(Software software) {
        softwareRepository.save(software);
    }

    public Page<SoftwareListView> getAllSoftwareWithVersion(int page, int size, SoftwareFilterRequest filterRequest) {
        Pageable pageable = PageRequest.of(page, size);

        return softwareRepository.findAllDistinctSoftwareWithVersion(pageable, filterRequest.getSearchTerm());
    }
}
