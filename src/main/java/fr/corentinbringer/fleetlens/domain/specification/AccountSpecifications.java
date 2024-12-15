package fr.corentinbringer.fleetlens.domain.specification;

import fr.corentinbringer.fleetlens.domain.model.Account;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecifications {

    public static Specification<Account> distinctByUsername() {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            query.multiselect(root.get("username"));
            return criteriaBuilder.conjunction();
        };
    }
}
