package it.usuratonkachi.searchcriteria.mysql;

import it.usuratonkachi.searchcriteria.common.SearchCriteriaContainer;
import it.usuratonkachi.searchcriteria.common.SearchCriteriaSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Classe che gestisce la doppia condizione di or e and nelle search criteria.
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class QueryBuilder<T> {

	private Class<?> cls;

	public QueryBuilder(Class<?> cls){
		this.cls = cls;
	}

	/**
	 * Classe utilizzata per generare le specification in caso di richiesta solamente con operatori in AND
	 * @param searchCriteriaList
	 * @return
	 */
	public Specification<T> specificationBuilder(List<SearchCriteriaSpecification> searchCriteriaList){
		return specificationBuilder(searchCriteriaList, null, null);
	}

	/**
	 * Classe utilizzata per generare le specification in caso di richiesta sia con operatori in AND che in OR.
	 * @param searchCriteriaList
	 * @param searchCriteriaORList
	 * @return
	 */
	public Specification<T> specificationBuilder(List<SearchCriteriaSpecification> searchCriteriaList, List<SearchCriteriaSpecification> searchCriteriaORList){
		return specificationBuilder(searchCriteriaList, searchCriteriaORList, null);
	}

	public Specification<T> specificationBuilder(SearchCriteriaContainer searchCriteriaContainer){
		return specificationBuilder(searchCriteriaContainer.getSearchCriteriaSpecificationsAnd(), searchCriteriaContainer.getSearchCriteriaSpecificationsOr(),
				searchCriteriaContainer.getDelegationCriteriaSpecification());
	}

	/**
	 * Classe utilizzata per generare le specification in caso di richiesta sia con operatori in AND che in OR.
	 * Al termine mergia le condizioni se necessario.
	 *
	 * @param searchCriteriaList
	 * @param searchCriteriaORList
	 * @return
	 */
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

	/**
	 * Classe privata che cicla sulla lista dei criteri in and e genera una specification
	 * @param searchCriteriaList
	 * @return
	 */
	private Specification<T> specificationBuilderAND(List<SearchCriteriaSpecification> searchCriteriaList){
		AtomicReference<Specification> refSpec = new AtomicReference<>();
		searchCriteriaList.forEach(criteria -> refSpec.getAndUpdate(ref -> (ref == null) ? new GenericJpaSpecification(criteria, cls) : ref.and(new GenericJpaSpecification(criteria, cls))));
		return refSpec.get();
	}

	/**
	 * Classe privata che cicla sulla lista dei criteri in or e genera una specification
	 * @param searchCriteriaList
	 * @return
	 */
	private Specification<T> specificationBuilderOR(List<SearchCriteriaSpecification> searchCriteriaList){
		AtomicReference<Specification> refSpec = new AtomicReference<>();
		searchCriteriaList.forEach(criteria -> refSpec.getAndUpdate(ref -> (ref == null) ? new GenericJpaSpecification(criteria, cls) : ref.or(new GenericJpaSpecification(criteria, cls))));
		return refSpec.get();
	}

}
