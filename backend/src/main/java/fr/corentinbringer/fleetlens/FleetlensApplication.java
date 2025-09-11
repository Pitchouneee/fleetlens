package fr.corentinbringer.fleetlens;

import fr.corentinbringer.fleetlens.model.user.AdminProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableConfigurationProperties(AdminProperties.class)
public class FleetlensApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleetlensApplication.class, args);
    }
}
