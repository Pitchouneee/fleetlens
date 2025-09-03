package fr.corentinbringer.fleetlens.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_api_token")
public class Token {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private String name;

    private boolean revoked = false;
}
