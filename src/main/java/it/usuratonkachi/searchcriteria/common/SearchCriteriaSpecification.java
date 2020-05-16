package it.usuratonkachi.searchcriteria.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCriteriaSpecification {

	private String field;
	private SearchOperator operator;
	private Object value;
	private Object valueAdditional;

	public SearchCriteriaSpecification(String field, SearchOperator searchOperator, Object value){
		this.field = field;
		this.operator = searchOperator;
		this.value = value;
		this.valueAdditional = null;
	}

	public SearchCriteriaSpecification(SearchCriteria searchCriteria) {
		field = searchCriteria.getField();
		operator = SearchOperator.valueOf(searchCriteria.getOperator());
		value = searchCriteria.getValue();
		valueAdditional = searchCriteria.getValueAdditional();
	}

}
