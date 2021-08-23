package it.usuratonkachi.libs.searchcriteria.exception;

public class EnumMissingException extends RuntimeException {

    public EnumMissingException() {
    }

    public EnumMissingException(String message) {
        super(message);
    }

    public EnumMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnumMissingException(Throwable cause) {
        super(cause);
    }

    public EnumMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
