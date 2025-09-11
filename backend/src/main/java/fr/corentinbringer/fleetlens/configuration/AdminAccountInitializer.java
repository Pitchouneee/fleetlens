package fr.corentinbringer.fleetlens.configuration;

import fr.corentinbringer.fleetlens.model.user.AdminProperties;
import fr.corentinbringer.fleetlens.model.user.User;
import fr.corentinbringer.fleetlens.model.user.UserRole;
import fr.corentinbringer.fleetlens.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class AdminAccountInitializer {

    @Bean
    public ApplicationRunner initAdminAccount(UserRepository userRepository,
                                              AdminProperties adminProperties,
                                              PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setFirstName(adminProperties.firstname());
                admin.setLastName(adminProperties.lastname());
                admin.setEmail(adminProperties.email());
                admin.setPassword(passwordEncoder.encode(adminProperties.password()));
                admin.setRole(UserRole.ADMIN);
                userRepository.save(admin);
                log.info("Admin account created");
            }
        };
    }
}
