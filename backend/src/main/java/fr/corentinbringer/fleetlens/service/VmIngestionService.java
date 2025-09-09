package fr.corentinbringer.fleetlens.service;

import fr.corentinbringer.fleetlens.model.*;
import fr.corentinbringer.fleetlens.repository.VmMachineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VmIngestionService {

    private final VmMachineRepository vmMachineRepository;

    @Transactional
    public UpsertOutcome upsert(VmIngestRequest dto) {
//        VmMachine vm = vmMachineRepository.findByHostname(dto.hostname()).orElseGet(() -> {
//            VmMachine fresh = new VmMachine();
//            fresh.setHostname(dto.hostname());
//            return fresh;
//        });

        VmMachine vm = vmMachineRepository.findByHostname(dto.hostname()).orElse(null);
        boolean created = (vm == null);
        if (created) {
            vm = new VmMachine();
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
                e.setProtocol(VmOpenPort.Protocol.valueOf(p.protocol().toLowerCase()));
                return e;
            }).toList();
            vm.replaceOpenPorts(mapped);
        }

        try {
            var saved = vmMachineRepository.saveAndFlush(vm);
            return new UpsertOutcome(created, saved.getId());
        } catch (DataIntegrityViolationException dup) {
            VmMachine existing = vmMachineRepository.findByHostname(dto.hostname())
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

            var saved = vmMachineRepository.save(existing);
            return new UpsertOutcome(false, saved.getId());
        }
    }

//    @Transactional
//    public List<VmMachine> upsertBatch(List<VmIngestRequest> dtos) {
//        // Transaction par requête; si tu préfères isoler par VM, traite par morceaux
//        List<VmMachine> res = new ArrayList<>(dtos.size());
//
//        for (var dto : dtos) {
//            res.add(upsert(dto));
//        }
//
//        return res;
//    }
}
