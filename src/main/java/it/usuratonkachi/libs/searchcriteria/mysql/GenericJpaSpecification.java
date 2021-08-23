package it.usuratonkachi.libs.searchcriteria.mysql;


import it.usuratonkachi.libs.searchcriteria.common.TypeHandlerConverters;
import it.usuratonkachi.libs.searchcriteria.criteria.SingleSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;


public class GenericJpaSpecification<T> implements Specification<T> {

	private final SingleSearchCriteria searchCriteria;
	private final Map<String, Class<?>> typeVariable;

	GenericJpaSpecification(SingleSearchCriteria searchCriteria, Class<T> cls){
		this.searchCriteria = searchCriteria;
		typeVariable = TypeHandlerConverters.getSearchFieldWithType(cls);
	}

	@Override
	@Nullable
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		return JpaTypeHandler.generatePredicate(searchCriteria.getField(), searchCriteria.getValue(), searchCriteria.getValueAdditional(),
				searchCriteria.getOperator(), criteriaBuilder, root, typeVariable.get(searchCriteria.getField()));
	}


}
