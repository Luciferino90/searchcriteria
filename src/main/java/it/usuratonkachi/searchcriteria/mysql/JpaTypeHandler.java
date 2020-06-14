package it.usuratonkachi.searchcriteria.mysql;

import it.usuratonkachi.searchcriteria.common.SearchOperator;
import it.usuratonkachi.searchcriteria.exception.SearchCriteriaException;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.usuratonkachi.searchcriteria.common.TypeHandlerConverters.*;


@SuppressWarnings("unchecked")
class JpaTypeHandler {

	private static final Map<Class, GenericHandler> dispatch = new HashMap<>();

	private interface GenericHandler {
		Predicate handleType(String field, Object value, Object additionalValue, SearchOperator searchOperator,
				CriteriaBuilder criteriaBuilder, Root root);
	}

	public static String toLike(String value){
		value = value.replaceAll("_", "%");
		return "%" + value + "%";
	}

	static {
		dispatch.put(LocalDateTime.class, (field, value, additionalValue, searchOperator, criteriaBuilder, root) -> {
			switch (searchOperator) {
				case LIKE:
					throw new UnsupportedOperationException();
				case EQUAL:
					return criteriaBuilder.equal(root.get(field), value == null ? null : toLocalDate(value));
				case GT:
					return criteriaBuilder.greaterThan(root.get(field), value == null ? null : toLocalDate(value));
				case GTE:
					return criteriaBuilder.greaterThanOrEqualTo(root.get(field), value == null ? null : toLocalDate(value));
				case LT:
					return criteriaBuilder.lessThan(root.get(field), value == null ? null : toLocalDate(value));
				case LTE:
					return criteriaBuilder.lessThanOrEqualTo(root.get(field), value == null ? null : toLocalDate(value));
				case BETWEEN:
					return criteriaBuilder.between(root.get(field), value == null ? null : toLocalDate(value), toLocalDate(additionalValue));
				case NOTEQUAL:
					return criteriaBuilder.notEqual(root.get(field), value == null ? null : toLocalDate(value));
				case NULL:
					return criteriaBuilder.isNull(root.get(field));
				case NOTNULL:
					return criteriaBuilder.isNotNull(root.get(field));
				case IN:
				default:
					throw new UnsupportedOperationException();
				}
		});

		dispatch.put(ZonedDateTime.class, (field, value, additionalValue, searchOperator, criteriaBuilder, root) -> {
			switch (searchOperator) {
				case EQUAL:
					return criteriaBuilder.equal(root.get(field), toZonedDateTime(value));
				case GT:
					return criteriaBuilder.greaterThan(root.get(field), toZonedDateTime(value));
				case GTE:
					return criteriaBuilder.greaterThanOrEqualTo(root.get(field), toZonedDateTime(value));
				case LT:
					return criteriaBuilder.lessThan(root.get(field), toZonedDateTime(value));
				case LTE:
					return criteriaBuilder.lessThanOrEqualTo(root.get(field), toZonedDateTime(value));
				case BETWEEN:
					return criteriaBuilder.between(root.get(field), toZonedDateTime(value), StringUtils.isEmpty((String)additionalValue) ? null : toZonedDateTime(additionalValue));
				case NOTEQUAL:
					return criteriaBuilder.notEqual(root.get(field), toZonedDateTime(value));
				case NULL:
					return criteriaBuilder.isNull(root.get(field));
				case NOTNULL:
					return criteriaBuilder.isNotNull(root.get(field));
				case IN:
				case LIKE:
				default:
					throw new UnsupportedOperationException();
			}
		});

		dispatch.put(Date.class, (field, value, additionalValue, searchOperator, criteriaBuilder, root) -> {
			switch (searchOperator) {
				case EQUAL:
					return criteriaBuilder.equal(root.get(field), toDate(value));
				case GT:
					return criteriaBuilder.greaterThan(root.get(field), toDate(value));
				case GTE:
					return criteriaBuilder.greaterThanOrEqualTo(root.get(field), toDate(value));
				case LT:
					return criteriaBuilder.lessThan(root.get(field), toDate(value));
				case LTE:
					return criteriaBuilder.lessThanOrEqualTo(root.get(field), toDate(value));
				case BETWEEN:
					return criteriaBuilder.between(root.get(field), toDate(value), StringUtils.isEmpty(additionalValue) ? null : toDate(additionalValue));
				case NOTEQUAL:
					return criteriaBuilder.notEqual(root.get(field), toDate(value));
				case NULL:
					return criteriaBuilder.isNull(root.get(field));
				case NOTNULL:
					return criteriaBuilder.isNotNull(root.get(field));
				case IN:
				case LIKE:
				default:
					throw new UnsupportedOperationException();
			}
		});

		dispatch.put(String.class, (field, value, additionalValue, searchOperator, criteriaBuilder, root) -> {
			switch (searchOperator) {
				case LIKE:
					return criteriaBuilder.like(root.get(field), toLike((String) value));
				case EQUAL:
					return criteriaBuilder.equal(root.get(field), value);
				case GT:
					return criteriaBuilder.greaterThan(root.get(field), (String) value);
				case GTE:
					return criteriaBuilder.greaterThanOrEqualTo(root.get(field), (String) value);
				case LT:
					return criteriaBuilder.lessThan(root.get(field), (String) value);
				case LTE:
					return criteriaBuilder.lessThanOrEqualTo(root.get(field), (String) value);
				case BETWEEN:
					return criteriaBuilder.between(root.get(field), (String) value, (String) additionalValue);
				case NOTEQUAL:
					return criteriaBuilder.notEqual(root.get(field), value);
				case NULL:
					return criteriaBuilder.isNull(root.get(field));
				case NOTNULL:
					return criteriaBuilder.isNotNull(root.get(field));
				case IN:
					return root.get(field).in((List)value);
				default:
					throw new UnsupportedOperationException();
			}
		});

		dispatch.put(Integer.class, (field, value, additionalValue, searchOperator, criteriaBuilder, root) -> {
			switch (searchOperator) {
				case EQUAL:
					return criteriaBuilder.equal(root.get(field), Integer.parseInt((String)value));
				case GT:
					return criteriaBuilder.greaterThan(root.get(field), Integer.parseInt((String)value));
				case GTE:
					return criteriaBuilder.greaterThanOrEqualTo(root.get(field), Integer.parseInt((String)value));
				case LT:
					return criteriaBuilder.lessThan(root.get(field), Integer.parseInt((String)value));
				case LTE:
					return criteriaBuilder.lessThanOrEqualTo(root.get(field), Integer.parseInt((String)value));
				case BETWEEN:
					return criteriaBuilder.between(root.get(field), Integer.parseInt((String)value), additionalValue == null ? null : Integer.parseInt((String)additionalValue));
				case NOTEQUAL:
					return criteriaBuilder.notEqual(root.get(field), Integer.parseInt((String)value));
				case NULL:
					return criteriaBuilder.isNull(root.get(field));
				case NOTNULL:
					return criteriaBuilder.isNotNull(root.get(field));
				case IN:
				case LIKE:
				default:
					throw new UnsupportedOperationException();
			}
		});

		dispatch.put(Long.class, (field, value, additionalValue, searchOperator, criteriaBuilder, root) -> {
			switch (searchOperator) {
				case EQUAL:
					return criteriaBuilder.equal(root.get(field), Long.parseLong((String)value));
				case GT:
					return criteriaBuilder.greaterThan(root.get(field), Long.parseLong((String)value));
				case GTE:
					return criteriaBuilder.greaterThanOrEqualTo(root.get(field), Long.parseLong((String)value));
				case LT:
					return criteriaBuilder.lessThan(root.get(field), Long.parseLong((String)value));
				case LTE:
					return criteriaBuilder.lessThanOrEqualTo(root.get(field), Long.parseLong((String)value));
				case BETWEEN:
					return criteriaBuilder.between(root.get(field), Long.parseLong((String)value), additionalValue == null ? null : Long.parseLong((String)additionalValue));
				case NOTEQUAL:
					return criteriaBuilder.notEqual(root.get(field), Long.parseLong((String)value));
				case NULL:
					return criteriaBuilder.isNull(root.get(field));
				case NOTNULL:
					return criteriaBuilder.isNotNull(root.get(field));
				case IN:
				case LIKE:
				default:
					throw new UnsupportedOperationException();
			}
		});

		dispatch.put(Object.class, (field, converted, additionalValue, searchOperator, criteriaBuilder, root) -> {
			switch (searchOperator) {
				case EQUAL:
					return criteriaBuilder.equal(root.get(field), converted);
				case NOTEQUAL:
					return criteriaBuilder.notEqual(root.get(field), converted);
				case NULL:
					return criteriaBuilder.isNull(root.get(field));
				case NOTNULL:
					return criteriaBuilder.isNotNull(root.get(field));
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

		dispatch.put(Boolean.class, (field, converted, additionalValue, searchOperator, criteriaBuilder, root) -> {
			switch (searchOperator) {
				case EQUAL:
					return criteriaBuilder.equal(root.get(field), toBoolean(converted));
				case NOTEQUAL:
					return criteriaBuilder.notEqual(root.get(field), toBoolean(converted));
				case NULL:
					return criteriaBuilder.isNull(root.get(field));
				case NOTNULL:
					return criteriaBuilder.isNotNull(root.get(field));
				case IN:
					return criteriaBuilder.in(root.get(field));
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
			CriteriaBuilder criteriaBuilder, Root root, Class typeVariable)
	{
		try {
			typeVariable = dispatch.containsKey(typeVariable) ? typeVariable : Object.class;
			return dispatch.get(typeVariable).handleType(field, value, additionalValue, searchOperator, criteriaBuilder, root);
		} catch (UnsupportedOperationException unex) {
			throw new SearchCriteriaException("Query not valid: " + unex.getMessage(), unex);
		} catch (Exception ex) {
			throw new SearchCriteriaException("Query not runnable: " + ex.getMessage(), ex);
		}

	}

}
