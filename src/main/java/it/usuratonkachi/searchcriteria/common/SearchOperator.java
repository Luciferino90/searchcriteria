package it.usuratonkachi.searchcriteria.common;

import java.util.Arrays;

public enum SearchOperator {

    LIKE(0, "Like"),
    EQUAL(1, "Equal"),
    GT(2, "Greater Than"),
    GTE(3, "Greater Than Equal"),
    LT(4, "Lesser Than"),
    LTE(5, "Lesser Than Equal"),
    BETWEEN(6, "Between"),
    NOTEQUAL(7, "Not equal"),
    NULL(8, "Empty"),
    NOTNULL(9, "Not empty"),
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
