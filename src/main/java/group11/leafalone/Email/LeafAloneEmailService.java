package group11.leafalone.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class LeafAloneEmailService { //implements EmailService????

    JavaMailSender mailSender;

    public LeafAloneEmailService() {
        mailSender = getJavaMailSender();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("lunaire426@gmail.com");
        mailSender.setPassword("nvpblwnxrqqkmpar");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;

//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.mail.de");
//        mailSender.setPort(587);
//
//        mailSender.setUsername("leafalone@mail.de");
//        mailSender.setPassword("gYcR6PAX9SnX");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
    }

    public void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Async
    public void sendRegistrationMail(String username, String email) {

        String emailText = "Hello " + username + "!" + System.lineSeparator() + "Thank you for registering on our site. We hope you enjoy your stay." + System.lineSeparator() + "Your LeafAlone team";

        sendSimpleMessage(email, "Registration Complete!", emailText);

    }

    @Async
    public void sendWaterReminderMail() {

    }

}
