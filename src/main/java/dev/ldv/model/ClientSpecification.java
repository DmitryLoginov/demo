package dev.ldv.model;

import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import dev.ldv.dto.ClientFilter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientSpecification {
    public static Specification<Client> withFilter(ClientFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getFirstName() != null && !filter.getFirstName().isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("fullName").get("firstName")),
                                "%" + filter.getFirstName().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getLastName() != null && !filter.getLastName().isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("fullName").get("lastName")),
                                "%" + filter.getLastName().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getMiddleName() != null && !filter.getMiddleName().isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("fullName").get("middleName")),
                                "%" + filter.getMiddleName().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getStatus() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("status"), filter.getStatus().getValue())
                );
            }

            if (filter.getMdmCode() != null) {
                predicates.add(criteriaBuilder.equal(root.get("mdmCode"), filter.getMdmCode()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}