package group11.leafalone.Plant;


import group11.leafalone.LeafaloneApplication;
import group11.leafalone.Pages.LoginPage;
import group11.leafalone.Pages.plants.AddPage;
import group11.leafalone.Pages.plants.PlantDetailsPage;
import group11.leafalone.Pages.plants.PlantEditPage;
import group11.leafalone.Pages.plants.PlantListPage;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeafaloneApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PlantEditTest extends FluentTest {

    @Page
    PlantEditPage editPage;
    @Page
    LoginPage loginPage;
    @Page
    AddPage addPage;

    public static final String PLANT_NAME = "TestName";
    public static final String PLANT_TYPE = "Jade Plant";
    public static final String SUN_SITUATION = "sunny";
    public static final String ACQUISITION_ISO = "2019-01-01T00:00:00.000Z";
    public static final String ACQUISITION = "2019-01-01";
    public static final String WATERED_ISO = "2019-01-01T00:00:00.000Z";
    public static final String WATERED = "2019-01-01";
    public static final String NOTES = "Notes";

    private void loginAsUser() {
        String username = "user";
        String password = "password";

        goTo(loginPage)
                .inputName(username)
                .inputPassword(password)
                .submitLoginForm();
    }

    private void createPlant() {
        goTo(addPage)
                .inputPlantName(PLANT_NAME)
                .choosePlantType(PLANT_TYPE)
                .chooseSunSituation(SUN_SITUATION)
                .setAcquisitionDate(ACQUISITION_ISO)
                .setWateringDate(WATERED_ISO)
                .inputNotes(NOTES)
                .submitAddPlantForm();
    }

    @Test
    void accessibleByUserAndContributor() {
        editPage.go(PLANT_NAME);
        assertThat(window().title()).isNotEqualTo(PlantEditPage.TITLE);

        goTo(loginPage)
                .inputName("user")
                .inputPassword("password")
                .submitLoginForm();
        createPlant();
        editPage.go(PLANT_NAME);
        assertThat(window().title()).isEqualTo(PlantEditPage.TITLE);

        goTo(loginPage)
                .inputName("contributor")
                .inputPassword("admin")
                .submitLoginForm();
        createPlant();
        editPage.go(PLANT_NAME);
        assertThat(window().title()).isEqualTo(PlantEditPage.TITLE);
    }

    @Test
    void isTitleCorrect() {
        loginAsUser();
        createPlant();
        editPage.go(PLANT_NAME);
        assertThat(window().title()).isEqualTo(PlantEditPage.TITLE);
    }

    @Test
    void contentsIdentical() {
        loginAsUser();
        createPlant();
        editPage.go(PLANT_NAME);
        editPage.assertPlantName(PLANT_NAME)
                .assertPlantTypeIs(PLANT_TYPE)
                .assertSunSituationIs(SUN_SITUATION)
                .assertPlantAcquisitionIs(ACQUISITION)
                .assertPlantWateredIs(WATERED)
                .assertPlantNotesIs(NOTES);
    }

    @Test
    @DirtiesContext
    void correctChangeShouldRenderDetails() {
        loginAsUser();
        createPlant();
        editPage.go(PLANT_NAME);
        editPage.choosePlantType("Bunny-Ears Cactus")
                .chooseSunSituation("shadow")
                .inputPlantAcquisition("2019-01-03T00:00:00.000Z")
                .inputPlantWatered("2019-04-04T00:00:00.000Z")
                .inputNotes("NewNotes")
                .submitPlantEditForm()
                .assertErrorMessage(false);
        assertThat(window().title()).isEqualTo(PlantDetailsPage.TITLE);
    }

    @Test
    void invalidChangeShouldDisplayErrorMessage() {
        loginAsUser();
        createPlant();
        editPage.go(PLANT_NAME);
        editPage.inputPlantAcquisition("2019-04-04T00:00:00.000Z")
                .inputPlantWatered("2019-01-01T00:00:00.000Z")
                .submitPlantEditForm()
                .assertErrorMessage(true);
    }

    @Test
    void deletePlantShouldRenderConfirmAlertAndRedirectToList() {
        loginAsUser();
        createPlant();
        editPage.go(PLANT_NAME);
        editPage.deletePlant()
                .alertPresent(true)
                .acceptAlert();
        assertThat(window().title()).isEqualTo(PlantListPage.TITLE);
    }
}
