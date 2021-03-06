package it.usuratonkachi.libs.searchcriteria.enumerator;

import com.mongodb.DBObject;

public interface SearchableEnumerator<T> {

    String getLabel();
    int getValue();
    String getDescription();
    T serializeToDatabase();
    <E extends SearchableEnumerator<T>> E deserializeFromDatabase(T val);

}
