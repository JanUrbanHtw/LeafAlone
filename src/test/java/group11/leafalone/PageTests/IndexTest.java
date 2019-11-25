package group11.leafalone.PageTests;

import group11.leafalone.Pages.IndexPage;
import group11.leafalone.Pages.LoginPage;
import group11.leafalone.Pages.NavbarHelper;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.hook.wait.Wait;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Wait
class IndexTest extends FluentTest {

    @Page
    IndexPage indexPage;

    @Page
    LoginPage loginPage;

    @Test
    void IsTitleCorrect() {
        goTo(indexPage);
        assertThat(window().title()).isEqualTo(IndexPage.TITLE);
    }

    @Test
    void WelcomeTextPresent() {
        goTo(indexPage)
                .assertIsPresent(IndexPage.WELCOME);
    }

    // -----------------------------------------------------------------------
    // ----------------------- Tests for Navbar ------------------------------
    // -----------------------------------------------------------------------


    @Test
    void LinksBeforeLogin() {
        goTo(indexPage)
                .assertIsPresent(NavbarHelper.HOME_LINK)
                .assertIsPresent(NavbarHelper.ABOUT_LINK)
                .assertIsPresent(NavbarHelper.LOGIN_LINK)
                .assertNotPresent(NavbarHelper.LIST_LINK)
                .assertNotPresent(NavbarHelper.PLANT_LINK)
                .assertNotPresent(NavbarHelper.CONTRIBUTE_LINK)
                .assertNotPresent(NavbarHelper.LOGOUT_LINK);
    }

    @Test
    void LinksAsUser() {
        String username = "user";
        String password = "password";

        goTo(loginPage)
                .inputName(username)
                .inputPassword(password)
                .submitLoginForm();

        goTo(indexPage)
                .assertIsPresent(NavbarHelper.HOME_LINK)
                .assertIsPresent(NavbarHelper.ABOUT_LINK)
                .assertIsPresent(NavbarHelper.PLANT_LINK)
                .assertIsPresent(NavbarHelper.LIST_LINK)
                .assertIsPresent(NavbarHelper.LOGOUT_LINK)
                .assertNotPresent(NavbarHelper.CONTRIBUTE_LINK)
                .assertNotPresent(NavbarHelper.LOGIN_LINK);
    }

    @Test
    void LinksAsContributor() {
        String username = "contributor";
        String password = "admin";

        goTo(loginPage)
                .inputName(username)
                .inputPassword(password)
                .submitLoginForm();

        goTo(indexPage)
                .assertIsPresent(NavbarHelper.HOME_LINK)
                .assertIsPresent(NavbarHelper.ABOUT_LINK)
                .assertIsPresent(NavbarHelper.PLANT_LINK)
                .assertIsPresent(NavbarHelper.CONTRIBUTE_LINK)
                .assertIsPresent(NavbarHelper.LOGOUT_LINK)
                .assertIsPresent(NavbarHelper.LIST_LINK)
                .assertNotPresent(NavbarHelper.LOGIN_LINK);
    }
}
