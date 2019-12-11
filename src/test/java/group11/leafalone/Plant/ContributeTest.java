package group11.leafalone.Plant;

import group11.leafalone.LeafaloneApplication;
import group11.leafalone.Pages.ConfirmPage;
import group11.leafalone.Pages.ContributePage;
import group11.leafalone.Pages.LoginPage;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeafaloneApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ContributeTest extends FluentTest {

    @Page
    ContributePage contributePage;
    @Page
    LoginPage loginPage;

    private void loginAsContributor() {
        String username = "contributor";
        String password = "admin";

        goTo(loginPage)
                .inputName(username)
                .inputPassword(password)
                .submitLoginForm();
    }

    @Test
    void onlyAccessibleByContributor() {
        goTo(contributePage);
        assertThat(window().title()).isNotEqualTo(ContributePage.TITLE);

        goTo(loginPage)
                .inputName("user")
                .inputPassword("password")
                .submitLoginForm();
        goTo(contributePage);
        assertThat(window().title()).isNotEqualTo(ContributePage.TITLE);

        loginAsContributor();
        goTo(contributePage);
        assertThat(window().title()).isEqualTo(ContributePage.TITLE);
    }

    @Test
    void isTitleCorrect() {
        loginAsContributor();
        goTo(contributePage);
        assertThat(window().title()).isEqualTo(ContributePage.TITLE);
    }

    @Test
    void correctlyFilledForm() {
        loginAsContributor();
        goTo(contributePage)
                .inputColloquial("correctlyFilledForm")
                .inputScientific("correctlyFilledForm")
                .inputSunSituation("sunny")
                .inputWaterCycle("2")
                .inputWaterAmount("2")
                .inputSoilAdvice("SoilAdvice")
                .inputDescription("Description")
                .submitContributePlantForm();

        assertThat(window().title()).isEqualTo(ConfirmPage.TITLE);
    }


    @Test
    void formWithErrors() {
        loginAsContributor();
        goTo(contributePage)
                //No colloquial
                .inputScientific("Scientific")
                .inputSunSituation("sunny")
                .inputWaterCycle("0")
                .inputWaterAmount("0")
                .inputSoilAdvice("SoilAdvice")
                //No Description
                .submitContributePlantForm()
                .assertErrorPresent();
    }
}
