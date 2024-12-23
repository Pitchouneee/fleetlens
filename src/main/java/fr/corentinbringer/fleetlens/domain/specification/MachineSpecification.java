package fr.corentinbringer.fleetlens.domain.specification;

import fr.corentinbringer.fleetlens.application.dto.machine.MachineFilterRequest;
import fr.corentinbringer.fleetlens.domain.model.Machine;
import fr.corentinbringer.fleetlens.domain.model.Machine_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public interface MachineSpecification {

    static Specification<Machine> filterBy(MachineFilterRequest filterRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getSearchTerm() != null && !filterRequest.getSearchTerm().isEmpty()) {
                String searchTerm = "%" + filterRequest.getSearchTerm().toLowerCase() + "%";

                Predicate hostnamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Machine_.hostname)),
                        searchTerm
                );

                Predicate ipPredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Machine_.ipAddressV4)),
                        searchTerm
                );

                predicates.add(criteriaBuilder.or(hostnamePredicate, ipPredicate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
