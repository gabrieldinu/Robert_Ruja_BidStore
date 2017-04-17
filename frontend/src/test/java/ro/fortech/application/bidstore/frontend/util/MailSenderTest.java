package ro.fortech.application.bidstore.frontend.util;

import org.junit.Before;
import org.junit.Test;
import ro.fortech.application.bidstore.backend.model.UserRole;
import ro.fortech.application.bidstore.backend.persisetence.entity.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by robert.ruja on 14-Apr-17.
 */

public class MailSenderTest{

    MailSender mailSender;

    @Before
    public void setUp() {
//        Properties properties = new Properties();
//        try {
//            properties.load(new InputStreamReader(MailSender.class.getResourceAsStream("mail_config.properties")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        properties.put("mail.server.address", "smtp.gmail.com");
//        properties.put("mail.server.port", "465");
//        properties.put("mail.from","caveat-emptor@fortech.ro");
//        properties.put("mail.username","ruja.robert.email");
//        properties.put("mail.password","googleaccount");
        //mailSender = new MailSender();
    }

    @Test
    public void TestSendEmail(){
        //not an actual test
//        mailSender.sendConfirmationEmail(
//                new User("gigi","gigi","gigi","robert.ruja@fortech.ro", UserRole.ADMIN),
//                UUID.randomUUID()
//        );
    }
}
