//package group11.leafalone.Email;
//
//import org.junit.Rule;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//
//import java.io.IOException;
//import java.util.Properties;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
//public class EmailTest {
//
//    @Autowired
//    LeafAloneEmailService emailService;
//
//    //How to mock an email receiver?
//    @Rule
//    public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);
//
//    @BeforeEach
//    public void buildEmailService(){
//
////        spring.mail.host=localhost
////        spring.mail.port=2525
////        spring.mail.username=username
////        spring.mail.password=secret
//
//            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//            mailSender.setHost("localhost");
//            mailSender.setPort(2525);
//
//            mailSender.setUsername("username");
//            mailSender.setPassword("secret");
//
//            Properties props = mailSender.getJavaMailProperties();
//            props.put("mail.transport.protocol", "smtp");
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.debug", "true");
//
//            emailService.setJavaMailSender(mailSender);
//    }
//
//    //TODO
//    @Test
//    public void sendingSimpleEmailToValidAddressSucceeds() throws IOException, MessagingException {
//
//        String to = "melonlord@posteo.de";
//        String subject = "Simple Mail Test";
//        String text = "test Text";
//
//        emailService.sendSimpleMessage(to, subject, text);
//
//        MimeMessage[] receivedMessages = smtpServerRule.getMessages();
//        assertEquals(1, receivedMessages.length);
//
//        MimeMessage current = receivedMessages[0];
//
//        assertEquals(subject, current.getSubject());
//        assertEquals(to, current.getAllRecipients()[0].toString());
//        assertTrue(String.valueOf(current.getContent()).contains(text));
//    }
//
//    //TODO
//    @Test
//    public void sendingSimpleEmailToInvalidAddressFails(){
//        String to = "hhhhhh";
//        String subject = "Simple Mail Test";
//        String text = "test Text";
//
//        emailService.sendSimpleMessage(to, subject, text);
//
//        MimeMessage[] receivedMessages = smtpServerRule.getMessages();
//        assertEquals(receivedMessages.length, 0);
//    }
//
////    //TODO
////    @Test
////    public void sendingRegistrationEmailToValidAddressSucceeds(){
////
////    }
////
////    //TODO
////    @Test
////    public void sendingRegistrationEmailToInvalidAddressFails(){
////
////    }
////
////    //TODO
////    @Test
////    public void sendingWateringEmailToValidAddressSucceeds(){
////
////    }
////
////    //TODO
////    @Test
////    public void sendingWateringEmailToInvalidAddressFails(){
////
////    }
//
//
//}
