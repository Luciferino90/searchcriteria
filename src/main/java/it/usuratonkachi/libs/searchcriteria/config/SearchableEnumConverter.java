package it.usuratonkachi.libs.searchcriteria.config;

import it.usuratonkachi.libs.searchcriteria.enumerator.SearchableEnumerator;
import it.usuratonkachi.libs.searchcriteria.exception.EnumMissingException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class SearchableEnumConverter<T extends SearchableEnumerator<T>> {

    private final Class<T> type;

    public SearchableEnumerator<T> convertToJava(T val) {
        return Arrays.stream(type.getEnumConstants())
                .filter(candidate -> convertToDB(candidate) == val)
                .findFirst()
                .orElseThrow(() -> new EnumMissingException("Missing value for field " + val));
    }

    public T convertToDB(SearchableEnumerator<T> enumerator) {
        return enumerator.serializeToDatabase();
    }

}
