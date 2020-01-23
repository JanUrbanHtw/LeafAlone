package group11.leafalone.Pages.plants;


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

    @FindBy(className = "errorMessage")
    private FluentWebElement errorMessage;

    @FindBy(id = "nameInput")
    private FluentWebElement plantNameInput;

    @FindBy(id = "plantCare")
    private FluentWebElement plantTypeInput;

    @FindBy(id = "sun")
    private FluentWebElement sunSituationInput;

    @FindBy(id = "acquisitionDate")
    private FluentWebElement acquisitionDateInput;

    @FindBy(id = "wateredDate")
    private FluentWebElement wateringDateInput;

    @FindBy(id = "notes")
    private FluentWebElement additionalNotesInput;

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

    //2019-01-01T00:00:00.000Z
    public AddPage setAcquisitionDate(String acquisitionDate) {
        acquisitionDateInput.write(acquisitionDate);
        return this;
    }

    //2019-01-01T00:00:00.000Z
    public AddPage setWateringDate(String wateringDate) {
        wateringDateInput.write(wateringDate);
        return this;
    }

    public AddPage inputNotes(String notes) {
        additionalNotesInput.write(notes);
        return this;
    }

    public AddPage submitAddPlantForm() {
        submitButton.submit();
        await().atMost(5, TimeUnit.SECONDS).untilPage().isLoaded();
        return this;
    }

    public AddPage assertErrorMessagePresent() {
        assertThat(errorMessage.present()).isTrue();
        return this;
    }
}
