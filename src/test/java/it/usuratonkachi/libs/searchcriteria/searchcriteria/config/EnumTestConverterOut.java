package it.usuratonkachi.libs.searchcriteria.searchcriteria.config;

import it.usuratonkachi.libs.searchcriteria.config.SearchableEnumConverterOut;
import it.usuratonkachi.libs.searchcriteria.enumerator.SearchableEnumerator;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.enumerator.EnumTest;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class EnumTestConverterOut extends SearchableEnumConverterOut<Integer> {

    @Override
    public SearchableEnumerator<Integer> logicConversion(Integer value) {
        return EnumTest.LABEL_ONE.deserializeFromDatabase(value);
    }

}
