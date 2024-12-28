package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.application.dto.profile.EditUserProfilRequest;
import fr.corentinbringer.fleetlens.domain.model.User;
import fr.corentinbringer.fleetlens.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User with provided Email not found"));
    }

    public void editUserProfile(UUID id, EditUserProfilRequest userProfilRequest) {
        User user = userRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("User with provided UUID not found"));
        user.setEmail(userProfilRequest.getEmail());
        user.setFirstName(userProfilRequest.getFirstName());
        user.setLastName(userProfilRequest.getLastName());

        if (!userProfilRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userProfilRequest.getPassword()));
        }

        userRepository.save(user);
    }
}
