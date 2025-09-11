package fr.corentinbringer.fleetlens.model.user;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.admin")
public record AdminProperties(
        String firstname,
        String lastname,
        String email,
        String password
) {
}
