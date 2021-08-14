package it.usuratonkachi.libs.searchcriteria.mongo;

import it.usuratonkachi.libs.searchcriteria.common.TypeHandlerConverters;
import it.usuratonkachi.libs.searchcriteria.exception.SearchCriteriaException;
import it.usuratonkachi.libs.searchcriteria.criteria.ComposedSearchCriteria;
import it.usuratonkachi.libs.searchcriteria.criteria.SearchCriteria;
import it.usuratonkachi.libs.searchcriteria.criteria.LogicalOperator;
import it.usuratonkachi.libs.searchcriteria.criteria.SingleSearchCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class GenericMongoQueryBuilder extends MongoQueryBuilder {

    public GenericMongoQueryBuilder(Class<?> cls) {
        this.cls = cls;
        typeVariable = getSearchFieldWithType(cls);
    }

    public GenericMongoQueryBuilder(Class<?> cls, Map<String, List<Class>> docClassCls) {
        this.cls = cls;
        typeVariable = getSearchFieldWithType(cls);
        docClassCls.forEach((key, value) -> typeVariable.put(key, value.get(0)));
    }

    @Override
    public Map<String, Class<?>> getSearchFieldWithType(Class<?> cls){
        return TypeHandlerConverters.getSearchFieldWithType(cls);
    }

    @Override
    public Criteria toCriteria(SingleSearchCriteria searchCriteriaWrapper) {
        if (searchCriteriaWrapper.getField().contains(":"))
            searchCriteriaWrapper.setField(searchCriteriaWrapper.getField().replace(":", "."));
        else {
            searchCriteriaWrapper.setField(fieldToLowercase(searchCriteriaWrapper.getField()));
            if (!typeVariable.containsKey(searchCriteriaWrapper.getField())) {
                throw new SearchCriteriaException(String.format("Ricerca per campo %s non supportata", searchCriteriaWrapper.getField()));
            }
        }
        return MongoTypeHandler
                .generateCriteria(searchCriteriaWrapper.getField(), searchCriteriaWrapper.getValue(), searchCriteriaWrapper.getValueAdditional(), searchCriteriaWrapper.getOperator(), typeVariable.get(searchCriteriaWrapper.getField()));
    }

    private String fieldToLowercase(String field) {
        if (field.contains("#")) {
            String firstPart = field.split("#")[0];
            String secondPart = field.split("#")[1];
            return firstPart.toLowerCase() + "#" + secondPart;
        }
        return field.toLowerCase();
    }

    public Query buildQuery(SearchCriteria searchCriteriaWrapper) {
        return _buildQuery(searchCriteriaWrapper);
    }

    public Query buildQuery(SearchCriteria searchCriteriaWrapper, Pageable pageable) {
        return _buildQuery(searchCriteriaWrapper).with(pageable);
    }

    private Query _buildQuery(SearchCriteria searchCriteriaWrapper) {
        return new Query().addCriteria(build(searchCriteriaWrapper));
    }

    private Criteria build(SearchCriteria searchCriteriaWrapper) {
        if (searchCriteriaWrapper instanceof ComposedSearchCriteria) {
            return build((ComposedSearchCriteria) searchCriteriaWrapper);
        } else {
            return build((SingleSearchCriteria) searchCriteriaWrapper);
        }
    }

    private Criteria build(ComposedSearchCriteria searchCriteriaWrapper) {
        AtomicReference<Criteria> refSpec = new AtomicReference<>();
        Criteria[] criteriaArray = searchCriteriaWrapper.getInnerSearchCriteria()
                .stream()
                .map(criteria -> {
                    if (criteria instanceof ComposedSearchCriteria) {
                        if (((ComposedSearchCriteria) criteria).getLogicalOperator() == LogicalOperator.AND) {
                            return new Criteria().andOperator(((ComposedSearchCriteria) criteria).getInnerSearchCriteria().stream().map(this::build).toArray(Criteria[]::new));
                        } else {
                            return new Criteria().orOperator(((ComposedSearchCriteria) criteria).getInnerSearchCriteria().stream().map(this::build).toArray(Criteria[]::new));
                        }
                    } else {
                        return toCriteria((SingleSearchCriteria) criteria);
                    }
                }).toArray(Criteria[]::new);
        if (searchCriteriaWrapper.getLogicalOperator() == LogicalOperator.AND) {
            return new Criteria().andOperator(criteriaArray);
        } else {
            return new Criteria().orOperator(criteriaArray);
        }
    }

    private Criteria build(SingleSearchCriteria searchCriteriaWrapper) {
        return toCriteria(searchCriteriaWrapper);
    }

}
