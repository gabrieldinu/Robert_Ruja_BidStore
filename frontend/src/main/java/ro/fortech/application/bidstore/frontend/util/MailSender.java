package ro.fortech.application.bidstore.frontend.util;

import ro.fortech.application.bidstore.backend.persisetence.entity.User;
import ro.fortech.application.bidstore.frontend.mvc.model.Email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by robert.ruja on 13-Apr-17.
 */
public class MailSender implements Serializable {

    private Properties properties;
    private final String username;
    private final String password;
    private final String from;

    public MailSender(Properties properties) {
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

    public void sendMail(Email email) {
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
    }

}
