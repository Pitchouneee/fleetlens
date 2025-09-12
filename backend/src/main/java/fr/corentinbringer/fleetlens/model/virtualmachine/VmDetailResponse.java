package fr.corentinbringer.fleetlens.model.virtualmachine;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

public interface VmDetailResponse {

    UUID getId();

    String getHostname();
    String getIpAddress();
    String getOsType();

    String getUptime();

    Long getRamTotal();
    Long getRamUsed();

    Integer getCpuCores();
    Double getCpuUsage();

    Long getDiskTotal();
    Long getDiskUsed();

    List<NetworkInterfaceProjection> getNetworkInterfaces();
    List<OpenPortProjection> getOpenPorts();

    @Value("#{@mathUtils.calculatePercentage(target.ramUsed, target.ramTotal)}")
    Double getRamUsagePercentage();

    @Value("#{@mathUtils.calculatePercentage(target.diskUsed, target.diskTotal)}")
    Double getDiskUsagePercentage();

    interface NetworkInterfaceProjection {
        String getName();
        String getIpAddress();
        String getMacAddress();
    }

    interface OpenPortProjection {
        Integer getPort();

        @Value("#{target.protocol.name()}")
        String getProtocol();
    }
}
