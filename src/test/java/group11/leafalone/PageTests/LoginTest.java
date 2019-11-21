package group11.leafalone.PageTests;

import group11.leafalone.Pages.*;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends FluentTest {

    @Page
    private LoginPage loginPage;

    @Test
    void IsTitleCorrect() {
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
    void noUserName() {
        String username = "";
        String password = "password";

        goTo(loginPage)
                .inputName(username)
                .inputPassword(password)
                .submitLoginForm()
                .assertErrorMessagePresent()
                .assertLogoutMessageNotPresent();
    }

    @Test
    void noPassword() {
        String username = "user";
        String password = "";

        goTo(loginPage)
                .inputName(username)
                .inputPassword(password)
                .submitLoginForm()
                .assertErrorMessagePresent()
                .assertLogoutMessageNotPresent();
    }

    @Test
    void noUserNameNoPassword() {
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
