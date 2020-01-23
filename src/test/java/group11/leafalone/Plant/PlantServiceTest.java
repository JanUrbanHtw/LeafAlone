package group11.leafalone.Plant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class PlantServiceTest {

    @Autowired
    PlantService plantService;

    //calculateNextWatering
    @Test
    public void calculateWateringSetsCorrectNextWatering() {
        int daysBetweenWatering = 5;
        LocalDateTime localDateTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime localDatePlusDays = localDateTime.plusDays(daysBetweenWatering);

        PlantCare plantCare = new PlantCare.Builder().withWaterCycle(daysBetweenWatering).build();
        Plant plant = new Plant.Builder().withPlantCare(plantCare).withWatered(localDateTime).build();

        LocalDateTime result = plantService.calculateNextWatering(plant, localDateTime);

        assertEquals(localDatePlusDays, result);
    }

    @Test
    public void calculateWateringCantGoToThePast() {
        int daysBetweenWatering = -5;
        LocalDateTime localDateTime = LocalDateTime.of(2020, 1, 1, 1, 1);
        LocalDateTime localDatePlusDays = localDateTime.plusDays(daysBetweenWatering);

        PlantCare plantCare = new PlantCare.Builder().withWaterCycle(daysBetweenWatering).build();
        Plant plant = new Plant.Builder().withPlantCare(plantCare).withWatered(localDateTime).build();

        LocalDateTime result = plantService.calculateNextWatering(plant, localDateTime);

        assertNotEquals(localDatePlusDays, result);
    }

}
