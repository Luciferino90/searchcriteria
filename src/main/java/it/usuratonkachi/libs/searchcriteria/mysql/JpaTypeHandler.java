package it.usuratonkachi.libs.searchcriteria.mysql;

import it.usuratonkachi.libs.searchcriteria.common.ConditionApplier;
import it.usuratonkachi.libs.searchcriteria.common.TypeHandlerConverters;
import it.usuratonkachi.libs.searchcriteria.criteria.SearchOperator;
import it.usuratonkachi.libs.searchcriteria.exception.SearchCriteriaException;
import org.hibernate.query.criteria.internal.path.SingularAttributePath;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@SuppressWarnings("unchecked")
public class JpaTypeHandler {

	public static final Map<Class<?>, GenericHandler> dispatch = new HashMap<>();

	private interface GenericHandler {
		Predicate handleType(Expression<?> expression, Object value, Object additionalValue, SearchOperator searchOperator, CriteriaBuilder criteriaBuilder);
	}

	public static String toLike(String value){
		value = value.replaceAll("_", "%");
		return "%" + value + "%";
	}

	public static Expression<?> getExpression(Root<?> root, String field) {
		Expression<?> expression;
		if (field.contains(".")) {
			String[] parts = field.split("\\.");
			String table = parts[0];
			String joinColumn = parts[1];
			expression = root.join(table).get(joinColumn);
		} else {
			expression = root.get(field);
		}
		return expression;
	}

