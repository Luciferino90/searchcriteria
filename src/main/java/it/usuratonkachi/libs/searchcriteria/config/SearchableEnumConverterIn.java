package it.usuratonkachi.libs.searchcriteria.config;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import it.usuratonkachi.libs.searchcriteria.enumerator.SearchableEnumerator;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class SearchableEnumConverterIn<T> implements Converter<SearchableEnumerator<T>, DBObject> {

    @Override
    public DBObject convert(SearchableEnumerator<T> tSearchableEnumerator) {
        return new BasicDBObject("", tSearchableEnumerator.serializeToDatabase());
    }

}
