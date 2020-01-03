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
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeafaloneApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AddTest extends FluentTest {

    @Page
    AddPage addPage;
    @Page
    LoginPage loginPage;

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
        goTo(addPage);
        assertThat(window().title()).isNotEqualTo(AddPage.TITLE);

        goTo(loginPage)
                .inputName("user")
                .inputPassword("password")
                .submitLoginForm();
        goTo(addPage);
        assertThat(window().title()).isEqualTo(AddPage.TITLE);

        goTo(loginPage)
                .inputName("contributor")
                .inputPassword("admin")
                .submitLoginForm();
        goTo(addPage);
        assertThat(window().title()).isEqualTo(AddPage.TITLE);
    }

    @Test
    void isTitleCorrect() {
        loginAsUser();
        goTo(addPage);
        assertThat(window().title()).isEqualTo(AddPage.TITLE);
    }
    //TODO implementation

    @Test
    void completelyFilledForm() {
        loginAsUser();
        goTo(addPage)
                .inputPlantName("PlantName")
                .choosePlantType("Jade Plant")
                .chooseSunSituation("sunny")
                .setAcquisitionDate("2019-01-01T00:00:00.000Z")
                .setWateringDate("2019-01-01T00:00:00.000Z")
                .inputNotes("Notes")
                .submitAddPlantForm();
        assertThat(window().title()).isEqualTo(PlantListPage.TITLE);
    }

    @Test
    void formWithErrors() {
        loginAsUser();
        goTo(addPage)
                //no plantName
                .choosePlantType("Jade Plant")
                .chooseSunSituation("sunny")
                .setAcquisitionDate("2019-01-01T00:00:00.000Z")
                .setWateringDate("2019-01-01T00:00:00.000Z")
                .inputNotes("Notes")
                .submitAddPlantForm()
                .assertErrorMessagePresent();
    }
}
