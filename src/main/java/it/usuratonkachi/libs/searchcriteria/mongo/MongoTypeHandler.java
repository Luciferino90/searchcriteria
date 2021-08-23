package it.usuratonkachi.libs.searchcriteria.mongo;

import it.usuratonkachi.libs.searchcriteria.criteria.SearchOperator;
import it.usuratonkachi.libs.searchcriteria.exception.SearchCriteriaException;
import it.usuratonkachi.libs.searchcriteria.common.TypeHandlerConverters;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MongoTypeHandler {

	public static final Map<Class<?>, GenericHandler> dispatch = new HashMap<>();

	private interface GenericHandler {
		Criteria handleType(String field, Object value, Object additionalValue, SearchOperator searchOperator);
	}

	private static String toLike(String value){
		return "/" + value + "/";
	}

	static {
		dispatch.put(Date.class, (field, value, additionalValue, searchOperator) -> {
			Date converted = TypeHandlerConverters.toDate(value);
			Date convertedAdditional = null;
			if (SearchOperator.BETWEEN.equals(searchOperator))
				convertedAdditional = TypeHandlerConverters.toDate(additionalValue);
			return searchCriteriaToCriteria(field, converted, convertedAdditional, searchOperator);
		});

		dispatch.put(LocalDateTime.class, (field, value, additionalValue, searchOperator) -> {
			LocalDateTime converted = TypeHandlerConverters.toLocalDate(value);
			LocalDateTime convertedAdditional = null;
			if (SearchOperator.BETWEEN.equals(searchOperator))
				convertedAdditional = TypeHandlerConverters.toLocalDate(additionalValue);
			return searchCriteriaToCriteria(field, converted, convertedAdditional, searchOperator);
		});

		dispatch.put(ZonedDateTime.class, (field, value, additionalValue, searchOperator) -> {
			ZonedDateTime converted = TypeHandlerConverters.toZonedDateTime(value);
			ZonedDateTime convertedAdditional = null;
			if (SearchOperator.BETWEEN.equals(searchOperator))
				convertedAdditional = TypeHandlerConverters.toZonedDateTime(additionalValue);
			return searchCriteriaToCriteria(field, converted, convertedAdditional, searchOperator);
		});

		dispatch.put(String.class, (field, value, additionalValue, searchOperator) -> {
			if (SearchOperator.IN.equals(searchOperator)){
				return searchCriteriaToCriteria(field, value, null, searchOperator);
			} else {
				return searchCriteriaToCriteria(field, value, additionalValue, searchOperator);
			}
		});

		dispatch.put(Integer.class, (field, value, additionalValue, searchOperator) -> {
			Integer converted = Integer.parseInt((String)value);
			Integer convertedAdditional = additionalValue == null ? null : Integer.parseInt((String)additionalValue);
			return searchCriteriaToCriteria(field, converted, convertedAdditional, searchOperator);
		});

		dispatch.put(Long.class, (field, value, additionalValue, searchOperator) -> {
			Long converted = Long.parseLong((String)value);
			Long convertedAdditional = additionalValue == null ? null : Long.parseLong((String)additionalValue);
			return searchCriteriaToCriteria(field, converted, convertedAdditional, searchOperator);
		});

		dispatch.put(Boolean.class, (field, value, additionalValue, searchOperator) ->
				searchCriteriaToCriteria(field, TypeHandlerConverters.toBoolean(value), additionalValue, searchOperator));

		dispatch.put(Object.class, MongoTypeHandler::searchCriteriaToCriteria);
	}

	static Criteria generateCriteria(String field, Object value, Object additionalValue, SearchOperator searchOperator, Class<?> typeVariable)
	{
		try {
			typeVariable = dispatch.containsKey(typeVariable) ? typeVariable : Object.class;
			return dispatch.get(typeVariable).handleType(field, value, additionalValue, searchOperator);
		} catch (UnsupportedOperationException unex) {
			throw new SearchCriteriaException("Query not valid: " + unex.getMessage(), unex);
		} catch (Exception ex) {
			throw new SearchCriteriaException("Query not runnable: " + ex.getMessage(), ex);
		}

	}

	private static Criteria searchCriteriaToCriteria(String field, Object value, Object valueAdditional, SearchOperator searchOperator)
	{
		switch (searchOperator) {
			case LIKE:
				return Criteria.where(field).regex((String) value);
			case EQUAL:
				return Criteria.where(field).is(value);
			case GT:
				return Criteria.where(field).gt(value);
			case GTE:
				return Criteria.where(field).gte(value);
			case LT:
				return Criteria.where(field).lt(value);
			case LTE:
				return Criteria.where(field).lte(value);
			case BETWEEN:
				return Criteria.where(field).gte(value).lte(valueAdditional);
			case NOTEQUAL:
				return Criteria.where(field).ne(value);
			case NULL:
				return Criteria.where(field).is(null);
			case NOTNULL:
				return Criteria.where(field).ne(null);
			case IN:
				return Criteria.where(field).in((List<?>)value);
			default:
				throw new UnsupportedOperationException();
		}
	}


}
