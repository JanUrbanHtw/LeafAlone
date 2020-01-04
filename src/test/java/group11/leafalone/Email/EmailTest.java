package group11.leafalone.Email;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import group11.leafalone.Auth.LeafAloneUser;
import group11.leafalone.LeafAloneUtil;
import group11.leafalone.Plant.Plant;
import group11.leafalone.Plant.PlantCare;
import group11.leafalone.Plant.PlantService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmailTest {

    @Autowired
    PlantService plantService;
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

    //TODO stop it from misbehaving
//    @Test
//    public void registrationEmailContainsRightUsername() throws IOException, MessagingException {
//        String to = "melonlord@posteo.de";
//        String username = "username";
//
//        emailService.sendRegistrationMail("username", to);
//
//        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
//        assertEquals(1, receivedMessages.length);
//
//        MimeMessage current = receivedMessages[0];
//
//        assertTrue(current.getContent().toString().contains(username));
//    }

    //    //TODO
//    @Test
//    public void registrationEmailIsSentToRightAddress(){
//
//    }

    //    //TODO
    //how do I make plantService not null?
    //Do I have to do dependency injection in EmailService? I thought I was already kinda doing constructor injection ...
//    @Test
//    public void wateringEmailContainsRightPlants() throws ParseException {
//        LeafAloneUser user = new LeafAloneUser.Builder().withUsername("user").build();
//        Date date = new SimpleDateFormat("2020-01-01").getCalendar().getTime();
//
//        PlantCare plantCare = new PlantCare.Builder().withColloquial("coll1").build();
//        Plant plant = new Plant.Builder().withName("test1").withNextWatering(date).withPlantCare(plantCare).build();
//        Plant plant2 = new Plant.Builder().withName("test2").withNextWatering(date).withPlantCare(plantCare).build();
//        List<Plant> plantList = new LinkedList<>();
//        plantList.add(plant);
//        plantList.add(plant2);
//        ArgumentCaptor<PlantService> plantService = ArgumentCaptor.forClass(PlantService.class);
//        when(emailService.plantService.findByLeafAloneUserOrdered(user)).thenReturn(plantList);
//        when(plant.getLeafAloneUser()).thenReturn(user);
//        when(plant2.getLeafAloneUser()).thenReturn(user);
//
//        when(LeafAloneUtil.getTodayAtMidnight()).thenReturn(date);
//
//        emailService.sendWaterReminderMail();
//
//        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
//        assertEquals(1, receivedMessages.length);
//
//        MimeMessage current = receivedMessages[0];
//    }

    //    //TODO
//    @Test
//    public void wateringEmailGoesToRightUsers(){
//
//    }
}
