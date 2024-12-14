package fr.corentinbringer.fleetlens.application.dto.systemgroup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemGroupDTO {

    private String name;
    private List<String> members;
}