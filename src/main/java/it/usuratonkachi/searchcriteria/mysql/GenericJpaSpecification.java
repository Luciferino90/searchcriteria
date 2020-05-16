package it.usuratonkachi.searchcriteria.mysql;


import it.usuratonkachi.searchcriteria.common.SearchCriteriaSpecification;
import it.usuratonkachi.searchcriteria.exception.SearchCriteriaException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

import static it.usuratonkachi.searchcriteria.common.TypeHandlerConverters.getSearchFieldWithType;


/**
 * Classe generica per creare query utilizzando JPA Specification
 */
@SuppressWarnings("unchecked")
public class GenericJpaSpecification implements Specification {

	private SearchCriteriaSpecification searchCriteria;
	private Map<String, Class<?>> typeVariable;

	GenericJpaSpecification(SearchCriteriaSpecification searchCriteria, Class<?> cls){
		this.searchCriteria = searchCriteria;
		typeVariable = getSearchFieldWithType(cls);
	}

	/**
	 * Metodo per trasformare un search criteria in un predicato da aggiungere al builder.
	 * Dato che non è stato identificato un metodo a priori per definire il tipo di oggetto presente nei campi, si
	 * prova a gestire ogni oggetto come LocalDateTime. Se l'operazione fallisce proseguiamo con la richiesta.
	 *
	 * @param root
	 * @param criteriaQuery
	 * @param criteriaBuilder
	 * @return
	 */
	@Override
	public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
		//try {
		//	if (typeVariable.get(searchCriteria.getField()).isEnum())
		//		return JpaTypeHandler.generatePredicate(
		//				searchCriteria.getField(),
		//				new EnumConverter(typeVariable.get(searchCriteria.getField())).convertToEntityAttribute(searchCriteria.getValue().toString()),
		//				searchCriteria.getValueAdditional(),
		//				searchCriteria.getOperator(), criteriaBuilder, root, typeVariable.get(searchCriteria.getField()));
		//} catch (Exception ex) {
		//	throw new SearchCriteriaException("Campo di ricerca " + searchCriteria.getField() + " non valido per il valore " + searchCriteria.getValue(), ex);
		//}

		return JpaTypeHandler.generatePredicate(searchCriteria.getField(), searchCriteria.getValue(), searchCriteria.getValueAdditional(),
				searchCriteria.getOperator(), criteriaBuilder, root, typeVariable.get(searchCriteria.getField()));
	}


}
