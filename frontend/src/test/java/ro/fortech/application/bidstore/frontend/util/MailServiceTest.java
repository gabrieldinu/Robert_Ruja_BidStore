package ro.fortech.application.bidstore.frontend.util;

import org.junit.Before;
import org.junit.Test;
import ro.fortech.application.bidstore.backend.service.mail.MailService;

/**
 * Created by robert.ruja on 14-Apr-17.
 */

public class MailServiceTest {

    MailService mailService;

    @Before
    public void setUp() {
//        Properties properties = new Properties();
//        try {
//            properties.load(new InputStreamReader(MailService.class.getResourceAsStream("config.properties")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        properties.put("mail.server.address", "smtp.gmail.com");
//        properties.put("mail.server.port", "465");
//        properties.put("mail.from","caveat-emptor@fortech.ro");
//        properties.put("mail.username","ruja.robert.email");
//        properties.put("mail.password","googleaccount");
        //mailService = new MailService();
    }

    @Test
    public void TestSendEmail(){
        //not an actual test
//        mailService.sendConfirmationEmail(
//                new User("gigi","gigi","gigi","robert.ruja@fortech.ro", UserRole.ADMIN),
//                UUID.randomUUID()
//        );
    }
}
