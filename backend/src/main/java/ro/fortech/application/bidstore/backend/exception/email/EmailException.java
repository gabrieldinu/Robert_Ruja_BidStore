package ro.fortech.application.bidstore.backend.exception.email;

/**
 * Created by robert.ruja on 26-Apr-17.
 */
public class EmailException extends Exception {
    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailException(Throwable cause) {
        super(cause);
    }
}
