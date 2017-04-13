package ro.fortech.application.bidstore.mvc.model;


/**
 * Created by robert.ruja on 13-Apr-17.
 */
public class Email {

    private String from;
    private String to;
    private String emailText;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getEmailText() {
        return emailText;
    }

    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }
}
