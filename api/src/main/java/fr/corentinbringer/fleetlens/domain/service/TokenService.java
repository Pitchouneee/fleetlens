package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.application.dto.token.CreateTokenRequest;
import fr.corentinbringer.fleetlens.application.dto.token.TokenListView;
import fr.corentinbringer.fleetlens.domain.model.Token;
import fr.corentinbringer.fleetlens.domain.repository.TokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;

    public Page<TokenListView> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return tokenRepository.findAll(pageable).map(token ->
                modelMapper.map(token, TokenListView.class)
        );
    }

    public void delete(UUID id) {
        tokenRepository.deleteById(id);
    }

    public Token createNewToken(CreateTokenRequest tokenRequest) {
        Token token = Token.builder()
                .name(tokenRequest.getName())
                .token(UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", ""))
                .build();

        return tokenRepository.save(token);
    }

    public void revokeToken(UUID id) {
        Token token = tokenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Token with provided ID not found"));

        token.setRevoked(true);
        tokenRepository.save(token);
    }

    public boolean isTokenValid(String tokenValue) {
        return tokenRepository.findByToken(tokenValue)
                .filter(token -> !token.isRevoked())
                .isPresent();
    }
}
