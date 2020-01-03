package group11.leafalone.Auth;

import group11.leafalone.LeafaloneApplication;
import group11.leafalone.Pages.IndexPage;
import group11.leafalone.Pages.LoginPage;
import group11.leafalone.Pages.NavbarHelper;
import group11.leafalone.Pages.plant_types.ContributePage;
import group11.leafalone.Pages.plants.AddPage;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeafaloneApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LoginTest extends FluentTest {

    @Page
    private LoginPage loginPage;

    @Test
    void isTitleCorrect() {
        goTo(loginPage);
        assertThat(window().title()).isEqualTo(LoginPage.TITLE);
    }

    @Test
    void userCanLogin() {
        String username = "user";
        String password = "password";

        goTo(loginPage)
                .inputName(username)
                .inputPassword(password)
                .submitLoginForm()
                .assertErrorMessageNotPresent()
                .assertLogoutMessageNotPresent()
                .assertTitleEquals(IndexPage.TITLE)
                .clickOnElement(NavbarHelper.PLANT_LINK)
                .assertTitleEquals(AddPage.TITLE);

    }

    @Test
    void contributorCanLogin() {
        String username = "contributor";
        String password = "admin";

        goTo(loginPage)
                .inputName(username)
                .inputPassword(password)
                .submitLoginForm()
                .assertErrorMessageNotPresent()
                .assertLogoutMessageNotPresent()
                .assertTitleEquals(IndexPage.TITLE)
                .clickOnElement(NavbarHelper.CONTRIBUTE_LINK)
                .assertTitleEquals(ContributePage.TITLE);
    }

    @Test
    void notFilledCorrectly() {
        String username = "";
        String password = "";
        goTo(loginPage)
                .inputName(username)
                .inputPassword(password)
                .submitLoginForm()
                .assertErrorMessagePresent()
                .assertLogoutMessageNotPresent();
    }
}
