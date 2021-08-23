package it.usuratonkachi.libs.searchcriteria.searchcriteria.enumerator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import it.usuratonkachi.libs.searchcriteria.enumerator.SearchableEnumerator;
import it.usuratonkachi.libs.searchcriteria.exception.EnumMissingException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EnumTest implements SearchableEnumerator<Integer> {

    LABEL_ONE("LABEL_ONE", 1, "Label with value one"),
    LABEL_TWO("LABEL_TWO", 2, "Label with value two"),
    LABEL_THREE("LABEL_THREE", 3, "Label with value three");

    private final String label;
    private final int value;
    private final String description;

    @Override
    public Integer serializeToDatabase() {
        return value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public EnumTest deserializeFromDatabase(Integer val) {
        return Arrays.stream(EnumTest.values())
                .filter(e -> e.getValue() == val)
                .findFirst()
                .orElseThrow(() ->
                        new EnumMissingException(
                                String.format(
                                        "Missing enum type %s for value %s",
                                        this.getClass().getSimpleName(),
                                        val
                                )
                        )
                );
    }

}
