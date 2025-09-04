package fr.corentinbringer.fleetlens.model;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record ApiKeyRequest(
        @NotBlank String name,
        Instant expiresAt
) {
}
