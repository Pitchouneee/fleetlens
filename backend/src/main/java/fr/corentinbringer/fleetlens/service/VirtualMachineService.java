package fr.corentinbringer.fleetlens.service;

import fr.corentinbringer.fleetlens.exception.ResourceNotFoundException;
import fr.corentinbringer.fleetlens.model.virtualmachine.*;
import fr.corentinbringer.fleetlens.repository.VirtualMachineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VirtualMachineService {

    private final VirtualMachineRepository virtualMachineRepository;

    @Transactional
    public UpsertOutcome upsert(VmIngestRequest dto) {
        VirtualMachine vm = virtualMachineRepository.findByHostname(dto.hostname()).orElse(null);
        boolean created = (vm == null);
        if (created) {
            vm = new VirtualMachine();
            vm.setHostname(dto.hostname());
        }

        vm.setIpAddress(dto.ipAddress());
        vm.setOsType(dto.osType());
        vm.setUptime(dto.uptime());

        vm.setRamTotal(dto.ramTotal());
        vm.setRamUsed(dto.ramUsed());
        vm.setCpuCores(dto.cpuCores());
        vm.setCpuUsage(dto.cpuUsage());
        vm.setDiskTotal(dto.diskTotal());
        vm.setDiskUsed(dto.diskUsed());

        if (dto.networkInterfaces() != null) {
            List<VmNetworkInterface> mapped = dto.networkInterfaces().stream().map(ni -> {
                VmNetworkInterface e = new VmNetworkInterface();
                e.setName(ni.name());
                e.setIpAddress(ni.ipAddress());
                e.setMacAddress(ni.macAddress());
                return e;
            }).toList();
            vm.replaceInterfaces(mapped);
        }

        if (dto.openPorts() != null) {
            List<VmOpenPort> mapped = dto.openPorts().stream().map(p -> {
                VmOpenPort e = new VmOpenPort();
                e.setPort(p.port());
                e.setProtocol(VmOpenPort.Protocol.valueOf(p.protocol().toUpperCase(Locale.ROOT)));
                return e;
            }).toList();
            vm.replaceOpenPorts(mapped);
        }

        try {
            var saved = virtualMachineRepository.saveAndFlush(vm);
            return new UpsertOutcome(created, saved.getId());
        } catch (DataIntegrityViolationException dup) {
            VirtualMachine existing = virtualMachineRepository.findByHostname(dto.hostname())
                    .orElseThrow(() -> dup);

            existing.setHostname(vm.getHostname());
            existing.setIpAddress(vm.getIpAddress());
            existing.setOsType(vm.getOsType());
            existing.setUptime(vm.getUptime());
            existing.setRamTotal(vm.getRamTotal());
            existing.setRamUsed(vm.getRamUsed());
            existing.setCpuCores(vm.getCpuCores());
            existing.setCpuUsage(vm.getCpuUsage());
            existing.setDiskTotal(vm.getDiskTotal());
            existing.setDiskUsed(vm.getDiskUsed());
            existing.replaceInterfaces(vm.getNetworkInterfaces());
            existing.replaceOpenPorts(vm.getOpenPorts());

            var saved = virtualMachineRepository.save(existing);
            return new UpsertOutcome(false, saved.getId());
        }
    }

    public Page<VmList> list(Pageable pageable) {
        return virtualMachineRepository.findBy(pageable);
    }

    public VmDetailResponse getVmDetails(UUID id) {
        return virtualMachineRepository.findProjectionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Virtual machine not found"));
    }
}
