package ro.fortech.application.bidstore.backend.service.mail;


import ro.fortech.application.bidstore.backend.exception.email.EmailException;
import ro.fortech.application.bidstore.backend.model.Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.Properties;

/**
 * Created by robert.ruja on 13-Apr-17.
 */
public class MailService implements Serializable {

    private Properties properties;
    private String username;
    private String password;
    private String from;


    public MailService(Properties properties) {
        configure(properties);

    }

    public MailService(){

    }

    public void configure(Properties properties) {

        this.properties = properties;

        //general mail settings
        this.properties.put("mail.smtp.auth", "true");
        this.properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        this.properties.put("mail.smtp.socketFactory.port", properties.getProperty("mail.server.port"));
        this.properties.put("mail.smtp.port", properties.getProperty("mail.server.port"));
        this.properties.put("mail.smtp.host", properties.getProperty("mail.server.address"));

        //custom connection settings
        this.from = (String) properties.get("mail.from");
        this.username = (String) properties.get("mail.username");
        this.password = (String) properties.get("mail.password");
    }

    public void sendMail(Email email) throws EmailException{
        if(this.properties != null) {
            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email.getFrom()));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(email.getTo()));
                message.setSubject(email.getSubject());
                message.setText(email.getEmailText());
                Transport.send(message);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new EmailException("The mail service was not configured!");
        }
    }

}
