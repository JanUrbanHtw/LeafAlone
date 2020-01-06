package group11.leafalone.Pages.plants;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withText;

@PageUrl("http://localhost:8080/plants/edit/{name}")
public class PlantEditPage extends FluentPage {
    public static final String TITLE = "Edit your Plant";

    @FindBy(className = "errorMessage")
    private FluentWebElement errorMessage;

    @FindBy(id = "addPlantForm")
    private FluentWebElement form;

    @FindBy(id = "nameText")
    private FluentWebElement nameText;

    @FindBy(id = "plantCare")
    private FluentWebElement plantCare;

    @FindBy(id = "sun")
    private FluentWebElement sun;

    @FindBy(id = "acquisition")
    private WebElement acquisition;

    @FindBy(id = "watered")
    private WebElement watered;

    @FindBy(id = "notes")
    private WebElement notes;

    @FindBy(id = "submitButton")
    private FluentWebElement submitButton;

    @FindBy(id = "deleteButton")
    private FluentWebElement deleteButton;

    public PlantEditPage assertPlantName(String name) {
        assertThat(nameText.text()).isEqualTo(name);
        return this;
    }

    public PlantEditPage assertPlantTypeIs(String plantCareName) {
        assertThat(plantCare.text()).isEqualTo(plantCareName);
        return this;
    }

    public PlantEditPage choosePlantType(String plantType) {
        plantCare.find("option", withText(plantType)).click();
        return this;
    }

    public PlantEditPage assertSunSituationIs(String sunSituation) {
        assertThat(sun.text()).isEqualTo(sunSituation);
        return this;
    }

    public PlantEditPage chooseSunSituation(String sunSituation) {
        sun.find("option", withText(sunSituation)).click();
        return this;
    }

    public PlantEditPage assertPlantAcquisitionIs(String acquisition) {
        assertThat(this.acquisition.getAttribute("value")).isEqualTo(acquisition);
        return this;
    }

    public PlantEditPage inputPlantAcquisition(String acquisition) {
        this.acquisition.sendKeys(Keys.chord(Keys.CONTROL, "a"), acquisition);
        return this;
    }

    public PlantEditPage assertPlantWateredIs(String watered) {
        assertThat(this.watered.getAttribute("value")).isEqualTo(watered);
        return this;
    }

    public PlantEditPage inputPlantWatered(String watered) {
        this.watered.sendKeys(Keys.chord(Keys.CONTROL, "a"), watered);
        return this;
    }

    public PlantEditPage assertPlantNotesIs(String notes) {
        assertThat(this.notes.getAttribute("value")).isEqualTo(notes);
        return this;
    }

    public PlantEditPage inputNotes(String notes) {
        this.notes.clear();
        assertThat(this.notes.getAttribute("value")).isEqualTo("");
        this.notes.sendKeys(notes);
        return this;
    }

    public PlantEditPage submitPlantEditForm() {
        form.submit();
        await().atMost(5, TimeUnit.SECONDS).untilPage().isLoaded();
        return this;
    }

    public PlantEditPage assertErrorMessage(boolean present) {
        if (present) {
            assertThat(errorMessage.present()).isTrue();
        } else {
            assertThat(errorMessage.present()).isFalse();
        }
        return this;
    }

    public PlantEditPage deletePlant() {
        deleteButton.click();
        return this;
    }

    public PlantEditPage alertPresent(boolean present) {
        if (present) {
            assertThat(alert().present()).isTrue();
        } else {
            assertThat(alert().present()).isFalse();
        }
        return this;
    }

    public PlantEditPage acceptAlert() {
        alert().accept();
        await().atMost(5, TimeUnit.SECONDS).untilPage().isLoaded();
        return this;
    }
}
