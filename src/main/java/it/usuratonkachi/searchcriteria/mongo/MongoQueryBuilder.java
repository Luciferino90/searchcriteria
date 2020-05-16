package it.usuratonkachi.searchcriteria.mongo;

import it.usuratonkachi.searchcriteria.common.SearchCriteriaContainer;
import it.usuratonkachi.searchcriteria.common.SearchCriteriaSpecification;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;

/**
 * Classe che ci serve per simulare la Specification di JPA anche su MongoDB.
 */
public abstract class MongoQueryBuilder {

    @Getter
    Class<?> cls;
    Map<String, Class<?>> typeVariable;

    public abstract Query buildQuery(List<SearchCriteriaSpecification> searchCriteriaSpecificationAndList);
    public abstract Query buildQuery(SearchCriteriaContainer searchCriteriaContainer);
    public abstract Query buildQuery(List<SearchCriteriaSpecification> searchCriteriaSpecificationAndList, List<SearchCriteriaSpecification> searchCriteriaSpecificationOrList, List<SearchCriteriaSpecification> delegationCriteriaList, Pageable pageable);
    public abstract Criteria toCriteria(SearchCriteriaSpecification searchCriteriaSpecification);
    public abstract Map<String, Class<?>> getSearchFieldWithType(Class<?> cls);

}
