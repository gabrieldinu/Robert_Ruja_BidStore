package ro.fortech.application.bidstore.backend.exception;

/**
 * Created by robert.ruja on 18-Apr-17.
 */
public class AccountEmailException extends AccountException {
    public AccountEmailException(Exception ex) {
        super(ex);
    }

    public AccountEmailException(String message) {
        super(message);
    }
}
