//package group11.leafalone.Email;
//
//import org.springframework.boot.autoconfigure.mail.MailProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
//@Configuration
//public class MailConfiguration {
//
//    @Bean
//    public JavaMailSender getJavaMailSender(MailProperties mailProperties) {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(mailProperties.getHost());
//        mailSender.setPort(mailProperties.getPort());
//
//        mailSender.setUsername(mailProperties.getUsername());
//        mailSender.setPassword(mailProperties.getPassword());
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
//
//}
