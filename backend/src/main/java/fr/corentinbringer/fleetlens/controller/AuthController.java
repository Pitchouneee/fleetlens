package fr.corentinbringer.fleetlens.controller;

import fr.corentinbringer.fleetlens.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        var authToken = new UsernamePasswordAuthenticationToken(req.email(), req.password());
        Authentication auth = authenticationManager.authenticate(authToken); // via DaoAuthenticationProvider
        var user = (UserDetails) auth.getPrincipal();
        String jwt = jwtService.generateToken(user);
        return ResponseEntity.ok(new TokenResponse(jwt, "Bearer", Instant.now().plusSeconds(3600)));
    }

    public record LoginRequest(String email, String password) {
    }

    public record TokenResponse(String accessToken, String tokenType, Instant expiresAt) {
    }
}
