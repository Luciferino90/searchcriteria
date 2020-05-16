package it.usuratonkachi.searchcriteria.common;


import it.usuratonkachi.searchcriteria.exception.SearchCriteriaException;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteriaCommonUtils {

	/**
	 * This method check a list size and return the first and only element. If there are more elements it will throw
	 * an exception.
	 * @param elements
	 * 		List of elements
	 * @param <T>
	 *     	Generic element type
	 * @return
	 * 		Single element
	 */
	public static <T> T findUnique(List<T> elements){
		if (elements.size()==1)
			return elements.get(0);
		else
			throw new SearchCriteriaException(elements.isEmpty() ? "Nessun risultato trovato" : "Molteplici risultati trovati");
	}

	/**
	 * Common method to convert a list of search criteria to a list of searchCriteriaSpecification
	 * @param searchCriteriaList
	 * 				Search Criteria coming from the request
	 * @return
	 * 				List of search criteria specification.
	 */
	public static List<SearchCriteriaSpecification> baseSearchCriteriaConverter(List<SearchCriteria> searchCriteriaList){
		return searchCriteriaList != null ? new SearchCriteriaContainer(searchCriteriaList).getSearchCriteriaSpecificationsAnd() : new ArrayList<>();
	}

	/**
	 * Common method to convert a list of search criteria to a list of searchCriteriaSpecification
	 * @param searchCriteriaListOpt
	 * 				Search Criteria coming from the request
	 * @param searchCriteriaORListOpt
	 * 				Search Criteria coming from the request in OR condition
	 * @return
	 * 				List of search criteria specification.
	 */
	public static SearchCriteriaContainer baseSearchCriteriaConverter(List<SearchCriteria> searchCriteriaListOpt, List<SearchCriteria> searchCriteriaORListOpt){
		return new SearchCriteriaContainer(searchCriteriaListOpt, searchCriteriaORListOpt);
	}

	/**
	 * Utility method to add a value to searchCriteriaList and return the updated list
	 * @param searchCriteriaList
	 * @param field
	 * @param value
	 * @param searchOperator
	 * @return
	 */
	public static List<SearchCriteria> addSearchCriteria(List<SearchCriteria> searchCriteriaList, String field, String value, SearchOperator searchOperator){
		searchCriteriaList.add(SearchCriteria.builder()
				.field(field)
				.operator(searchOperator.name())
				.value(value)
				.build()
		);
		return searchCriteriaList;
	}

}
