package it.usuratonkachi.searchcriteria.common;

import java.util.Arrays;

public enum SearchOperator {

    LIKE(0, "Simile"),
    EQUAL(1, "Uguale"),
    GT(2, "Maggiore"),
    GTE(3, "Maggiore uguale"),
    LT(4, "Minore"),
    LTE(5, "Minore Uguale"),
    BETWEEN(6, "Compreso"),
    NOTEQUAL(7, "Diverso"),
    NULL(8, "Vuoto"),
    NOTNULL(9, "Non vuoto"),
    IN(10, "In");

    private final Integer value;
    private final String message;

    SearchOperator(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static SearchOperator valueOf(Integer value){
        return Arrays.stream(SearchOperator.values()).filter(en -> en.getValue().equals(value)).findFirst().orElse(null);
    }

}
