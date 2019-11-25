package group11.leafalone.PageTests;

import group11.leafalone.Pages.AddPage;
import group11.leafalone.Pages.LoginPage;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddTest extends FluentTest {

    private static final String DUMMY_DATA = "DummyData";
    private static final String DUMMY_SUN_SITUATION = "Sunny";
    private static final String DUMMY_INT = "5";
    private static final String DUMMY_TYPE = "Dummy One";
    private static final String DUMMY_DATE = "14.11.2019";

    @Page
    AddPage addPage;

    @Page
    LoginPage loginPage;

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
        goTo(addPage);
        assertThat(window().title()).isNotEqualTo(AddPage.TITLE);

        goTo(loginPage)
                .inputName("user")
                .inputPassword("password")
                .submitLoginForm();
        goTo(addPage);
        assertThat(window().title()).isEqualTo(AddPage.TITLE);

        goTo(loginPage)
                .inputName("contributor")
                .inputPassword("admin")
                .submitLoginForm();
        goTo(addPage);
        assertThat(window().title()).isEqualTo(AddPage.TITLE);
    }

    @Test
    void IsTitleCorrect() {
        loginAsUser();
        goTo(addPage);
        assertThat(window().title()).isEqualTo(AddPage.TITLE);
    }
    //TODO implementation

    @Test
    void completelyFilledForm() {
        loginAsUser();
        goTo(addPage)
                .inputPlantName(DUMMY_DATA)
                .choosePlantType(DUMMY_TYPE)
                .chooseSunSituation(DUMMY_SUN_SITUATION)
                .setAcquisitionDate(DUMMY_DATE)
                .setWateringDate(DUMMY_DATE)
                .inputNotes(DUMMY_DATA)
                .setID(DUMMY_INT)
                .submitAddPlantForm();
        //TODO implement the your plants Page
        // the title may be different
        assertThat(window().title()).isEqualTo("Your Plants");
    }

    @Test
    void noPlantName() {
        loginAsUser();
        goTo(addPage)
                .choosePlantType(DUMMY_TYPE)
                .chooseSunSituation(DUMMY_SUN_SITUATION)
                .setAcquisitionDate(DUMMY_DATE)
                .setWateringDate(DUMMY_DATE)
                .inputNotes(DUMMY_DATA)
                .setID(DUMMY_INT)
                .submitAddPlantForm()
                .assertErrorMessagePresent();
    }

    @Test
    void noPlantType() {
        loginAsUser();
        goTo(addPage)
                .inputPlantName(DUMMY_DATA)
                .chooseSunSituation(DUMMY_SUN_SITUATION)
                .setAcquisitionDate(DUMMY_DATE)
                .setWateringDate(DUMMY_DATE)
                .inputNotes(DUMMY_DATA)
                .setID(DUMMY_INT)
                .submitAddPlantForm()
                .assertErrorMessagePresent();
    }

    @Test
    void noSunSituation() {
        loginAsUser();
        goTo(addPage)
                .inputPlantName(DUMMY_DATA)
                .choosePlantType(DUMMY_TYPE)
                .setAcquisitionDate(DUMMY_DATE)
                .setWateringDate(DUMMY_DATE)
                .inputNotes(DUMMY_DATA)
                .setID(DUMMY_INT)
                .submitAddPlantForm();
        //TODO implement the your plants Page
        assertThat(window().title()).isEqualTo("Your Plants");
    }

    @Test
    void noAcquisitionDate() {
        loginAsUser();
        goTo(addPage)
                .inputPlantName(DUMMY_DATA)
                .choosePlantType(DUMMY_TYPE)
                .chooseSunSituation(DUMMY_SUN_SITUATION)
                .setWateringDate(DUMMY_DATE)
                .inputNotes(DUMMY_DATA)
                .setID(DUMMY_INT)
                .submitAddPlantForm();
        //TODO implement the your plants Page
        assertThat(window().title()).isEqualTo("Your Plants");
    }

    @Test
    void noWateringDate() {
        loginAsUser();
        goTo(addPage)
                .inputPlantName(DUMMY_DATA)
                .choosePlantType(DUMMY_TYPE)
                .chooseSunSituation(DUMMY_SUN_SITUATION)
                .setAcquisitionDate(DUMMY_DATE)
                .inputNotes(DUMMY_DATA)
                .setID(DUMMY_INT)
                .submitAddPlantForm();
        //TODO implement the your plants Page
        assertThat(window().title()).isEqualTo("Your Plants");
    }

    @Test
    void noInputNotes() {
        loginAsUser();
        goTo(addPage)
                .inputPlantName(DUMMY_DATA)
                .choosePlantType(DUMMY_TYPE)
                .chooseSunSituation(DUMMY_SUN_SITUATION)
                .setAcquisitionDate(DUMMY_DATE)
                .setWateringDate(DUMMY_DATE)
                .setID(DUMMY_INT)
                .submitAddPlantForm();
        //TODO implement the your plants Page
        assertThat(window().title()).isEqualTo("Your Plants");
    }


}
