package it.usuratonkachi.libs.searchcriteria.common;

import it.usuratonkachi.libs.searchcriteria.mongo.MongoTypeHandler;
import it.usuratonkachi.libs.searchcriteria.mysql.JpaTypeHandler;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class TypeHandlerConverters {

	private static final List<Class<?>> managedType = Stream.concat(
			JpaTypeHandler.dispatch.keySet().stream(),
			MongoTypeHandler.dispatch.keySet().stream()
	).collect(Collectors.toList());

	public static Date toDate(Object value){
		return Date.from(toLocalDate(value).atZone( ZoneId.systemDefault()).toInstant());
	}

	public static LocalDateTime toLocalDate(Object value){
		return Optional.ofNullable(value).map(v -> LocalDateTime.parse((String) value)).orElse(null);
	}

	public static ZonedDateTime toZonedDateTime(Object value){
		return Optional.ofNullable(value).map(v -> {
			try {
				return ZonedDateTime.parse((String)v);
			} catch(Exception ex){
				LocalDateTime localDateTime = toLocalDate(v);
				return ZonedDateTime.ofLocal(localDateTime, ZoneId.systemDefault(), ZoneOffset.UTC);
			}
		}).orElse(null);
	}

	public static Boolean toBoolean(Object value){
		return Optional.ofNullable(value).map(v -> {
			try {
				return BooleanUtils.toBoolean(Integer.parseInt((String) v));
			} catch (Exception ex) {
				return BooleanUtils.toBoolean((String)v);
			}
		}).orElse(null);
	}

	public static Long toLong(Object value) {
		return Optional.ofNullable(value)
				.map(v -> Long.parseLong((String) v))
				.orElse(null);
	}

	public static Map<String, Class<?>> getSearchFieldWithType(Class<?> cls){
		return getSearchFieldWithType(cls, "");
	}

	public static Map<String, Class<?>> getSearchFieldWithType(Class<?> cls, String baseName){
		List<Class<?>> joinedClass =  new ArrayList<>();
		return getSearchFieldWithType(cls, baseName, joinedClass);
	}

	public static Map<String, Class<?>> getSearchFieldWithType(Class<?> cls, String baseName, List<Class<?>> joinedClasses){
		String format = StringUtils.hasText(baseName) ? "%s.%s": "%s%s";
		return Stream.concat(
				cls.getSuperclass() == null ? Stream.empty() : getSearchFieldWithType(cls.getSuperclass()).entrySet().stream(),
				Arrays.stream(cls.getDeclaredFields())
						.filter(f -> !joinedClasses.contains(f.getType()))
						.flatMap(field -> manageInnerFields(field, joinedClasses))
						.flatMap(e -> e.entrySet().stream())
		)
				.filter(Objects::nonNull)
				.collect(Collectors.toMap(e -> String.format(format, baseName, e.getKey()), Map.Entry::getValue, (oldValue, newValue) -> newValue));
	}

	private static Stream<Map<String, Class<?>>> manageInnerFields(Field field, List<Class<?>> joinedClasses) {
		if (field.getGenericType() instanceof ParameterizedType) {
			joinedClasses.add(field.getDeclaringClass());
			return Arrays.stream(((ParameterizedType)field.getGenericType()).getActualTypeArguments())
					.filter(type -> type instanceof Class<?>)
					.map(type -> (Class<?>) type)
					.filter(clazz -> !joinedClasses.contains(clazz))
					.map(clazz -> (Class<?>) clazz)
					.map(clazz -> getSearchFieldWithType(clazz, field.getName(), joinedClasses));
		} else if (!field.getType().isPrimitive() && !managedType.contains(field.getType())) { // is a type to manage?
			if (!joinedClasses.contains(field.getDeclaringClass())) joinedClasses.add(field.getDeclaringClass());
			return Stream.of(getSearchFieldWithType(field.getType(), field.getName(), joinedClasses));
		} else {
			return Stream.of(Map.of(field.getName(), field.getType()));
		}
	}

}
