package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.domain.model.Token;
import fr.corentinbringer.fleetlens.domain.repository.TokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public List<Token> getAllToken() {
        return tokenRepository.findAll();
    }

    public void delete(Long id) {
        tokenRepository.deleteById(id);
    }

    public void createNewToken() {
        Token token = Token.builder()
                .token(UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", ""))
                .build();

        tokenRepository.save(token);
    }

    public void revokeToken(Long id) {
        Token token = tokenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Token with provided ID not found"));

        token.setRevoked(true);
        tokenRepository.save(token);
    }
}
