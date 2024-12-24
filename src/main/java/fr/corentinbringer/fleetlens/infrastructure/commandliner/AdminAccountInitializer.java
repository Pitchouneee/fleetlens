package fr.corentinbringer.fleetlens.infrastructure.commandliner;

import fr.corentinbringer.fleetlens.domain.model.User;
import fr.corentinbringer.fleetlens.domain.model.UserRole;
import fr.corentinbringer.fleetlens.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminAccountInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.firstName}")
    private String firstName;

    @Value("${admin.lastName}")
    private String lastName;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        // Check database for users
        if (userService.countUsers() == 0) {
            // Create a default admin user with the information passed via the arguments
            User adminUser = new User();
            adminUser.setFirstName(firstName);
            adminUser.setLastName(lastName);
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setRole(UserRole.ADMIN);

            userService.save(adminUser);

            log.info("Admin user created successfully.");
        } else {
            log.info("Admin user already exists.");
        }
    }
}
