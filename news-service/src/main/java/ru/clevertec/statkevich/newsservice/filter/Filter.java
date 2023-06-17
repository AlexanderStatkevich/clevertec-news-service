package ru.clevertec.statkevich.newsservice.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


/**
 * [{
 * "type": "string",
 * "value": "***",
 * "field": "model"
 * },{
 * "type": "numeric",
 * "value": "***",
 * "field": "year",
 * "comparison": "gt"
 * }]
 */
public class Filter<T> implements Specification<T> {

    private List<Condition> conditions;

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = buildPredicates(root, criteriaQuery, criteriaBuilder);
        return predicates.size() > 1
                ? criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))
                : predicates.get(0);
    }

    private List<Predicate> buildPredicates(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
//       return conditions.stream().map(this::buildPredicate).collect(toList());
        List<Predicate> predicates = new ArrayList<>();
        conditions.forEach(condition -> predicates.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));
        return predicates;
    }

    public Predicate buildPredicate(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (condition.comparison()) {
            case EQUALS:
                return buildEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            case GREATER_THAN:
                return buildGreaterThanPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            case LESS_THAN:
                return buildLessThanPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            case NOT_EQUALS:
                return buildNotEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            case IS_NULL:
                return buildIsNullPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            default:
                return null;
        }
    }

    private Predicate buildEqualsPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(condition.field()), condition.value());
    }

    private Predicate buildGreaterThanPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
//        return criteriaBuilder.greaterThan(root.get(condition.field()), condition.value());
        return null;
    }

    private Predicate buildLessThanPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get(condition.field()),
                "%" + condition.value() + "%");
    }

    private Predicate buildNotEqualsPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }

    private Predicate buildIsNullPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
