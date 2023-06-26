package ru.clevertec.statkevich.newsservice.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


@Setter
public class Filter<T> implements Specification<T> {

    private List<Condition> conditions;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = buildPredicates(root, criteriaBuilder);
        return predicates.size() > 1
                ? criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))
                : predicates.get(0);
    }

    private List<Predicate> buildPredicates(Root<T> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        conditions.forEach(condition -> predicates.add(buildPredicate(condition, root, criteriaBuilder)));
        return predicates;
    }

    public Predicate buildPredicate(Condition condition, Root<T> root, CriteriaBuilder criteriaBuilder) {
        return switch (condition.comparison()) {
            case LIKE -> buildLikePredicateToCriteria(condition, root, criteriaBuilder);
            case EQUALS -> buildEqualsPredicateToCriteria(condition, root, criteriaBuilder);
            case GREATER_THAN_OR_EQUAL -> buildGreaterThanOrEqualPredicateToCriteria(condition, root, criteriaBuilder);
            case LESS_THAN_OR_EQUAL -> buildLessThanPredicateToCriteria(condition, root, criteriaBuilder);
            case NOT_EQUALS -> buildNotEqualsPredicateToCriteria(condition, root, criteriaBuilder);
        };
    }

    private Predicate buildLikePredicateToCriteria(Condition condition, Root<T> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get(condition.field()), "%" + condition.value() + "%");
    }

    private Predicate buildEqualsPredicateToCriteria(Condition condition, Root<T> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(condition.field()), condition.value());
    }

    private Predicate buildGreaterThanOrEqualPredicateToCriteria(Condition condition, Root<T> root, CriteriaBuilder criteriaBuilder) {
        Path<Comparable> yPath = root.get(condition.field());
        Comparable value = (Comparable) condition.value();
        return criteriaBuilder.greaterThanOrEqualTo(yPath, value);
    }

    private Predicate buildLessThanPredicateToCriteria(Condition condition, Root<T> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get(condition.field()),
                "%" + condition.value() + "%");
    }

    private Predicate buildNotEqualsPredicateToCriteria(Condition condition, Root<T> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.notEqual(root.get(condition.field()), condition.value());
    }


}
