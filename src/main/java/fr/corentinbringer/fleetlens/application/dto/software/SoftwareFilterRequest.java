package fr.corentinbringer.fleetlens.application.dto.software;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareFilterRequest {

    private String searchTerm; // Package name or version
}
