package it.usuratonkachi.searchcriteria.common;

import org.apache.commons.lang3.BooleanUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class TypeHandlerConverters {

	/**
	 * Ogni stringa che sia parsabile come data viene interpretata come tale. Hibernate pretende che sia tipata
	 * come Date. Se l'operazione fallisce utilizziamo la Stringa stessa.
	 * @param value
	 * @return
	 */
	public static Date toDate(Object value){
		return Date.from(toLocalDate(value).atZone( ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Ogni stringa che sia parsabile come data viene interpretata come tale. Hibernate pretende che sia tipata
	 * come LocalDateTime. Se l'operazione fallisce utilizziamo la Stringa stessa.
	 * @param value
	 * @return
	 */
	public static LocalDateTime toLocalDate(Object value){
		return LocalDateTime.parse((String) value);
	}

	/**
	 * Non siamo sicuri del formato della data che ci può arrivare, proviamo prima con il zone.. se non funziona
	 * proviamo con il local. Se neppure lui va non consideriamo il valore come una data ma come una stringa.
	 * @param value
	 * @return
	 */
	public static ZonedDateTime toZonedDateTime(Object value){
		try {
			return ZonedDateTime.parse((String)value);
		} catch(Exception ex){
			LocalDateTime localDateTime = LocalDateTime.parse((String)value);
			return ZonedDateTime.ofLocal(localDateTime, ZoneId.systemDefault(), ZoneOffset.UTC);
		}
	}

	public static Boolean toBoolean(Object value){
		try {
			return BooleanUtils.toBoolean(Integer.parseInt((String) value));
		} catch (Exception ex) {
			return BooleanUtils.toBoolean((String)value);
		}
	}

	/**
	 * Method to get map of FieldName, TypeName of entity from domain lib.
	 * This information are needed to cast query into the right type, as requested by spring.
	 * In case of field with same type (should never happen for limitation over database structure) we will use just the latter.
	 * @param cls
	 * @return
	 */
	public static Map<String, Class<?>> getSearchFieldWithType(Class<?> cls){
		return Stream.concat(
				cls.getSuperclass() == null ? Stream.empty() : getSearchFieldWithType(cls.getSuperclass()).entrySet().stream(),
				Arrays.stream(cls.getDeclaredFields()).collect(Collectors.toMap(Field::getName, Field::getType, (oldValue, newValue) -> newValue)).entrySet().stream()
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> newValue));
	}

}
