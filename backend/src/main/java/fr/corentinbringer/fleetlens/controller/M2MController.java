package fr.corentinbringer.fleetlens.controller;

import fr.corentinbringer.fleetlens.model.virtualmachine.UpsertOutcome;
import fr.corentinbringer.fleetlens.model.virtualmachine.VmIngestRequest;
import fr.corentinbringer.fleetlens.service.VirtualMachineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/m2m")
@RequiredArgsConstructor
public class M2MController {

    private final VirtualMachineService virtualMachineService;

    @PutMapping("/v1/ingest")
    public ResponseEntity<Map<String, Object>> putSingle(@Valid @RequestBody VmIngestRequest dto) {
        UpsertOutcome outcome = virtualMachineService.upsert(dto);

        URI self = URI.create("/m2m/v1/ingest/");
        Map<String, Object> payload = Map.of(
                "hostname", dto.hostname(),
                "status", outcome.created() ? "created" : "updated",
                "id", outcome.internalId());

        return outcome.created()
                ? ResponseEntity.created(self).body(payload)
                : ResponseEntity.ok(payload);
    }
}
