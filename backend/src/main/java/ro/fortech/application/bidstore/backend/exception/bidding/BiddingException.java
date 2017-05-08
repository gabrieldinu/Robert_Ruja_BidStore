package ro.fortech.application.bidstore.backend.exception.bidding;

/**
 * Created by robert.ruja on 08-May-17.
 */
public class BiddingException extends Exception {
    public BiddingException(String message) {
        super(message);
    }

    public BiddingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BiddingException(Throwable cause) {
        super(cause);
    }
}
