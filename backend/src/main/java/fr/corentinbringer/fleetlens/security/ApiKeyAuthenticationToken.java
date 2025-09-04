package fr.corentinbringer.fleetlens.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final String credentials;

    public ApiKeyAuthenticationToken(String presentedKey) {
        super(null);
        this.principal = "apiKey";
        this.credentials = presentedKey;
        setAuthenticated(false);
    }

    public ApiKeyAuthenticationToken(ApiKeyPrincipal principal, Collection<? extends GrantedAuthority> auths) {
        super(auths);
        this.principal = principal;
        this.credentials = null;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
