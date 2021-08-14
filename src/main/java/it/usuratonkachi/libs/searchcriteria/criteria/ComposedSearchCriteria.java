package it.usuratonkachi.libs.searchcriteria.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComposedSearchCriteria implements SearchCriteria {

    private static final long serialVersionUID = -1669018802677104366L;

    private LogicalOperator logicalOperator;
    private List<SearchCriteria> innerSearchCriteria;

}
