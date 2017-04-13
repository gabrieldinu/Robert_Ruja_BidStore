package ro.fortech.application.bidstore.backend.exception;

/**
 * Created by robert.ruja on 13-Apr-17.
 */
public class AccountActivationException extends AccountException {
    public AccountActivationException(Exception ex) {
        super(ex);
    }

    public AccountActivationException(String message) {
        super(message);
    }
}
