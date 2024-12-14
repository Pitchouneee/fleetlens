package fr.corentinbringer.fleetlens.domain.repository;

import fr.corentinbringer.fleetlens.domain.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

}
