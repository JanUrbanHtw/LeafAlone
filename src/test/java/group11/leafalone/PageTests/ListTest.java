package group11.leafalone.PageTests;

import group11.leafalone.Pages.AddPage;
import group11.leafalone.Pages.ListPage;
import group11.leafalone.Pages.LoginPage;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
    void submittedShouldBePresent() {
        String name = "name";

        loginAsUser();
        goTo(addPage)
                .inputPlantName(name)
                .choosePlantType("Dummy One")
                .chooseSunSituation("Sunny")
                .setAcquisitionDate("14.11.2019")
                .setWateringDate("14.11.2019")
                .inputNotes("notes")
                .setID("5")
                .submitAddPlantForm();

        goTo(listPage)
                .isContainedInList(name);


    }
}
