package group11.leafalone.Plant;

import group11.leafalone.LeafaloneApplication;
import group11.leafalone.Pages.LoginPage;
import group11.leafalone.Pages.plants.AddPage;
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
class PlantListTest extends FluentTest {

    @Page
    PlantListPage plantListPage;
    @Page
    LoginPage loginPage;
    @Page
    AddPage addPage;

    private void loginAsUser() {
        String username = "user";
        String password = "password";

        goTo(loginPage)
                .inputName(username)
                .inputPassword(password)
                .submitLoginForm();
    }

    @Test
    void accessibleByUserAndContributor() {
        goTo(plantListPage);
        assertThat(window().title()).isNotEqualTo(PlantListPage.TITLE);

        goTo(loginPage)
                .inputName("user")
                .inputPassword("password")
                .submitLoginForm();
        goTo(plantListPage);
        assertThat(window().title()).isEqualTo(PlantListPage.TITLE);

        goTo(loginPage)
                .inputName("contributor")
                .inputPassword("admin")
                .submitLoginForm();
        goTo(plantListPage);
        assertThat(window().title()).isEqualTo(PlantListPage.TITLE);
    }

    @Test
    void isTitleCorrect() {
        loginAsUser();
        goTo(plantListPage);
        assertThat(window().title()).isEqualTo(PlantListPage.TITLE);
    }

    @Test
    @DirtiesContext
    void submittedShouldBePresent() {
        String name = "name";

        loginAsUser();
        goTo(addPage)
                .inputPlantName(name)
                .choosePlantType("Jade Plant")
                .chooseSunSituation("sunny")
                .setAcquisitionDate("2019-01-01T00:00:00.000Z")
                .setWateringDate("2019-01-01T00:00:00.000Z")
                .inputNotes("notes")
                .submitAddPlantForm();

        goTo(plantListPage)
                .isContainedInList(name);
    }

    @Test
    void addPlantLinkShouldBePresentAndRedirect() {
        loginAsUser();
        goTo(plantListPage)
                .clickPlantLink();
        assertThat(window().title()).isEqualTo(AddPage.TITLE);
    }
}