	static {
		dispatch.put(LocalDateTime.class, (expression, converted, additionalValue, searchOperator, criteriaBuilder) -> {
			final ConditionApplier<LocalDateTime> conditionApplier = new ConditionApplier<>();
			String fieldName = ((SingularAttributePath<?>) expression).getAttribute().getName();
			LocalDateTime firstValue = convert(TypeHandlerConverters::toLocalDate, converted, LocalDateTime.class, fieldName);
			LocalDateTime secondValue = convert(TypeHandlerConverters::toLocalDate, additionalValue, LocalDateTime.class, fieldName);
			switch (searchOperator) {
				case LIKE:
					throw new UnsupportedOperationException();
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<LocalDateTime>) expression, firstValue);
				case GT:
					return conditionApplier.isGreaterThan.apply(criteriaBuilder, (Expression<LocalDateTime>) expression, firstValue);
				case GTE:
					return conditionApplier.isGreaterThanEqual.apply(criteriaBuilder, (Expression<? extends LocalDateTime>) expression, firstValue);
				case LT:
					return conditionApplier.isLessThan.apply(criteriaBuilder, (Expression<? extends LocalDateTime>) expression, firstValue);
				case LTE:
					return conditionApplier.isLessThanEqual.apply(criteriaBuilder, (Expression<? extends LocalDateTime>) expression, firstValue);
				case BETWEEN:
					return conditionApplier.isBetween.apply(criteriaBuilder, (Expression<? extends LocalDateTime>) expression, firstValue, secondValue);
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<LocalDateTime>) expression, firstValue);
				case NULL:
					return conditionApplier.isNull.apply(criteriaBuilder, (Expression<LocalDateTime>) expression);
				case NOTNULL:
					return conditionApplier.isNotNull.apply(criteriaBuilder, (Expression<LocalDateTime>) expression);
				case IN:
				default:
					throw new UnsupportedOperationException();
			}
		});

		dispatch.put(ZonedDateTime.class, (expression, converted, additionalValue, searchOperator, criteriaBuilder) -> {
			final ConditionApplier<ZonedDateTime> conditionApplier = new ConditionApplier<>();
			String fieldName = ((SingularAttributePath<?>) expression).getAttribute().getName();
			ZonedDateTime firstValue = convert(TypeHandlerConverters::toZonedDateTime, converted, ZonedDateTime.class, fieldName);
			ZonedDateTime secondValue = convert(TypeHandlerConverters::toZonedDateTime, additionalValue, ZonedDateTime.class, fieldName);
			switch (searchOperator) {
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<ZonedDateTime>) expression, firstValue);
				case GT:
					return conditionApplier.isGreaterThan.apply(criteriaBuilder, (Expression<? extends ZonedDateTime>) expression, firstValue);
				case GTE:
					return conditionApplier.isGreaterThanEqual.apply(criteriaBuilder, (Expression<? extends ZonedDateTime>) expression, firstValue);
				case LT:
					return conditionApplier.isLessThan.apply(criteriaBuilder, (Expression<? extends ZonedDateTime>) expression, firstValue);
				case LTE:
					return conditionApplier.isLessThanEqual.apply(criteriaBuilder, (Expression<? extends ZonedDateTime>) expression, firstValue);
				case BETWEEN:
					return conditionApplier.isBetween.apply(criteriaBuilder, (Expression<? extends ZonedDateTime>) expression, firstValue, secondValue);
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<ZonedDateTime>) expression, firstValue);
				case NULL:
					return conditionApplier.isNull.apply(criteriaBuilder, (Expression<ZonedDateTime>) expression);
				case NOTNULL:
					return conditionApplier.isNotNull.apply(criteriaBuilder, (Expression<ZonedDateTime>) expression);
				case IN:
				case LIKE:
				default:
					throw new UnsupportedOperationException();
			}
		});

		dispatch.put(Date.class, (expression, converted, additionalValue, searchOperator, criteriaBuilder) -> {
			final ConditionApplier<Date> conditionApplier = new ConditionApplier<>();
			String fieldName = ((SingularAttributePath<?>) expression).getAttribute().getName();
			Date firstValue = convert(TypeHandlerConverters::toDate, converted, Date.class, fieldName);
			Date secondValue = convert(TypeHandlerConverters::toDate, additionalValue, Date.class, fieldName);
			switch (searchOperator) {
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<Date>) expression, firstValue);
				case GT:
					return conditionApplier.isGreaterThan.apply(criteriaBuilder, (Expression<? extends Date>) expression, firstValue);
				case GTE:
					return conditionApplier.isGreaterThanEqual.apply(criteriaBuilder, (Expression<? extends Date>) expression, firstValue);
				case LT:
					return conditionApplier.isLessThan.apply(criteriaBuilder, (Expression<? extends Date>) expression, firstValue);
				case LTE:
					return conditionApplier.isLessThanEqual.apply(criteriaBuilder, (Expression<? extends Date>) expression, firstValue);
				case BETWEEN:
					return conditionApplier.isBetween.apply(criteriaBuilder, (Expression<? extends Date>) expression, firstValue, secondValue);
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<Date>) expression, firstValue);
				case NULL:
					return conditionApplier.isNull.apply(criteriaBuilder, (Expression<Date>) expression);
				case NOTNULL:
					return conditionApplier.isNotNull.apply(criteriaBuilder, (Expression<Date>) expression);
				case IN:
				case LIKE:
				default:
					throw new UnsupportedOperationException();
			}
		});

		dispatch.put(String.class, (expression, converted, additionalValue, searchOperator, criteriaBuilder) -> {
			final ConditionApplier<String> conditionApplier = new ConditionApplier<>();
			String fieldName = ((SingularAttributePath<?>) expression).getAttribute().getName();
			String firstValue = null;
			String secondValue = null;
			if (!searchOperator.equals(SearchOperator.IN)) {
				firstValue = convert(Function.identity(), converted, String.class, firstValue);
				secondValue = convert(Function.identity(), additionalValue, String.class, firstValue);
			}
			switch (searchOperator) {
				case LIKE:
					return conditionApplier.isLike.apply(criteriaBuilder, (Expression<String>) expression, firstValue);
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<String>) expression, firstValue);
				case GT:
					return conditionApplier.isGreaterThan.apply(criteriaBuilder, (Expression<? extends String>) expression, firstValue);
				case GTE:
					return conditionApplier.isGreaterThanEqual.apply(criteriaBuilder, (Expression<? extends String>) expression, firstValue);
				case LT:
					return conditionApplier.isLessThan.apply(criteriaBuilder, (Expression<? extends String>) expression, firstValue);
				case LTE:
					return conditionApplier.isLessThanEqual.apply(criteriaBuilder, (Expression<? extends String>) expression, firstValue);
				case BETWEEN:
					return conditionApplier.isBetween.apply(criteriaBuilder, (Expression<? extends String>) expression, firstValue, secondValue);
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<String>) expression, firstValue);
				case NULL:
					return conditionApplier.isNull.apply(criteriaBuilder, (Expression<String>) expression);
				case NOTNULL:
					return conditionApplier.isNotNull.apply(criteriaBuilder, (Expression<String>) expression);
				case IN:
					return conditionApplier.isIn.apply(criteriaBuilder, (Expression<String>) expression, ((List<Object>)converted).stream().map(s -> convert(Function.identity(), s, String.class, fieldName)).collect(Collectors.toList()));
				default:
					throw new UnsupportedOperationException();
			}
		});

		dispatch.put(Integer.class, (expression, converted, additionalValue, searchOperator, criteriaBuilder) -> {
			final ConditionApplier<Integer> conditionApplier = new ConditionApplier<>();
			String fieldName = ((SingularAttributePath<?>) expression).getAttribute().getName();
			Integer firstValue = convert(Integer::parseInt, converted, Integer.class, fieldName);
			Integer secondValue = convert(Integer::parseInt, additionalValue, Integer.class, fieldName);
			switch (searchOperator) {
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<Integer>) expression, firstValue);
				case GT:
					return conditionApplier.isGreaterThan.apply(criteriaBuilder, (Expression<? extends Integer>) expression, firstValue);
				case GTE:
					return conditionApplier.isGreaterThanEqual.apply(criteriaBuilder, (Expression<? extends Integer>) expression, firstValue);
				case LT:
					return conditionApplier.isLessThan.apply(criteriaBuilder, (Expression<? extends Integer>) expression, firstValue);
				case LTE:
					return conditionApplier.isLessThanEqual.apply(criteriaBuilder, (Expression<? extends Integer>) expression, firstValue);
				case BETWEEN:
					return conditionApplier.isBetween.apply(criteriaBuilder, (Expression<? extends Integer>) expression, firstValue, secondValue);
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<Integer>) expression, firstValue);
				case NULL:
					return conditionApplier.isNull.apply(criteriaBuilder, (Expression<Integer>) expression);
				case NOTNULL:
					return conditionApplier.isNotNull.apply(criteriaBuilder, (Expression<Integer>) expression);
				case IN:
				case LIKE:
				default:
					throw new UnsupportedOperationException();
			}
		});

		dispatch.put(Long.class, (expression, converted, additionalValue, searchOperator, criteriaBuilder) -> {
			final ConditionApplier<Long> conditionApplier = new ConditionApplier<>();
			String fieldName = ((SingularAttributePath<?>) expression).getAttribute().getName();
			Long firstValue = convert(TypeHandlerConverters::toLong, converted, Long.class, fieldName);
			Long secondValue = convert(TypeHandlerConverters::toLong, additionalValue, Long.class, fieldName);
			switch (searchOperator) {
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<Long>) expression, firstValue);
				case GT:
					return conditionApplier.isGreaterThan.apply(criteriaBuilder, (Expression<? extends Long>) expression, firstValue);
				case GTE:
					return conditionApplier.isGreaterThanEqual.apply(criteriaBuilder, (Expression<? extends Long>) expression, firstValue);
				case LT:
					return conditionApplier.isLessThan.apply(criteriaBuilder, (Expression<? extends Long>) expression, firstValue);
				case LTE:
					return conditionApplier.isLessThanEqual.apply(criteriaBuilder, (Expression<? extends Long>) expression, firstValue);
				case BETWEEN:
					return conditionApplier.isBetween.apply(criteriaBuilder, (Expression<? extends Long>) expression, firstValue, secondValue);
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<Long>) expression, firstValue);
				case NULL:
					return conditionApplier.isNull.apply(criteriaBuilder, (Expression<Long>) expression);
				case NOTNULL:
					return conditionApplier.isNotNull.apply(criteriaBuilder, (Expression<Long>) expression);
				case IN:
				case LIKE:
				default:
					throw new UnsupportedOperationException();
			}
		});

		dispatch.put(Object.class, (expression, converted, additionalValue, searchOperator, criteriaBuilder) -> {
			// if (true) return dispatch.get(String.class).handleType(expression, converted, additionalValue, searchOperator, criteriaBuilder);
			// final ConditionApplier<? extends Comparable<Object>> conditionApplier = new ConditionApplier<>();
			switch (searchOperator) {
				case EQUAL:
					// return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<? extends Comparable<Object>>) expression, converted);
				case NOTEQUAL:
					// return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<? extends Comparable<Object>>) expression, converted);
				case NULL:
					// return conditionApplier.isNull.apply(criteriaBuilder, (Expression<? extends Comparable<Object>>) expression);
				case NOTNULL:
					// return conditionApplier.isNotNull.apply(criteriaBuilder, (Expression<? extends Comparable<Object>>) expression);
				case IN:
				case LIKE:
				case GT:
				case GTE:
				case LT:
				case LTE:
				case BETWEEN:
				default:
					throw new UnsupportedOperationException();
			}
		});

		dispatch.put(Boolean.class, (expression, converted, additionalValue, searchOperator, criteriaBuilder) -> {
			final ConditionApplier<Boolean> conditionApplier = new ConditionApplier<>();
			String fieldName = ((SingularAttributePath<?>) expression).getAttribute().getName();
			String firstValue = null;
			String secondValue = null;
			if (!searchOperator.equals(SearchOperator.IN)) {
				firstValue = convert(Function.identity(), converted, String.class, fieldName);
				secondValue = convert(Function.identity(), additionalValue, String.class, fieldName);
			}
			switch (searchOperator) {
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<Boolean>) expression, firstValue);
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<Boolean>) expression, firstValue);
				case NULL:
					return conditionApplier.isNull.apply(criteriaBuilder, (Expression<Boolean>) expression);
				case NOTNULL:
					return conditionApplier.isNotNull.apply(criteriaBuilder, (Expression<Boolean>) expression);
				case IN:
					return conditionApplier.isIn.apply(criteriaBuilder, (Expression<Boolean>) expression, ((List<Object>)converted).stream().map(s -> convert(Function.identity(), s, String.class, fieldName)).collect(Collectors.toList()));
				case LIKE:
				case GT:
				case GTE:
				case LT:
				case LTE:
				case BETWEEN:
				default:
					throw new UnsupportedOperationException();
			}
		});
	}

	static Predicate generatePredicate(String field, Object value, Object additionalValue, SearchOperator searchOperator,
									   CriteriaBuilder criteriaBuilder, Root<?> root, Class<?> typeVariable)
	{
		try {
			typeVariable = dispatch.containsKey(typeVariable) ? typeVariable : Object.class;
			Expression<?> expression = getExpression(root, field);
			return dispatch.get(typeVariable).handleType(expression, value, additionalValue, searchOperator, criteriaBuilder);
		} catch (UnsupportedOperationException unex) {
			throw new SearchCriteriaException("Query not valid: " + unex.getMessage(), unex);
			//return criteriaBuilder.and(); Risky
		} catch (Exception ex) {
			throw new SearchCriteriaException("Query not runnable: " + ex.getMessage(), ex);
		}

	}

	static <R> R convert(Function<String, R> converterFunction, Object value, Class<R> clazz, String fieldName) {
		return Optional.ofNullable(value)
				.map(val -> {
					if (value.getClass().equals(String.class)) {
						return converterFunction.apply((String) value);
					} else if (value.getClass().equals(clazz)) {
						return clazz.cast(value);
					} else {
						throw new SearchCriteriaException("Unsupported type for field " + fieldName);
					}
				})
				.orElse(null);
	}

}
