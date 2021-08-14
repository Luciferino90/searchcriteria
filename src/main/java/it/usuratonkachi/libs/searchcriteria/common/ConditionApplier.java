package it.usuratonkachi.libs.searchcriteria.common;

import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.function.BiFunction;

@NoArgsConstructor
public class ConditionApplier<T extends Comparable<? super T>> {

    public static String toLike(String value){
        value = value.replaceAll("_", "%");
        return "%" + value + "%";
    }

    public final TriFunction<CriteriaBuilder, Expression<T>, Object, Predicate> isEqual = CriteriaBuilder::equal;
    public final TriFunction<CriteriaBuilder, Expression<T>, Object, Predicate> isNotEqual = CriteriaBuilder::notEqual;
    public final BiFunction<CriteriaBuilder, Expression<T>, Predicate> isNull = CriteriaBuilder::isNull;
    public final BiFunction<CriteriaBuilder, Expression<T>, Predicate> isNotNull = CriteriaBuilder::isNotNull;
    public final TriFunction<CriteriaBuilder, Expression<T>, List<?>, Predicate> isIn = (criteriaBuilder, expression, values) -> criteriaBuilder.in(expression).in(values);
    public final TriFunction<CriteriaBuilder, Expression<String>, Object, Predicate> isLike =
            (criteriaBuilder, expression, converted) -> criteriaBuilder.like(expression, toLike((String)converted));

    public final TriFunction<CriteriaBuilder, Expression<? extends T>, T, Predicate> isGreaterThan = CriteriaBuilder::greaterThan;
    public final TriFunction<CriteriaBuilder, Expression<? extends T>, T, Predicate> isGreaterThanEqual = CriteriaBuilder::greaterThanOrEqualTo;
    public final TriFunction<CriteriaBuilder, Expression<? extends T>, T, Predicate> isLessThan = CriteriaBuilder::lessThan;
    public final TriFunction<CriteriaBuilder, Expression<? extends T>, T, Predicate> isLessThanEqual = CriteriaBuilder::lessThanOrEqualTo;
    public final QuadFunction<CriteriaBuilder, Expression<? extends T>, T, T, Predicate> isBetween = CriteriaBuilder::between;

}
