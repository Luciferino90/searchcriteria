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

	public static Date toDate(Object value){
		return Date.from(toLocalDate(value).atZone( ZoneId.systemDefault()).toInstant());
	}

	public static LocalDateTime toLocalDate(Object value){
		return LocalDateTime.parse((String) value);
	}

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

	public static Map<String, Class<?>> getSearchFieldWithType(Class<?> cls){
		return Stream.concat(
				cls.getSuperclass() == null ? Stream.empty() : getSearchFieldWithType(cls.getSuperclass()).entrySet().stream(),
				Arrays.stream(cls.getDeclaredFields()).collect(Collectors.toMap(Field::getName, Field::getType, (oldValue, newValue) -> newValue)).entrySet().stream()
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> newValue));
	}

}
