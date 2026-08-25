package kdb;

/** Represents an expected error caused by invalid chatbot input. */
public class KDBException extends Exception {
    public KDBException(String message) {
        super(message);
    }
}
