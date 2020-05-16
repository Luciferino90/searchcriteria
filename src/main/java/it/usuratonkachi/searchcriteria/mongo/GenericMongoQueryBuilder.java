package it.usuratonkachi.searchcriteria.mongo;

import it.usuratonkachi.searchcriteria.common.SearchCriteriaContainer;
import it.usuratonkachi.searchcriteria.common.SearchCriteriaSpecification;
import it.usuratonkachi.searchcriteria.common.TypeHandlerConverters;
import it.usuratonkachi.searchcriteria.exception.SearchCriteriaException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

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

    public Query buildQuery(List<SearchCriteriaSpecification> searchCriteriaSpecificationAndList) {
        return buildQuery(searchCriteriaSpecificationAndList, new ArrayList<>(), new ArrayList<>());
    }

    public Query buildQuery(SearchCriteriaContainer searchCriteriaContainer) {
        return buildQuery(searchCriteriaContainer.getSearchCriteriaSpecificationsAnd(), searchCriteriaContainer.getSearchCriteriaSpecificationsOr(),
                searchCriteriaContainer.getDelegationCriteriaSpecification());
    }

    private Query buildQuery(List<SearchCriteriaSpecification> searchCriteriaSpecificationAndList,
            List<SearchCriteriaSpecification> searchCriteriaSpecificationOrList,
            List<SearchCriteriaSpecification> delegationCriteriaList
    ) {
        Query query = new Query();
        query.addCriteria(
                new Criteria()
                        .andOperator(
                                Stream.of(
                                        searchCriteriaSpecificationAndList.stream().map(this::toCriteria),
                                        delegationCriteriaList != null && !delegationCriteriaList.isEmpty() ? Stream.of(new Criteria().orOperator(delegationCriteriaList.stream().map(this::toCriteria).toArray(Criteria[]::new))) : Stream.empty(),
                                        searchCriteriaSpecificationOrList != null && !searchCriteriaSpecificationOrList.isEmpty() ? Stream.of(new Criteria().orOperator(searchCriteriaSpecificationOrList.stream().map(this::toCriteria).toArray(Criteria[]::new))) : Stream.empty()
                                )
                                        .flatMap(Function.identity())
                                        .map(Criteria.class::cast)
                                        .toArray(Criteria[]::new)
                        )
        );
        return query;
    }

    public Query buildQuery(List<SearchCriteriaSpecification> searchCriteriaSpecificationAndList,
            List<SearchCriteriaSpecification> searchCriteriaSpecificationOrList,
            List<SearchCriteriaSpecification> delegationCriteriaList, Pageable pageable
    ) {
        return buildQuery(searchCriteriaSpecificationAndList, searchCriteriaSpecificationOrList, delegationCriteriaList)
                .with(pageable);
    }

    @Override
    public Criteria toCriteria(SearchCriteriaSpecification searchCriteriaSpecification) {
        if (searchCriteriaSpecification.getField().contains(":"))
            searchCriteriaSpecification.setField(searchCriteriaSpecification.getField().replace(":", "."));
        else {
            searchCriteriaSpecification.setField(fieldToLowercase(searchCriteriaSpecification.getField()));
            if (!typeVariable.containsKey(searchCriteriaSpecification.getField())) {
                throw new SearchCriteriaException(String.format("Ricerca per campo %s non supportata", searchCriteriaSpecification.getField()));
            }
        }
        return MongoTypeHandler
                .generateCriteria(searchCriteriaSpecification.getField(), searchCriteriaSpecification.getValue(), searchCriteriaSpecification.getValueAdditional(), searchCriteriaSpecification.getOperator(), typeVariable.get(searchCriteriaSpecification.getField()));
    }


    private String fieldToLowercase(String field) {
        if (field.contains("#")) {
            String firstPart = field.split("#")[0];
            String secondPart = field.split("#")[1];
            return firstPart.toLowerCase() + "#" + secondPart;
        }
        return field.toLowerCase();
    }

}
