package it.usuratonkachi.libs.searchcriteria.mongo;

import it.usuratonkachi.libs.searchcriteria.criteria.SearchCriteria;
import it.usuratonkachi.libs.searchcriteria.criteria.SingleSearchCriteria;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Map;

public abstract class MongoQueryBuilder {

    @Getter
    Class<?> cls;
    Map<String, Class<?>> typeVariable;

    public abstract Query buildQuery(SearchCriteria searchCriteriaWrapper);
    public abstract Query buildQuery(SearchCriteria searchCriteriaWrapper, Pageable pageable);
    public abstract Criteria toCriteria(SingleSearchCriteria searchCriteriaWrapper);
    public abstract Map<String, Class<?>> getSearchFieldWithType(Class<?> cls);

}
