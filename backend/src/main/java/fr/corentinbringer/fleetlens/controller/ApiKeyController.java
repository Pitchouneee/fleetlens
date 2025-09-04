package fr.corentinbringer.fleetlens.controller;

import fr.corentinbringer.fleetlens.model.ApiKeyRequest;
import fr.corentinbringer.fleetlens.model.ApiKeyResponse;
import fr.corentinbringer.fleetlens.service.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
