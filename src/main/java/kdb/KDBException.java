package kdb;

/** Represents an expected error caused by invalid chatbot input. */
public class KDBException extends Exception {
    /**
     * Creates an exception with a message suitable for displaying to the user.
     *
     * @param message explanation of the invalid command or input
     */
    public KDBException(String message) {
        super(message);
    }
}
