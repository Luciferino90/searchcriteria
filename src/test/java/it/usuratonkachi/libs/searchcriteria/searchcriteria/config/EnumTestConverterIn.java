package it.usuratonkachi.libs.searchcriteria.searchcriteria.config;

import it.usuratonkachi.libs.searchcriteria.config.SearchableEnumConverterIn;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.enumerator.EnumTest;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class EnumTestConverterIn extends SearchableEnumConverterIn<Integer> {

}
