package group11.leafalone.Email;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
//@ActiveProfiles("test")
public class EmailTest {

    @Autowired
    LeafAloneEmailService emailService;

//    @Rule
//    public SmtpServerRule smtpServerRule = new SmtpServerRule(3025);

//    @Rule
//    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP);
//

    private GreenMail greenMail;

    @BeforeEach
    public void startMailServer() {
        ServerSetup setup = new ServerSetup(3025, "localhost", "smtp");
        greenMail = new GreenMail(setup).withConfiguration(GreenMailConfiguration.aConfig().withDisabledAuthentication());
        greenMail.start();
    }

    @AfterEach
    public void stopMailServer() {
        greenMail.stop();
    }

//    @Test
//    public void testSend() throws MessagingException {
//        GreenMailUtil.sendTextEmailTest("to@localhost.com", "from@localhost.com",
//                "some subject", "some body"); // --- Place your sending code here instead
//        assertEquals("some body", GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
//    }


    //TODO
    @Test
    public void sendingSimpleEmailToValidAddressSucceeds() throws IOException, MessagingException {

        String to = "melonlord@posteo.de";
        String subject = "Simple Mail Test";
        String text = "test Text";

        emailService.sendSimpleMessage(to, subject, text);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage current = receivedMessages[0];

        assertEquals(subject, current.getSubject());
        assertEquals(to, current.getAllRecipients()[0].toString());
        assertTrue(String.valueOf(current.getContent()).contains(text));
    }

    //for this we first need some email value validation ..... (Email needs [something]@[something].[something]-Pattern)
//    @Test
//    public void sendingSimpleEmailToInvalidAddressFails(){
//        String to = "hhhhhh";
//        String subject = "Simple Mail Test";
//        String text = "test Text";
//
//        emailService.sendSimpleMessage(to, subject, text);
//        emailService.sendSimpleMessage(to, subject, text);
//
//        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
//        assertEquals(0, receivedMessages.length);
//    }

    //TODO
    @Test
    public void registrationEmailContainsRightUsername() {

    }
//
    //    //TODO
//    @Test
//    public void registrationEmailIsSentToRightAddress(){
//
//    }

//    //TODO
//    @Test
//    public void wateringEmailContainsRightPlants(){
//
//    }

    //    //TODO
//    @Test
//    public void wateringEmailGoesToRightUsers(){
//
//    }
}
