package org.example.financial_transactions.model.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.example.financial_transactions.model.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public record TransactionSearch(@Pattern(regexp = "^[0-9]{14}$", message = "sourceAccountNumber must be 14 digits")
                                String sourceAccountNumber,

                                @Pattern(regexp = "^[0-9]{14}$", message = "destinationAccountNumber must be 14 digits")
                                String destinationAccountNumber,

                                @Positive(message = "amount must be positive")
                                Double minAmount,

                                @Positive(message = "amount must be positive")
                                Double maxAmount,

                                Date startDate,

                                Date endDate) implements Specification<Transaction> {
    @Override
    public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (sourceAccountNumber != null) {
            criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("sourceAccount").get("accountNumber"), sourceAccountNumber));
        }
        if (destinationAccountNumber != null) {
            criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("destinationAccount").get("accountNumber"), destinationAccountNumber));
        }
        if (minAmount != null) {
            predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount);
        }
        if (maxAmount != null) {
            predicate = criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount);
        }
        if (startDate != null) {
            predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), startDate);
        }
        if (endDate != null) {
            predicate = criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), endDate);
        }
        return predicate;
    }
}
