package fr.corentinbringer.fleetlens.service;

import fr.corentinbringer.fleetlens.exception.ResourceNotFoundException;
import fr.corentinbringer.fleetlens.model.apikey.ApiKey;
import fr.corentinbringer.fleetlens.model.apikey.ApiKeyList;
import fr.corentinbringer.fleetlens.model.apikey.ApiKeyResponse;
import fr.corentinbringer.fleetlens.repository.ApiKeyRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    @Value("${security.api-key.pepper:}")
    private String pepper;

    private static String sha256Hex(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(s.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public ApiKeyResponse createApiKey(String name, @Nullable Instant expiresAt) {
        String plain = "fk_" + UUID.randomUUID().toString().replace("-", "");
        String hash = sha256Hex(plain + (pepper == null ? "" : pepper));

        String prefix = plain.substring(0, Math.min(12, plain.length()));
        String suffix = plain.substring(Math.max(plain.length() - 5, 0));

        var entity = new ApiKey();
        entity.setKeyHash(hash);
        entity.setName(name);
        entity.setExpiresAt(expiresAt);
        entity.setEnabled(true);
        entity.setKeyPrefix(prefix);
        entity.setKeySuffix(suffix);
        apiKeyRepository.save(entity);

        return new ApiKeyResponse(plain);
    }

    public Optional<ApiKey> authenticate(String presentedKey) {
        String hash = sha256Hex(peppered(presentedKey));
        return apiKeyRepository.findByKeyHash(hash)
                .filter(ApiKey::isEnabled)
                .filter(k -> k.getExpiresAt() == null || k.getExpiresAt().isAfter(Instant.now()))
                .map(this::touch);
    }

    private ApiKey touch(ApiKey k) {
        k.setLastUsedAt(Instant.now());
        apiKeyRepository.save(k);
        return k;
    }

    private String peppered(String key) {
        return pepper == null ? key : key + pepper;
    }

    public Page<ApiKeyList> list(Pageable pageable) {
        return apiKeyRepository.findBy(pageable);
    }

    public void revoke(UUID id) {
        ApiKey key = apiKeyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("API key not found"));

        if (!key.isEnabled()) {
            return;
        }

        key.setEnabled(false);
        apiKeyRepository.save(key);
    }
}
