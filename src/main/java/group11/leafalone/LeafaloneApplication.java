package group11.leafalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class LeafaloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeafaloneApplication.class, args);
    }

}
