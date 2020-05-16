package it.usuratonkachi.searchcriteria.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SearchCriteria implements Serializable {
	static final long serialVersionUID = 20181106_1840L;

	private String field;
	private String operator;
	private String value;
	private String valueAdditional;

}
