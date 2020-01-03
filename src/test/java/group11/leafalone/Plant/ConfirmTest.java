package group11.leafalone.Plant;

import group11.leafalone.LeafaloneApplication;
import group11.leafalone.Pages.IndexPage;
import group11.leafalone.Pages.LoginPage;
import group11.leafalone.Pages.plant_types.ConfirmPage;
import group11.leafalone.Pages.plant_types.ContributePage;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeafaloneApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ConfirmTest extends FluentTest {

    @Page
    ConfirmPage confirmPage;
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
    public void confirmRevert() {
        String colloquial = "Colloquial2";
        String scientific = "Scientific2";
        String sun = "sunny";
        String waterCycle = "2";
        String waterAmount = "2";
        String soilAdvice = "soilAdvice";
        String description = "description";

        loginAsContributor();

        goTo(contributePage)
                .inputColloquial(colloquial)
                .inputScientific(scientific)
                .inputSunSituation(sun)
                .inputWaterCycle(waterCycle)
                .inputWaterAmount(waterAmount)
                .inputSoilAdvice(soilAdvice)
                .inputDescription(description)
                .submitContributePlantForm();
        confirmPage
                .assertIsColloquial(colloquial)
                .assertIsScientific(scientific)
                .assertIsSun(sun)
                .assertIsWaterCycle(waterCycle)
                .assertIsWaterAmount(waterAmount)
                .assertIsSoilAdvice(soilAdvice)
                .assertIsDescription(description)
                .revert();
        assertThat(window().title()).isEqualTo(ContributePage.TITLE);
    }

    @Test
    public void confirmOk() {
        String colloquial = "Colloquial";
        String scientific = "Scientific";
        String sun = "sunny";
        String waterCycle = "2";
        String waterAmount = "2";
        String soilAdvice = "soilAdvice";
        String description = "description";

        loginAsContributor();

        goTo(contributePage)
                .inputColloquial(colloquial)
                .inputScientific(scientific)
                .inputSunSituation(sun)
                .inputWaterCycle(waterCycle)
                .inputWaterAmount(waterAmount)
                .inputSoilAdvice(soilAdvice)
                .inputDescription(description)
                .submitContributePlantForm();
        confirmPage
                .assertIsColloquial(colloquial)
                .assertIsScientific(scientific)
                .assertIsSun(sun)
                .assertIsWaterCycle(waterCycle)
                .assertIsWaterAmount(waterAmount)
                .assertIsSoilAdvice(soilAdvice)
                .assertIsDescription(description)
                .ok();
        assertThat(window().title()).isEqualTo(IndexPage.TITLE);
    }
}
