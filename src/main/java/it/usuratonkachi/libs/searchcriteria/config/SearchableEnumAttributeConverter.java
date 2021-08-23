package it.usuratonkachi.libs.searchcriteria.config;

import it.usuratonkachi.libs.searchcriteria.enumerator.SearchableEnumerator;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public abstract class SearchableEnumAttributeConverter<T> implements AttributeConverter<SearchableEnumerator<T>, T> {

    public abstract SearchableEnumerator<T> logicConversion(T value);

    @Override
    public T convertToDatabaseColumn(SearchableEnumerator<T> attribute) {
        return attribute.serializeToDatabase();
    }

    @Override
    public SearchableEnumerator<T> convertToEntityAttribute(T dbData) {
        return logicConversion(dbData);
    }

}
