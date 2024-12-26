package fr.corentinbringer.fleetlens.domain.service;

import fr.corentinbringer.fleetlens.application.dto.user.CreateUserRequest;
import fr.corentinbringer.fleetlens.application.dto.user.UserFilterRequest;
import fr.corentinbringer.fleetlens.application.dto.user.UserListView;
import fr.corentinbringer.fleetlens.domain.exception.UserAlreadyExistsException;
import fr.corentinbringer.fleetlens.domain.model.User;
import fr.corentinbringer.fleetlens.domain.repository.UserRepository;
import fr.corentinbringer.fleetlens.domain.specification.UserSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public long countUsers() {
        return userRepository.count();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public Page<UserListView> findAll(int page, int size, UserFilterRequest filterRequest) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<User> specification = UserSpecification.filterBy(filterRequest);

        return userRepository.findAll(specification, pageable).map(user ->
                modelMapper.map(user, UserListView.class)
        );
    }

    public void createUser(CreateUserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("A user with this email already exists: " + userRequest.getEmail());
        }

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());

        userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("User with provided UUID not found"));
    }

    public void editUser(UUID id, CreateUserRequest userRequest) {
        if(id == null) {
            throw new IllegalArgumentException("Provide user id ");
        }

        User user = findById(id);
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());

        userRepository.save(user);
    }
}
