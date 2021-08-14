package it.usuratonkachi.libs.searchcriteria.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SingleSearchCriteria implements SearchCriteria {

    private static final long serialVersionUID = -1669018802677104365L;
    private String field;
    private SearchOperator operator;
    private Object value;
    private Object valueAdditional;

}
