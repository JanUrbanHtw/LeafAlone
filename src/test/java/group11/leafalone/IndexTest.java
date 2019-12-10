package group11.leafalone;

import group11.leafalone.Pages.IndexPage;
import group11.leafalone.Pages.LoginPage;
import group11.leafalone.Pages.NavbarHelper;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeafaloneApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class IndexTest extends FluentTest {

    @Page
    IndexPage indexPage;
    @Page
    LoginPage loginPage;

    @Test
    void everythingPresent() {
        goTo(indexPage);
        assertThat($(IndexPage.WELCOME).present()).isTrue();
        assertThat(window().title()).isEqualTo(IndexPage.TITLE);
    }


    // -----------------------------------------------------------------------
    // ----------------------- Tests for Navbar ------------------------------
    // -----------------------------------------------------------------------


    //TODO update every time the navbar changes
    @Test
    void LinksCorrect() {
        //before login
        goTo(indexPage)
                .assertIsPresent(NavbarHelper.HOME_LINK)
                .assertIsPresent(NavbarHelper.ABOUT_LINK)
                .assertIsPresent(NavbarHelper.REGISTER_LINK)
                .assertIsPresent(NavbarHelper.LOGIN_LINK)
                .assertNotPresent(NavbarHelper.LIST_LINK)
                .assertNotPresent(NavbarHelper.PLANT_LINK)
                .assertNotPresent(NavbarHelper.CONTRIBUTE_LINK)
                .assertNotPresent(NavbarHelper.LOGOUT_LINK);

        //login as user

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
                .assertNotPresent(NavbarHelper.REGISTER_LINK)
                .assertNotPresent(NavbarHelper.LOGIN_LINK);
        $(NavbarHelper.LOGOUT_LINK).click();

        //login as contributor

        username = "contributor";
        password = "admin";

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
                .assertNotPresent(NavbarHelper.REGISTER_LINK)
                .assertIsPresent(NavbarHelper.LIST_LINK)
                .assertNotPresent(NavbarHelper.LOGIN_LINK);
    }
}
