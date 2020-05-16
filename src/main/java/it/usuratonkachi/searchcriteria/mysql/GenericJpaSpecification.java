package it.usuratonkachi.searchcriteria.mysql;


import it.usuratonkachi.searchcriteria.common.SearchCriteriaSpecification;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

import static it.usuratonkachi.searchcriteria.common.TypeHandlerConverters.getSearchFieldWithType;


@SuppressWarnings("unchecked")
public class GenericJpaSpecification implements Specification {

	private SearchCriteriaSpecification searchCriteria;
	private Map<String, Class<?>> typeVariable;

	GenericJpaSpecification(SearchCriteriaSpecification searchCriteria, Class<?> cls){
		this.searchCriteria = searchCriteria;
		typeVariable = getSearchFieldWithType(cls);
	}

	@Override
	public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
		return JpaTypeHandler.generatePredicate(searchCriteria.getField(), searchCriteria.getValue(), searchCriteria.getValueAdditional(),
				searchCriteria.getOperator(), criteriaBuilder, root, typeVariable.get(searchCriteria.getField()));
	}


}
