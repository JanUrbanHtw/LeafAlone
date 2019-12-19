package group11.leafalone.Plant;

import group11.leafalone.LeafaloneApplication;
import group11.leafalone.Pages.AddPage;
import group11.leafalone.Pages.ListPage;
import group11.leafalone.Pages.LoginPage;
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
class ListTest extends FluentTest {

    @Page
    ListPage listPage;
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
    void AccessibleByUserAndContributor() {
        goTo(listPage);
        assertThat(window().title()).isNotEqualTo(ListPage.TITLE);

        goTo(loginPage)
                .inputName("user")
                .inputPassword("password")
                .submitLoginForm();
        goTo(listPage);
        assertThat(window().title()).isEqualTo(ListPage.TITLE);

        goTo(loginPage)
                .inputName("contributor")
                .inputPassword("admin")
                .submitLoginForm();
        goTo(listPage);
        assertThat(window().title()).isEqualTo(ListPage.TITLE);
    }

    @Test
    void IsTitleCorrect() {
        loginAsUser();
        goTo(listPage);
        assertThat(window().title()).isEqualTo(ListPage.TITLE);
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

        goTo(listPage)
                .isContainedInList(name);
    }

    @Test
    void addPlantLinkShouldBePresentAndRedirect() {
        loginAsUser();
        goTo(listPage)
                .clickPlantLink();
        assertThat(window().title()).isEqualTo(AddPage.TITLE);
    }
}
