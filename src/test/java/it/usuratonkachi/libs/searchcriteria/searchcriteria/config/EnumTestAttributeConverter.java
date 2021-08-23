package it.usuratonkachi.libs.searchcriteria.searchcriteria.config;

import it.usuratonkachi.libs.searchcriteria.config.SearchableEnumAttributeConverter;
import it.usuratonkachi.libs.searchcriteria.enumerator.SearchableEnumerator;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.enumerator.EnumTest;

public class EnumTestAttributeConverter extends SearchableEnumAttributeConverter<Integer> {

    @Override
    public SearchableEnumerator<Integer> logicConversion(Integer value) {
        return EnumTest.LABEL_ONE.deserializeFromDatabase(value);
    }

}
