package fr.corentinbringer.fleetlens.controller;

import fr.corentinbringer.fleetlens.model.apikey.ApiKeyList;
import fr.corentinbringer.fleetlens.model.apikey.ApiKeyRequest;
import fr.corentinbringer.fleetlens.model.apikey.ApiKeyResponse;
import fr.corentinbringer.fleetlens.service.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/api-keys")
@RequiredArgsConstructor
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    @PostMapping
    public ResponseEntity<ApiKeyResponse> create(@Valid @RequestBody ApiKeyRequest req) {
        var created = apiKeyService.createApiKey(req.name(), req.expiresAt());
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public Page<ApiKeyList> list(@ParameterObject Pageable pageable) {
        return apiKeyService.list(pageable);
    }

    @PostMapping("/{id}/revoke")
    public ResponseEntity<Void> revoke(@PathVariable UUID id) {
        apiKeyService.revoke(id);
        return ResponseEntity.noContent().build();
    }
}
