package group11.leafalone;

import group11.leafalone.Pages.ContributePage;
import group11.leafalone.Pages.LoginPage;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContributeTest extends FluentTest {

    private static final String DUMMY_DATA = "DummyData";
    private static final String DUMMY_SUN_SITUATION = "sunny";
    private static final String DUMMY_INT = "5";

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
    void IsTitleCorrect() {
        loginAsContributor();
        goTo(contributePage);
        assertThat(window().title()).isEqualTo(ContributePage.TITLE);
    }

    @Test
    void correctlyFilledForm() {
        loginAsContributor();
        goTo(contributePage)
                .inputColloquial(DUMMY_DATA)
                .inputScientific(DUMMY_DATA)
                .inputSunSituation(DUMMY_SUN_SITUATION)
                .inputWaterCycle(DUMMY_INT)
                .inputWaterAmount(DUMMY_INT)
                .inputSoilAdvice(DUMMY_DATA)
                .inputDescription(DUMMY_DATA)
                .submitContributePlantForm();

        //TODO implementation of Confirmation Element
        // changing the element name is permitted

        assertThat($("#confirmation").present()).isTrue();
    }

    @Test
    void noColloquial() {
        loginAsContributor();
        goTo(contributePage)
                .inputScientific(DUMMY_DATA)
                .inputSunSituation(DUMMY_SUN_SITUATION)
                .inputWaterCycle(DUMMY_INT)
                .inputWaterAmount(DUMMY_INT)
                .inputSoilAdvice(DUMMY_DATA)
                .inputDescription(DUMMY_DATA)
                .submitContributePlantForm()
                .assertErrorPresent();
    }

    @Test
    void noScientific() {
        loginAsContributor();
        goTo(contributePage)
                .inputColloquial(DUMMY_DATA)
                .inputSunSituation(DUMMY_SUN_SITUATION)
                .inputWaterCycle(DUMMY_INT)
                .inputWaterAmount(DUMMY_INT)
                .inputSoilAdvice(DUMMY_DATA)
                .inputDescription(DUMMY_DATA)
                .submitContributePlantForm()
                .assertErrorPresent();
    }

    @Test
    void noSunSituation() {
        loginAsContributor();
        goTo(contributePage)
                .inputColloquial(DUMMY_DATA)
                .inputScientific(DUMMY_DATA)
                .inputWaterCycle(DUMMY_INT)
                .inputWaterAmount(DUMMY_INT)
                .inputSoilAdvice(DUMMY_DATA)
                .inputDescription(DUMMY_DATA)
                .submitContributePlantForm()
                .assertErrorPresent();
    }

    @Test
    void noWaterCycle() {
        loginAsContributor();
        goTo(contributePage)
                .inputColloquial(DUMMY_DATA)
                .inputScientific(DUMMY_DATA)
                .inputSunSituation(DUMMY_SUN_SITUATION)
                .inputWaterAmount(DUMMY_INT)
                .inputSoilAdvice(DUMMY_DATA)
                .inputDescription(DUMMY_DATA)
                .submitContributePlantForm()
                .assertErrorPresent();
    }

    @Test
    void noWaterAmount() {
        loginAsContributor();
        goTo(contributePage)
                .inputColloquial(DUMMY_DATA)
                .inputScientific(DUMMY_DATA)
                .inputSunSituation(DUMMY_SUN_SITUATION)
                .inputWaterCycle(DUMMY_INT)
                .inputSoilAdvice(DUMMY_DATA)
                .inputDescription(DUMMY_DATA)
                .submitContributePlantForm()
                .assertErrorPresent();
    }

    @Test
    void noSoilAdvice() {
        loginAsContributor();
        goTo(contributePage)
                .inputColloquial(DUMMY_DATA)
                .inputScientific(DUMMY_DATA)
                .inputSunSituation(DUMMY_SUN_SITUATION)
                .inputWaterCycle(DUMMY_INT)
                .inputWaterAmount(DUMMY_INT)
                .inputDescription(DUMMY_DATA)
                .submitContributePlantForm()
                .assertErrorPresent();
    }

    @Test
    void noDescription() {
        loginAsContributor();
        goTo(contributePage)
                .inputColloquial(DUMMY_DATA)
                .inputScientific(DUMMY_DATA)
                .inputSunSituation(DUMMY_SUN_SITUATION)
                .inputWaterCycle(DUMMY_INT)
                .inputWaterAmount(DUMMY_INT)
                .inputSoilAdvice(DUMMY_DATA)
                .submitContributePlantForm()
                .assertErrorPresent();
    }
}
