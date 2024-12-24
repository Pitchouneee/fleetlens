package fr.corentinbringer.fleetlens.domain.specification;

import fr.corentinbringer.fleetlens.application.dto.user.UserFilterRequest;
import fr.corentinbringer.fleetlens.domain.model.*;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public interface UserSpecification {

    static Specification<User> filterBy(UserFilterRequest filterRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getSearchTerm() != null && !filterRequest.getSearchTerm().isEmpty()) {
                String searchTerm = "%" + filterRequest.getSearchTerm().toLowerCase() + "%";

                Predicate firstNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(User_.firstName)),
                        searchTerm
                );

                Predicate lastNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(User_.lastName)),
                        searchTerm
                );

                Predicate emailPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(User_.email)),
                        searchTerm
                );

                predicates.add(criteriaBuilder.or(firstNamePredicate, lastNamePredicate, emailPredicate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
