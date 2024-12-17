package fr.corentinbringer.fleetlens.domain.specification;

import fr.corentinbringer.fleetlens.domain.model.Account;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public interface AccountSpecifications {

//    public static Specification<Account> distinctByUsername() {
//        return (root, query, criteriaBuilder) -> {
//            query.distinct(true);
//            return criteriaBuilder.isNotNull(root.get("username"));
//        };
//    }

    static Specification<Account> distinctUsernames() {
        return (root, query, criteriaBuilder) -> {
//            query.distinct(true);
//
//            return criteriaBuilder.conjunction();

            Subquery<String> subquery = query.subquery(String.class);
            Root<Account> subRoot = subquery.from(Account.class);
            subquery.select(subRoot.get("username")).distinct(true);

            // Condition pour ne garder que les comptes avec un username unique
            return root.get("username").in(subquery);
        };
    }
}
