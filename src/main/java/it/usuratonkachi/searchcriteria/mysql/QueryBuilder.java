package it.usuratonkachi.searchcriteria.mysql;

import it.usuratonkachi.searchcriteria.common.SearchCriteriaContainer;
import it.usuratonkachi.searchcriteria.common.SearchCriteriaSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("unchecked")
public class QueryBuilder<T> {

	private final Class<?> cls;

	public QueryBuilder(Class<?> cls){
		this.cls = cls;
	}

	public Specification<T> specificationBuilder(List<SearchCriteriaSpecification> searchCriteriaList){
		return specificationBuilder(searchCriteriaList, null, null);
	}

	public Specification<T> specificationBuilder(List<SearchCriteriaSpecification> searchCriteriaList, List<SearchCriteriaSpecification> searchCriteriaORList){
		return specificationBuilder(searchCriteriaList, searchCriteriaORList, null);
	}

	public Specification<T> specificationBuilder(SearchCriteriaContainer searchCriteriaContainer){
		return specificationBuilder(searchCriteriaContainer.getSearchCriteriaSpecificationsAnd(), searchCriteriaContainer.getSearchCriteriaSpecificationsOr(),
				searchCriteriaContainer.getDelegationCriteriaSpecification());
	}

	private Specification<T> specificationBuilder(List<SearchCriteriaSpecification> searchCriteriaList,
			List<SearchCriteriaSpecification> searchCriteriaORList, List<SearchCriteriaSpecification> delegationList){

		List<Specification> chainSpec = new ArrayList<>();
		if (searchCriteriaList != null && !searchCriteriaList.isEmpty())
			chainSpec.add(specificationBuilderAND(searchCriteriaList));
		if (searchCriteriaORList != null && !searchCriteriaORList.isEmpty())
			chainSpec.add(specificationBuilderOR(searchCriteriaORList));
		if (delegationList != null && !delegationList.isEmpty())
			chainSpec.add(specificationBuilderOR(delegationList));

		AtomicReference<Specification> refSpec = new AtomicReference<>();
		chainSpec.forEach(spec -> refSpec.getAndUpdate(ref -> (ref == null) ? spec : ref.and(spec)));

		return refSpec.get();
	}

	private Specification<T> specificationBuilderAND(List<SearchCriteriaSpecification> searchCriteriaList){
		AtomicReference<Specification> refSpec = new AtomicReference<>();
		searchCriteriaList.forEach(criteria -> refSpec.getAndUpdate(ref -> (ref == null) ? new GenericJpaSpecification(criteria, cls) : ref.and(new GenericJpaSpecification(criteria, cls))));
		return refSpec.get();
	}

	private Specification<T> specificationBuilderOR(List<SearchCriteriaSpecification> searchCriteriaList){
		AtomicReference<Specification> refSpec = new AtomicReference<>();
		searchCriteriaList.forEach(criteria -> refSpec.getAndUpdate(ref -> (ref == null) ? new GenericJpaSpecification(criteria, cls) : ref.or(new GenericJpaSpecification(criteria, cls))));
		return refSpec.get();
	}

}
