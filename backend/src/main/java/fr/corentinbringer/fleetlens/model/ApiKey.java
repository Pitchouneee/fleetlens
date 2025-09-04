package fr.corentinbringer.fleetlens.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_api_keys", indexes = {
        @Index(name = "idx_apikey_hash", columnList = "keyHash", unique = true)
})
public class ApiKey {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true, length = 64)
    private String keyHash;

    @Column(nullable = false)
    private String name;

    private Instant createdAt = Instant.now();
    private Instant expiresAt;
    private boolean enabled = true;
    private Instant lastUsedAt;

    @Column(length = 20)
    private String keyPrefix;

    @Column(length = 20)
    private String keySuffix;

    @Transient
    public String getPreview() {
        if (keyPrefix == null || keySuffix == null) return "…";
        return keyPrefix + "..." + keySuffix;
    }
}
