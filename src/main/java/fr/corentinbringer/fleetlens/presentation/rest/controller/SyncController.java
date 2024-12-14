package fr.corentinbringer.fleetlens.presentation.rest.controller;

import fr.corentinbringer.fleetlens.synchronization.dto.SyncRequest;
import fr.corentinbringer.fleetlens.synchronization.service.SyncService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/systems")
public class SyncController {

    private final SyncService syncService;

    @PostMapping("/sync")
    public ResponseEntity<?> syncSystemData(@RequestBody @Valid SyncRequest syncRequest) {
        syncService.syncData(syncRequest);
        return ResponseEntity.ok("Synchronization complete");
    }
}
