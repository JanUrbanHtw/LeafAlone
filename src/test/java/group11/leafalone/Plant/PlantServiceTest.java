package group11.leafalone.Plant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
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
        Date date = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        LocalDate localDatePlusDays = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate().plusDays(daysBetweenWatering);
        Date datePlusDays = Date.from(localDatePlusDays.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        PlantCare plantCare = new PlantCare.Builder().withWaterCycle(daysBetweenWatering).build();
        Plant plant = new Plant.Builder().withPlantCare(plantCare).withWatered(date).build();

        Date result = plantService.calculateNextWatering(plant, date);

        assertEquals(datePlusDays, result);
    }

    @Test
    public void calculateWateringCantGoToThePast() {
        int daysBetweenWatering = -5;
        Date date = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        LocalDate localDatePlusDays = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate().plusDays(daysBetweenWatering);
        Date datePlusDays = Date.from(localDatePlusDays.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        PlantCare plantCare = new PlantCare.Builder().withWaterCycle(daysBetweenWatering).build();
        Plant plant = new Plant.Builder().withPlantCare(plantCare).withWatered(date).build();

        Date result = plantService.calculateNextWatering(plant, date);

        assertNotEquals(datePlusDays, result);
    }

}
