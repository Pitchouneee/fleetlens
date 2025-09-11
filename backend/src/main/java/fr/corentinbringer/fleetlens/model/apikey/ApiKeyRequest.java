package fr.corentinbringer.fleetlens.model.apikey;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record ApiKeyRequest(
        @NotBlank String name,
        Instant expiresAt
) {
}
