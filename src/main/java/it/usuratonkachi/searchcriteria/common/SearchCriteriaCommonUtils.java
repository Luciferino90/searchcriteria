package it.usuratonkachi.searchcriteria.common;


import it.usuratonkachi.searchcriteria.exception.SearchCriteriaException;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteriaCommonUtils {

	public static <T> T findUnique(List<T> elements){
		if (elements.size()==1)
			return elements.get(0);
		else
			throw new SearchCriteriaException(elements.isEmpty() ? "No result" : "Multiple results");
	}

	public static List<SearchCriteriaSpecification> baseSearchCriteriaConverter(List<SearchCriteria> searchCriteriaList){
		return searchCriteriaList != null ? new SearchCriteriaContainer(searchCriteriaList).getSearchCriteriaSpecificationsAnd() : new ArrayList<>();
	}

	public static SearchCriteriaContainer baseSearchCriteriaConverter(List<SearchCriteria> searchCriteriaListOpt, List<SearchCriteria> searchCriteriaORListOpt){
		return new SearchCriteriaContainer(searchCriteriaListOpt, searchCriteriaORListOpt);
	}

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
