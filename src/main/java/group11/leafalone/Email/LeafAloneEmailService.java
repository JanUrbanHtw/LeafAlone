package group11.leafalone.Email;

import group11.leafalone.Auth.LeafAloneUser;
import group11.leafalone.Auth.LeafAloneUserDetailsService;
import group11.leafalone.LeafAloneUtil;
import group11.leafalone.Plant.Plant;
import group11.leafalone.Plant.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Component
public class LeafAloneEmailService {

    @Autowired
    JavaMailSender mailSender;
    PlantService plantService;
    LeafAloneUserDetailsService userDetailsService;

    public LeafAloneEmailService(PlantService plantService, LeafAloneUserDetailsService userDetailsService) {
//        mailSender = getJavaMailSender();
        this.plantService = plantService;
        this.userDetailsService = userDetailsService;
    }

//    @Bean
//    public JavaMailSender getJavaMailSender() {
//
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//
//        mailSender.setUsername("lunaire426@gmail.com");
//        mailSender.setPassword("nvpblwnxrqqkmpar");
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
//    public void setJavaMailSender(JavaMailSenderImpl mailSender) {
//        this.mailSender = mailSender;
//    }

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
        if (username != null && email != null) {
            String emailText = "Hello " + username + "!" + System.lineSeparator() + "Thank you for registering on our site. We hope you enjoy your stay." + System.lineSeparator() + "Your LeafAlone team";
            sendSimpleMessage(email, "Registration Complete!", emailText);
        }
    }

    //TODO testen???
    @Scheduled(cron = "0 37 22 ? * ?")
    //@Scheduled(cron = "0 0 8 ? * ?")
    public void sendWaterReminderMail() {
        List<Plant> plantList = plantService.getPlantsToWaterTodayOrderedByUser();
        LeafAloneUser currUser = null;
        String emailText = "";
        for (Plant plant : plantList) {
            if (plant.getLeafAloneUser() != currUser) {
                //nächster User = nächste Mail
                if (currUser != null) {
                    //beende jetzige Mail
                    emailText = emailText + "need(s) to be watered today." + System.lineSeparator() + "Your LeafAlone Team";
                    //sende Mail
                    sendSimpleMessage(currUser.getEmail(), "Don't forget to water your plants!", emailText);
                }
                //setze currUser auf neuen User
                currUser = plant.getLeafAloneUser();
                //fange neue Mail an
                emailText = "Hello " + currUser.getUsername() + "!" + System.lineSeparator()
                        + "Your plant(s)" + System.lineSeparator();
            }
            //appende Mail
            emailText = emailText + plant.getName() + " species " + plant.getPlantCare().getColloquial() + System.lineSeparator();
        }
        if (plantList.size() > 0) {
            //beende jetzige Mail
            emailText = emailText + "need(s) to be watered today." + System.lineSeparator() + "Your LeafAlone Team";
            //sende Mail
            sendSimpleMessage(currUser.getEmail(), "Don't forget to water your plants!", emailText);
        }
    }

    //TODO: how do you even test this kind of stuff??
    @Scheduled(cron = "0 0 23 * * SUN")
    public void sendWaterPlanForWeek() {
        Iterable<LeafAloneUser> userList = userDetailsService.findAll();
        for(LeafAloneUser user : userList) {

            List<Plant> monday = plantService.findPlantsWateredNextDaysByUser(user, 1);
            List<Plant> tuesday = plantService.findPlantsWateredNextDaysByUser(user, 2);
            List<Plant> wednesday = plantService.findPlantsWateredNextDaysByUser(user, 3);
            List<Plant> thursday = plantService.findPlantsWateredNextDaysByUser(user, 4);
            List<Plant> friday = plantService.findPlantsWateredNextDaysByUser(user, 5);
            List<Plant> saturday = plantService.findPlantsWateredNextDaysByUser(user, 6);
            List<Plant> sunday = plantService.findPlantsWateredNextDaysByUser(user, 7);

            if(monday.isEmpty() && tuesday.isEmpty() && wednesday.isEmpty() && thursday.isEmpty()
                    && friday.isEmpty() && saturday.isEmpty() && sunday.isEmpty()) continue;

            String text = "Hello " + user.getUsername() + "!" + System.lineSeparator() +
                    "This week your watering-schedule looks as following:" + System.lineSeparator()+System.lineSeparator();

            if(monday.isEmpty()) {
                text += "Monday: None"+System.lineSeparator()+System.lineSeparator();
            } else {
                text += "Monday:"+System.lineSeparator();
                for(Plant plant : monday) {
                    text += plant.getName() + ", species " + plant.getPlantCare().getColloquial() + System.lineSeparator();
                }
                text += System.lineSeparator();
            }

            if(tuesday.isEmpty()) {
                text += "Tuesday: None"+System.lineSeparator()+System.lineSeparator();
            } else {
                text += "Tuesday:"+System.lineSeparator();
                for(Plant plant : monday) {
                    text += plant.getName() + ", species " + plant.getPlantCare().getColloquial() + System.lineSeparator();
                }
                text += System.lineSeparator();
            }

            if(wednesday.isEmpty()) {
                text += "Wednesday: None"+System.lineSeparator()+System.lineSeparator();
            } else {
                text += "Wednesday:"+System.lineSeparator();
                for(Plant plant : monday) {
                    text += plant.getName() + ", species " + plant.getPlantCare().getColloquial() + System.lineSeparator();
                }
                text += System.lineSeparator();
            }

            if(thursday.isEmpty()) {
                text += "Thursday: None"+System.lineSeparator()+System.lineSeparator();
            } else {
                text += "Thursday:"+System.lineSeparator();
                for(Plant plant : monday) {
                    text += plant.getName() + ", species " + plant.getPlantCare().getColloquial() + System.lineSeparator();
                }
                text += System.lineSeparator();
            }

            if(friday.isEmpty()) {
                text += "Friday: None"+System.lineSeparator()+System.lineSeparator();
            } else {
                text += "Friday:"+System.lineSeparator();
                for(Plant plant : monday) {
                    text += plant.getName() + ", species " + plant.getPlantCare().getColloquial() + System.lineSeparator();
                }
                text += System.lineSeparator();
            }

            if(saturday.isEmpty()) {
                text += "Saturday: None"+System.lineSeparator()+System.lineSeparator();
            } else {
                text += "Saturday:"+System.lineSeparator();
                for(Plant plant : monday) {
                    text += plant.getName() + ", species " + plant.getPlantCare().getColloquial() + System.lineSeparator();
                }
                text += System.lineSeparator();
            }

            if(sunday.isEmpty()) {
                text += "Sunday: None"+System.lineSeparator()+System.lineSeparator();
            } else {
                text += "Sunday:"+System.lineSeparator();
                for(Plant plant : monday) {
                    text += plant.getName() + ", species " + plant.getPlantCare().getColloquial() + System.lineSeparator();
                }
                text += System.lineSeparator();
            }

            text += "Sincerely," + System.lineSeparator() + "Your LeafAlone Team";
            sendSimpleMessage(user.getEmail(), "Your weekly watering-schedule arrived!", text);
        }
    }
}
