package it.usuratonkachi.libs.searchcriteria.config;

import com.mongodb.DBObject;
import it.usuratonkachi.libs.searchcriteria.enumerator.SearchableEnumerator;
import it.usuratonkachi.libs.searchcriteria.exception.EnumMissingException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public abstract class SearchableEnumConverterOut<T> implements Converter<DBObject, SearchableEnumerator<T>> {

    public abstract SearchableEnumerator<T> logicConversion(T value);

    @Override
    @SuppressWarnings("unchecked")
    public SearchableEnumerator<T> convert(DBObject dbObject) {
        return dbObject.keySet()
                .stream()
                .map(val -> (T) val)
                .map(this::logicConversion)
                .findFirst()
                .orElseThrow(() -> new EnumMissingException("Cannot convert enum type for object " + dbObject));
    }

}
