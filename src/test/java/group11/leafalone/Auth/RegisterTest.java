package group11.leafalone.Auth;

import group11.leafalone.LeafaloneApplication;
import group11.leafalone.Pages.NavbarHelper;
import group11.leafalone.Pages.RegisterPage;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeafaloneApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RegisterTest extends FluentTest {

    @Page
    RegisterPage registerPage;

    @Test
    void isTitleCorrect() {
        goTo(registerPage);
        assertThat(window().title()).isEqualTo(RegisterPage.TITLE);
    }

    //TODO make it pass & comment in
    //problem: probably something related to email (sending)
//    @Test
//    void registerAsUser() {
//        goTo(registerPage)
//                .inputUsername("testUser")
//                .setRole("Standard User")
//                .inputEmail("melonlord@posteo.de")
//                .inputPassword("testPassword")
//                .inputConfirmPassword("testPassword")
//                .submit();
//        assertThat($(NavbarHelper.PLANT_LINK).present()).isTrue();
//        assertThat($(NavbarHelper.CONTRIBUTE_LINK).present()).isFalse();
//    }

    //TODO make it pass & comment in
    //problem: probably something related to email (sending)
//    @Test
//    void registerAsContributor() {
//        goTo(registerPage)
//                .inputUsername("testContributor")
//                .setRole("Contributor")
//                .inputEmail("leafalone@mail.de")
//                .inputPassword("testPassword")
//                .inputConfirmPassword("testPassword")
//                .submit();
//        assertThat($(NavbarHelper.CONTRIBUTE_LINK).present()).isTrue();
//    }

    @Test
    void notMatchingPassword() {
        goTo(registerPage)
                .inputUsername("testUser2")
                .setRole("Standard User")
                .inputEmail("leafalone@mail.de")
                .inputPassword("1")
                .inputConfirmPassword("2")
                .submit()
                .assertErrorMessagePresent();
    }
}
