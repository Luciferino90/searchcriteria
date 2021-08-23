package it.usuratonkachi.libs.searchcriteria.config;

import com.mongodb.DBObject;
import it.usuratonkachi.libs.searchcriteria.enumerator.SearchableEnumerator;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SearchableEnumConverterOut<T> implements Converter<DBObject, SearchableEnumerator<T>> {

    @Override
    public SearchableEnumerator<T> convert(DBObject dbObject) {
        // TODO
        return null;
    }
}
