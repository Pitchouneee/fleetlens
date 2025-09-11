package fr.corentinbringer.fleetlens.model.apikey;

import java.time.Instant;
import java.util.UUID;

public record ApiKeyList(
        UUID id,
        String name,
        Instant createdAt,
        Instant expiresAt,
        boolean enabled,
        Instant lastUsedAt,
        String preview
) {
}
