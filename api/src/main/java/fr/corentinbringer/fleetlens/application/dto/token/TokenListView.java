package fr.corentinbringer.fleetlens.application.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenListView {

    private UUID id;
    private String name;
    private boolean revoked;
}
