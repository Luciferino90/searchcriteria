package it.usuratonkachi.libs.searchcriteria.mysql;

import it.usuratonkachi.libs.searchcriteria.criteria.ComposedSearchCriteria;
import it.usuratonkachi.libs.searchcriteria.criteria.SearchCriteria;
import it.usuratonkachi.libs.searchcriteria.criteria.LogicalOperator;
import it.usuratonkachi.libs.searchcriteria.criteria.SingleSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public class QueryBuilder<T> {

	private final Class<T> cls;

	private final BiFunction<Specification<T>, Specification<T>, Specification<T>> andCriteria =
			(specification, searchCriteriaWrapper) ->
					Optional.ofNullable(specification)
							.map(ref -> ref.and(searchCriteriaWrapper))
							.orElse(searchCriteriaWrapper);

	private final BiFunction<Specification<T>, Specification<T>, Specification<T>> orCriteria =
			(specification, searchCriteriaWrapper) ->
					Optional.ofNullable(specification)
							.map(ref -> ref.or(searchCriteriaWrapper))
							.orElse(searchCriteriaWrapper);


	public QueryBuilder(Class<T> cls){
		this.cls = cls;
	}

	public Specification<T> specificationBuilder(SearchCriteria searchCriteriaWrapper){
		if (searchCriteriaWrapper instanceof ComposedSearchCriteria) {
			return specificationBuilder((ComposedSearchCriteria) searchCriteriaWrapper);
		} else {
			return specificationBuilder((SingleSearchCriteria) searchCriteriaWrapper);
		}
	}

	private Specification<T> specificationBuilder(ComposedSearchCriteria searchCriteriaWrapper) {
		AtomicReference<Specification<T>> refSpec = new AtomicReference<>();
		searchCriteriaWrapper.getInnerSearchCriteria()
				.forEach(criteria -> {
					if (criteria instanceof ComposedSearchCriteria) {
						Specification<T> s = specificationBuilder((ComposedSearchCriteria) criteria);
						refSpec.set(addCriteria(refSpec.get(), s, ((ComposedSearchCriteria) criteria).getLogicalOperator()));
					} else {
						Specification<T> s = new GenericJpaSpecification<T>((SingleSearchCriteria) criteria, cls);
						refSpec.set(addCriteria(refSpec.get(), s, searchCriteriaWrapper.getLogicalOperator()));
					}
				});
		return refSpec.get();
	}

	private Specification<T> specificationBuilder(SingleSearchCriteria searchCriteriaWrapper) {
		return new GenericJpaSpecification<T>(searchCriteriaWrapper, cls);
	}

	private Specification<T> addCriteria(Specification<T> oldCriteria, Specification<T> newCriteria, LogicalOperator logicalOperator) {
		if (logicalOperator == LogicalOperator.AND) {
			return andCriteria.apply(oldCriteria, newCriteria);
		} else {
			return orCriteria.apply(oldCriteria, newCriteria);
		}
	}

}
