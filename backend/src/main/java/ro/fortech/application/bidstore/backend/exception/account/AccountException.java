package ro.fortech.application.bidstore.backend.exception.account;

/**
 * Created by robert.ruja on 13-Apr-17.
 */
public class AccountException extends Exception {

    public AccountException(Exception ex) {
        super(ex);
    }

    public AccountException(String message){
        super(message);
    }
}
