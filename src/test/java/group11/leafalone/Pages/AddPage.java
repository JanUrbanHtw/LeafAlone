package group11.leafalone.Pages;


import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withText;


@PageUrl("http://localhost:8080/plants/add")
public class AddPage extends FluentPage {
    public static final String TITLE = "Add a personal plant!";
    private static final String ERROR_MESSAGE = "#errorMessage";

    @FindBy(id = "type")
    private FluentWebElement plantNameInput;

    @FindBy(id = "plantType")
    private FluentWebElement plantTypeInput;

    @FindBy(id = "sun")
    private FluentWebElement sunSituationInput;

    @FindBy(id = "acquisition")
    private FluentWebElement acquisitionDateInput;

    @FindBy(id = "watered")
    private FluentWebElement wateringDateInput;

    @FindBy(id = "notes")
    private FluentWebElement additionalNotesInput;

    @FindBy(id = "id")
    private FluentWebElement idInput;

    @FindBy(id = "submitButton")
    private FluentWebElement submitButton;

    public AddPage inputPlantName(String plantName) {
        plantNameInput.write(plantName);
        return this;
    }

    public AddPage choosePlantType(String plantType) {
        plantTypeInput.find("option", withText(plantType)).click();
        return this;
    }

    public AddPage chooseSunSituation(String sunSituation) {
        sunSituationInput.find("option", withText(sunSituation)).click();
        return this;
    }

    public AddPage setAcquisitionDate(String acquisitionDate) {
        acquisitionDateInput.write(acquisitionDate);
        return this;
    }

    public AddPage setWateringDate(String wateringDate) {
        wateringDateInput.write(wateringDate);
        return this;
    }

    public AddPage inputNotes(String notes) {
        additionalNotesInput.write(notes);
        return this;
    }

    public AddPage setID(String id) {
        idInput.write(id);
        return this;
    }

    public AddPage submitAddPlantForm() {
        submitButton.submit();
        await().atMost(5, TimeUnit.SECONDS).untilPage().isLoaded();
        return this;
    }

    public AddPage assertErrorMessagePresent() {
        assertThat($(ERROR_MESSAGE).present()).isTrue();
        return this;
    }
}
