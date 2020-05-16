package it.usuratonkachi.searchcriteria.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Container di search criteria. JPA Specialization necessita di un object per poter gestire query con condizioni dipendenti
 * da altre tabelle. Volendo lasciare intatto l'oggetto SearchCriteria delle shared utilizziamo questa classe per
 * convertire il messaggio in ciò che viene utilizzato dal core a basso livello.

 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteriaContainer {

	List<SearchCriteriaSpecification> searchCriteriaSpecificationsAnd;
	List<SearchCriteriaSpecification> searchCriteriaSpecificationsOr;
	List<SearchCriteriaSpecification> delegationCriteriaSpecification;

	public SearchCriteriaContainer(List<SearchCriteria> searchCriteria){
		doConvert(searchCriteria, null);
	}

	public SearchCriteriaContainer(List<SearchCriteria> searchCriteria, List<SearchCriteria> searchCriteriaOR) {
		doConvert(searchCriteria, searchCriteriaOR);
	}

	private void doConvert(List<SearchCriteria> searchCriteria, List<SearchCriteria> searchCriteriaOR) {
		searchCriteriaSpecificationsAnd = convertSearchCriteriaAnd(searchCriteria);
		searchCriteriaSpecificationsOr = convertSearchCriteriaOr(searchCriteriaOR);
	}

	/**
	 * Metodo che converte il search criteria presente nella libreria delle shared api con quello che si aspetta
	 * la Jpa Specification.
	 *
	 * @param searchCriteriaList
	 * @return
	 */
	private List<SearchCriteriaSpecification> convertSearchCriteriaAnd(List<SearchCriteria> searchCriteriaList) {
		return searchCriteriaList == null ?
				new ArrayList<>() :
				searchCriteriaList.stream().map(this::filterBetween).filter(sc -> !StringUtils.isEmpty(sc.getValue())).map(SearchCriteriaSpecification::new).collect(Collectors.toList());
	}

	private List<SearchCriteriaSpecification> convertSearchCriteriaOr(List<SearchCriteria> searchCriteriaList) {
		return searchCriteriaList == null ?
				new ArrayList<>() :
				searchCriteriaList.stream().map(this::filterBetween).filter(sc -> !StringUtils.isEmpty(sc.getValue())).map(SearchCriteriaSpecification::new).collect(Collectors.toList());
	}

	public SearchCriteriaContainer addAndList(SearchCriteriaSpecification... searchCriteriaSpecifications){
		return addAndList(Arrays.stream(searchCriteriaSpecifications).collect(Collectors.toList()));
	}

	public SearchCriteriaContainer addOrList(SearchCriteriaSpecification... searchCriteriaSpecifications){
		return addOrList(Arrays.stream(searchCriteriaSpecifications).collect(Collectors.toList()));
	}

	public SearchCriteriaContainer addAndList(List<SearchCriteriaSpecification> searchCriteriaSpecifications){
		if (this.searchCriteriaSpecificationsAnd == null)
			this.searchCriteriaSpecificationsAnd = new ArrayList<>(searchCriteriaSpecifications);
		else
			this.searchCriteriaSpecificationsAnd.addAll(searchCriteriaSpecifications);
		return this;
	}

	public SearchCriteriaContainer addOrList(List<SearchCriteriaSpecification> searchCriteriaSpecifications){
		if (this.searchCriteriaSpecificationsOr == null)
			this.searchCriteriaSpecificationsOr = new ArrayList<>(searchCriteriaSpecifications);
		else
			this.searchCriteriaSpecificationsOr.addAll(searchCriteriaSpecifications);
		return this;
	}

	public SearchCriteriaContainer addDelegation(SearchCriteriaSpecification delegationCriteria){
		return addDelegation(List.of(delegationCriteria));
	}

	public SearchCriteriaContainer addDelegation(List<SearchCriteriaSpecification> delegationCriteria){
		if (this.delegationCriteriaSpecification == null)
			this.delegationCriteriaSpecification = new ArrayList<>(delegationCriteria);
		else
			this.delegationCriteriaSpecification.addAll(delegationCriteria);
		return this;
	}

	private SearchCriteria filterBetween(SearchCriteria searchCriteria){
		if (SearchOperator.BETWEEN.name().equalsIgnoreCase(searchCriteria.getOperator())){
			if (StringUtils.isEmpty(searchCriteria.getValue()) && StringUtils.isEmpty(searchCriteria.getValueAdditional())) {
				searchCriteria.setValue("");
			} else if (StringUtils.isEmpty(searchCriteria.getValue())){
				searchCriteria.setOperator(SearchOperator.LTE.name());
				searchCriteria.setValue(searchCriteria.getValueAdditional());
				searchCriteria.setValueAdditional("");
			} else if (StringUtils.isEmpty(searchCriteria.getValueAdditional())){
				searchCriteria.setOperator(SearchOperator.GTE.name());
			}
		}
		return searchCriteria;
	}

}
