package ro.fortech.application.bidstore.frontend.util;

import ro.fortech.application.bidstore.frontend.mvc.model.Email;


public class EmailBuilder {

    private Email email;

    private EmailBuilder(){
        this.email = new Email("","","","");
    }

    public static EmailBuilder getEmailBuilder(){
        return new EmailBuilder();
    }

    public EmailBuilder withFrom(String from){
        this.email.setFrom(from);
        return this;
    }
    public EmailBuilder withTo(String to){
        this.email.setTo(to);
        return this;
    }
    public EmailBuilder withSubject(String subject){
        this.email.setSubject(subject);
        return this;
    }
    public EmailBuilder withText(String text){
        this.email.setEmailText(text);
        return this;
    }
    public Email build() {
        return this.email;
    }

}
