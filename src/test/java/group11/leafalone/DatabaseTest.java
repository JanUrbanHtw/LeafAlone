package group11.leafalone;

import group11.leafalone.Auth.LeafAloneUser;
import group11.leafalone.Auth.UserRepository;
import group11.leafalone.Plant.*;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeafaloneApplication.class)
class DatabaseTest {

    @Autowired
    private PlantCareRepository plantCareRepository;
    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    private void clearTables() {
        plantCareRepository.deleteAll();
        plantRepository.deleteAll();
        //userRepository.deleteAll();
    }

    //before everything: start application

    void startingApplicationShouldCreatePlantCareTable() {
        //start application? (mock)
        //test if table is there?
    }

    void startingApplicationShouldCreatePlantTable() {

    }

    void startingApplicationShouldCreateUserTable() {

    }

    @Test
    void userShouldExist() {
        LeafAloneUser user = userRepository.findByUsername("user");
        assertNotNull(user);
    }

    @Test
    void contributorShouldExist() {
        LeafAloneUser user = userRepository.findByUsername("contributor");
        assertNotNull(user);
    }

    @Test
    void submittingPlantCareFormShouldCreateDatabaseEntry() {
        PlantCare savedPlantCare = plantCareRepository.save(new PlantCare("Dummy", "Duminitus Testitus",
                SunSituation.SUNNY, 5, 5, "soil", "Test", "Paul"));
        Optional<PlantCare> retrievedPlantCare = plantCareRepository.findById(savedPlantCare.getId());

        assertTrue(retrievedPlantCare.isPresent());
        assertEquals(savedPlantCare.getId(), retrievedPlantCare.get().getId());
    }

    // Doesn't work because MockObjects are not permitted and not possible to crate invalid Plantcare
    //@Test
//    void submittingPlantCareFormWithoutValidInfoShouldFail() {
//        PlantCare mock = Mockito.mock(PlantCare.class);
//        PlantCare savedPlantCare = plantCareRepository.save(mock);
//        Optional<PlantCare> retrievedPlantCare = plantCareRepository.findById(savedPlantCare.getId());
//
//        assertFalse(retrievedPlantCare.isPresent());
//        //assertEquals(savedPlantCare.getId(), retrievedPlantCare.get().getId());
//
//    }

    @Test
    void submittingPlantFormShouldCreateDatabaseEntry() {
        UserPlant savedPlant = plantRepository.save(new UserPlant("Dummy", "Dummy", SunSituation.SUNNY,
                null, null, null, null));
        Optional<UserPlant> retrievedPlant = plantRepository.findById(savedPlant.getId());

        assertTrue(retrievedPlant.isPresent());
        assertEquals(savedPlant.getId(), retrievedPlant.get().getId());
    }

    // doesn't work see: submittingPlantCareFormWithoutValidInfoShouldFail()
//    void submittingPlantFormWithoutValidInfoShouldFail() {
//
//    }

    @Test
    void submittedPlantShouldHaveUniqueName() {
        UserPlant plant = new UserPlant("Dummy", "Dummy", SunSituation.SUNNY,
                null, null, null, null);

        UserPlant plant2 = new UserPlant("Dummy", "Dummy", SunSituation.SUNNY,
                null, null, null, null);
        plantRepository.save(plant);
        assertThrows(DataIntegrityViolationException.class, () -> plantRepository.save(plant2));

        Optional<UserPlant> retrievedPlant = plantRepository.findById(plant.getId());
        Optional<UserPlant> retrievedPlant2 = plantRepository.findById(plant2.getId());

        assertTrue(retrievedPlant.isPresent());
        assertFalse(retrievedPlant2.isPresent());

    }

    @Test
    void submittedPlantCareShouldHaveUniqueScientificName() {
        PlantCare plantCare = new PlantCare("Dummy", "Duminitus Testitus",
                SunSituation.SUNNY, 5, 5, "soil", "Test", "Paul");
        PlantCare plantCare2 = new PlantCare("Dummy2", "Duminitus Testitus",
                SunSituation.SUNNY, 5, 5, "soil", "Test", "Paul");
        plantCareRepository.save(plantCare);
        assertThrows(DataIntegrityViolationException.class, () -> plantCareRepository.save(plantCare2));

        Optional<PlantCare> retrievedPlantCare = plantCareRepository.findById(plantCare.getId());
        Optional<PlantCare> retrievedPlantCare2 = plantCareRepository.findById(plantCare2.getId());

        assertTrue(retrievedPlantCare.isPresent());
        assertFalse(retrievedPlantCare2.isPresent());

    }
}
