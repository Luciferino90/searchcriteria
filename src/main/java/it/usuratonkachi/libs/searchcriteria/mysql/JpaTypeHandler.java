package it.usuratonkachi.libs.searchcriteria.mysql;

import it.usuratonkachi.libs.searchcriteria.common.ConditionApplier;
import it.usuratonkachi.libs.searchcriteria.criteria.SearchOperator;
import it.usuratonkachi.libs.searchcriteria.exception.SearchCriteriaException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.usuratonkachi.libs.searchcriteria.common.TypeHandlerConverters.*;

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

	private final static ConditionApplier<String> stringConditionApplier = new ConditionApplier<>();
	private final static ConditionApplier<Long> longConditionApplier = new ConditionApplier<>();
	private final static ConditionApplier<Integer> integerConditionApplier = new ConditionApplier<>();
	private final static ConditionApplier<Date> dateConditionApplier = new ConditionApplier<>();
	private final static ConditionApplier<LocalDateTime> localDateTimeConditionApplier = new ConditionApplier<>();
	private final static ConditionApplier<ZonedDateTime> zonedDateTimeConditionApplier = new ConditionApplier<>();
	private final static ConditionApplier<Boolean> booleanConditionApplier = new ConditionApplier<>();

	static {
		dispatch.put(LocalDateTime.class, (expression, value, additionalValue, searchOperator, criteriaBuilder) -> {
			final ConditionApplier<LocalDateTime> conditionApplier = new ConditionApplier<>();
			switch (searchOperator) {
				case LIKE:
					throw new UnsupportedOperationException();
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<LocalDateTime>) expression, toLocalDate(value));
				case GT:
					return conditionApplier.isGreaterThan.apply(criteriaBuilder, (Expression<LocalDateTime>) expression, toLocalDate(value));
				case GTE:
					return conditionApplier.isGreaterThanEqual.apply(criteriaBuilder, (Expression<? extends LocalDateTime>) expression, toLocalDate(value));
				case LT:
					return conditionApplier.isLessThan.apply(criteriaBuilder, (Expression<? extends LocalDateTime>) expression, toLocalDate(value));
				case LTE:
					return conditionApplier.isLessThanEqual.apply(criteriaBuilder, (Expression<? extends LocalDateTime>) expression, toLocalDate(value));
				case BETWEEN:
					return conditionApplier.isBetween.apply(criteriaBuilder, (Expression<? extends LocalDateTime>) expression, toLocalDate(value), toLocalDate(additionalValue));
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<LocalDateTime>) expression, toLocalDate(value));
				case NULL:
					return conditionApplier.isNull.apply(criteriaBuilder, (Expression<LocalDateTime>) expression);
				case NOTNULL:
					return conditionApplier.isNotNull.apply(criteriaBuilder, (Expression<LocalDateTime>) expression);
				case IN:
				default:
					throw new UnsupportedOperationException();
				}
		});

		dispatch.put(ZonedDateTime.class, (expression, value, additionalValue, searchOperator, criteriaBuilder) -> {
			final ConditionApplier<ZonedDateTime> conditionApplier = new ConditionApplier<>();
			switch (searchOperator) {
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<ZonedDateTime>) expression, toZonedDateTime(value));
				case GT:
					return conditionApplier.isGreaterThan.apply(criteriaBuilder, (Expression<? extends ZonedDateTime>) expression, toZonedDateTime(value));
				case GTE:
					return conditionApplier.isGreaterThanEqual.apply(criteriaBuilder, (Expression<? extends ZonedDateTime>) expression, toZonedDateTime(value));
				case LT:
					return conditionApplier.isLessThan.apply(criteriaBuilder, (Expression<? extends ZonedDateTime>) expression, toZonedDateTime(value));
				case LTE:
					return conditionApplier.isLessThanEqual.apply(criteriaBuilder, (Expression<? extends ZonedDateTime>) expression, toZonedDateTime(value));
				case BETWEEN:
					return conditionApplier.isBetween.apply(criteriaBuilder, (Expression<? extends ZonedDateTime>) expression, toZonedDateTime(value), toZonedDateTime(additionalValue));
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<ZonedDateTime>) expression, toZonedDateTime(value));
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

		dispatch.put(Date.class, (expression, value, additionalValue, searchOperator, criteriaBuilder) -> {
			final ConditionApplier<Date> conditionApplier = new ConditionApplier<>();
			switch (searchOperator) {
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<Date>) expression, toZonedDateTime(value));
				case GT:
					return conditionApplier.isGreaterThan.apply(criteriaBuilder, (Expression<? extends Date>) expression, toDate(value));
				case GTE:
					return conditionApplier.isGreaterThanEqual.apply(criteriaBuilder, (Expression<? extends Date>) expression, toDate(value));
				case LT:
					return conditionApplier.isLessThan.apply(criteriaBuilder, (Expression<? extends Date>) expression, toDate(value));
				case LTE:
					return conditionApplier.isLessThanEqual.apply(criteriaBuilder, (Expression<? extends Date>) expression, toDate(value));
				case BETWEEN:
					return conditionApplier.isBetween.apply(criteriaBuilder, (Expression<? extends Date>) expression, toDate(value), toDate(additionalValue));
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<Date>) expression, toDate(value));
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

		dispatch.put(String.class, (expression, value, additionalValue, searchOperator, criteriaBuilder) -> {
			final ConditionApplier<String> conditionApplier = new ConditionApplier<>();
			switch (searchOperator) {
				case LIKE:
					return conditionApplier.isLike.apply(criteriaBuilder, (Expression<String>) expression, value);
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<String>) expression, value);
				case GT:
					return conditionApplier.isGreaterThan.apply(criteriaBuilder, (Expression<? extends String>) expression, (String)value);
				case GTE:
					return conditionApplier.isGreaterThanEqual.apply(criteriaBuilder, (Expression<? extends String>) expression, (String)value);
				case LT:
					return conditionApplier.isLessThan.apply(criteriaBuilder, (Expression<? extends String>) expression, (String)value);
				case LTE:
					return conditionApplier.isLessThanEqual.apply(criteriaBuilder, (Expression<? extends String>) expression, (String)value);
				case BETWEEN:
					return conditionApplier.isBetween.apply(criteriaBuilder, (Expression<? extends String>) expression, (String) value, (String) additionalValue);
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<String>) expression, value);
				case NULL:
					return conditionApplier.isNull.apply(criteriaBuilder, (Expression<String>) expression);
				case NOTNULL:
					return conditionApplier.isNotNull.apply(criteriaBuilder, (Expression<String>) expression);
				case IN:
					return conditionApplier.isIn.apply(criteriaBuilder, (Expression<String>) expression, (List<String>)value);
				default:
					throw new UnsupportedOperationException();
			}
		});

		dispatch.put(Integer.class, (expression, value, additionalValue, searchOperator, criteriaBuilder) -> {
			final ConditionApplier<Integer> conditionApplier = new ConditionApplier<>();
			switch (searchOperator) {
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<Integer>) expression, Integer.parseInt((String)value));
				case GT:
					return conditionApplier.isGreaterThan.apply(criteriaBuilder, (Expression<? extends Integer>) expression, Integer.parseInt((String)value));
				case GTE:
					return conditionApplier.isGreaterThanEqual.apply(criteriaBuilder, (Expression<? extends Integer>) expression, Integer.parseInt((String)value));
				case LT:
					return conditionApplier.isLessThan.apply(criteriaBuilder, (Expression<? extends Integer>) expression, Integer.parseInt((String)value));
				case LTE:
					return conditionApplier.isLessThanEqual.apply(criteriaBuilder, (Expression<? extends Integer>) expression, Integer.parseInt((String)value));
				case BETWEEN:
					return conditionApplier.isBetween.apply(criteriaBuilder, (Expression<? extends Integer>) expression, Integer.parseInt((String)value), additionalValue == null ? null : Integer.parseInt((String)additionalValue));
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<Integer>) expression, Long.parseLong((String)value));
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

		dispatch.put(Long.class, (expression, value, additionalValue, searchOperator, criteriaBuilder) -> {
			final ConditionApplier<Long> conditionApplier = new ConditionApplier<>();
			switch (searchOperator) {
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<Long>) expression, Long.parseLong((String)value));
				case GT:
					return conditionApplier.isGreaterThan.apply(criteriaBuilder, (Expression<? extends Long>) expression, Long.parseLong((String)value));
				case GTE:
					return conditionApplier.isGreaterThanEqual.apply(criteriaBuilder, (Expression<? extends Long>) expression, Long.parseLong((String)value));
				case LT:
					return conditionApplier.isLessThan.apply(criteriaBuilder, (Expression<? extends Long>) expression, Long.parseLong((String)value));
				case LTE:
					return conditionApplier.isLessThanEqual.apply(criteriaBuilder, (Expression<? extends Long>) expression, Long.parseLong((String)value));
				case BETWEEN:
					return conditionApplier.isBetween.apply(criteriaBuilder, (Expression<? extends Long>) expression, toLong(value), toLong(additionalValue));
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<Long>) expression, toLong(value));
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
			switch (searchOperator) {
				case EQUAL:
					return conditionApplier.isEqual.apply(criteriaBuilder, (Expression<Boolean>) expression, toBoolean(converted));
				case NOTEQUAL:
					return conditionApplier.isNotEqual.apply(criteriaBuilder, (Expression<Boolean>) expression, toBoolean(converted));
				case NULL:
					return conditionApplier.isNull.apply(criteriaBuilder, (Expression<Boolean>) expression);
				case NOTNULL:
					return conditionApplier.isNotNull.apply(criteriaBuilder, (Expression<Boolean>) expression);
				case IN:
					return conditionApplier.isIn.apply(criteriaBuilder, (Expression<Boolean>) expression, (List<Boolean>) converted);
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

}
