package fr.corentinbringer.fleetlens.security;

import fr.corentinbringer.fleetlens.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private final ApiKeyService apiKeyService;
    ;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String presented = (String) authentication.getCredentials();

        return apiKeyService.authenticate(presented)
                .map(apiKey -> {
                    var roleApi = new SimpleGrantedAuthority("ROLE_API");
                    var principal = new ApiKeyPrincipal(apiKey.getId(), apiKey.getName(), apiKey.getPreview());
                    return new ApiKeyAuthenticationToken(principal, List.of(roleApi));
                })
                .orElse(null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
