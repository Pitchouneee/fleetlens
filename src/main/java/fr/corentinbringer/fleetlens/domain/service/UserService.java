package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.domain.model.User;
import fr.corentinbringer.fleetlens.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public long countUsers() {
        return userRepository.count();
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
