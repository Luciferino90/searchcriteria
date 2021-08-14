package it.usuratonkachi.libs.searchcriteria.exception;

public class SearchCriteriaException extends RuntimeException {

    public SearchCriteriaException() {
    }

    public SearchCriteriaException(String message) {
        super(message);
    }

    public SearchCriteriaException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchCriteriaException(Throwable cause) {
        super(cause);
    }

    public SearchCriteriaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
