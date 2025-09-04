package fr.corentinbringer.fleetlens.security;

import java.security.Principal;
import java.util.UUID;

public record ApiKeyPrincipal(UUID id, String name, String preview) implements Principal {

    @Override
    public String getName() {
        return "apiKey:" + id;
    }
}
