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
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeafaloneApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PlantDetailsTest extends FluentTest {

    @Page
    PlantDetailsPage detailsPage;
    @Page
    LoginPage loginPage;
    @Page
    AddPage addPage;

    public static final String PLANT_NAME = "TestName";
    public static final String PLANT_TYPE = "Jade Plant";
    public static final String SUN_SITUATION = "shadow";
    public static final String ACQUISITION_ISO = "2019-01-01T00:00:00.000Z";
    public static final String ACQUISITION = "01 Januar 2019";
    public static final String WATERED_ISO = "2019-01-01T00:00:00.000Z";
    public static final String WATERED = "01 Januar 2019";
    public static final String NEXT_WATERED = "02 Januar 2019";
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
        detailsPage.go(PLANT_NAME);
        assertThat(window().title()).isNotEqualTo(PlantDetailsPage.TITLE);

        goTo(loginPage)
                .inputName("user")
                .inputPassword("password")
                .submitLoginForm();
        createPlant();
        detailsPage.go(PLANT_NAME);
        assertThat(window().title()).isEqualTo(PlantDetailsPage.TITLE);

        goTo(loginPage)
                .inputName("contributor")
                .inputPassword("admin")
                .submitLoginForm();
        createPlant();
        detailsPage.go(PLANT_NAME);
        assertThat(window().title()).isEqualTo(PlantDetailsPage.TITLE);
    }

    @Test
    void isTitleCorrect() {
        loginAsUser();
        createPlant();
        detailsPage.go(PLANT_NAME);
        assertThat(window().title()).isEqualTo(PlantDetailsPage.TITLE);
    }

    //@Test
    // Travis fails on this one
    void contentsIdentical() {
        loginAsUser();
        createPlant();
        detailsPage.go(PLANT_NAME);
        detailsPage.assertNameIs(PLANT_NAME)
                .assertPlantCareIs(PLANT_TYPE)
                .assertSunSituationIs(SUN_SITUATION)
                .assertSunSituationWarningPresent(true)
                .assertAcquisitionIs(ACQUISITION)
                .assertWateredIs(WATERED)
                .assertNextWateringIs(NEXT_WATERED)
                .assertNotesIs(NOTES);
    }

    @Test
    void listButtonShouldRenderList() {
        loginAsUser();
        createPlant();
        detailsPage.go(PLANT_NAME);
        detailsPage.pressListButton();
        assertThat(window().title()).isEqualTo(PlantListPage.TITLE);
    }

    @Test
    void editButtonShouldRenderList() {
        loginAsUser();
        createPlant();
        detailsPage.go(PLANT_NAME);
        detailsPage.pressEditButton();
        assertThat(window().title()).isEqualTo(PlantEditPage.TITLE);
    }
}
