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
import java.util.Map;
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

    public void sendSimpleMessage(String to, String subject, String text) {
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

    @Scheduled(cron = "0 0 8 ? * ?")
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

            Map<String, List> lists = plantService.findPlantsWateredNextWeekByUser(user);
            List<Plant> beforeWeek = plantService.findPlantsWateredBeforeToday(user);
            if(lists.get("Monday").isEmpty() && lists.get("Tuesday").isEmpty() && lists.get("Wednesday").isEmpty() && lists.get("Thursday").isEmpty()
                    && lists.get("Friday").isEmpty() && lists.get("Saturday").isEmpty() && lists.get("Sunday").isEmpty() && beforeWeek.isEmpty()) continue;

            String text = "Hello " + user.getUsername() + "!" + System.lineSeparator() +
                    "This week your watering-schedule looks as following:" + System.lineSeparator()+System.lineSeparator();

            for(Map.Entry<String, List> entry : lists.entrySet()) {
                text = computeWeeklyEmailText(entry.getValue(), entry.getKey(), text);
            }

            if(!beforeWeek.isEmpty()) {
                text+= "The following plants' watering is overdue. If they are still alive, you should water them too next time: " + System.lineSeparator();
                for (Plant plant : beforeWeek) {
                    text += plant.getName() + ", species " + plant.getPlantCare().getColloquial() + System.lineSeparator();
                }
                text += System.lineSeparator();
            }

            text += "Sincerely," + System.lineSeparator() + "Your LeafAlone Team";
            sendSimpleMessage(user.getEmail(), "Your weekly watering-schedule arrived!", text);
        }
    }

    private String computeWeeklyEmailText(List<Plant> list, String day, String text) {
        if(list.isEmpty()) {
            text += day + ": None"+System.lineSeparator()+System.lineSeparator();
        } else {
            text += day + ":"+System.lineSeparator();
            for(Plant plant : list) {
                text += plant.getName() + ", species " + plant.getPlantCare().getColloquial() + System.lineSeparator();
            }
            text += System.lineSeparator();
        }
        return text;
    }
}
