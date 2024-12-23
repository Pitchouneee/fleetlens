package fr.corentinbringer.fleetlens.domain.specification;

import fr.corentinbringer.fleetlens.application.dto.account.AccountFilterRequest;
import fr.corentinbringer.fleetlens.domain.model.Account;
import fr.corentinbringer.fleetlens.domain.model.Account_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public interface AccountSpecification {

    static Specification<Account> filterBy(AccountFilterRequest filterRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getUsername() != null && !filterRequest.getUsername().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Account_.USERNAME)), "%" + filterRequest.getUsername().toLowerCase() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
