package group11.leafalone.Pages.plants;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@PageUrl("http://localhost:8080/plants/details/{name}")
public class PlantDetailsPage extends FluentPage {
    public static final String TITLE = "Plant Details";

    @FindBy(id = "name")
    private FluentWebElement name;

    @FindBy(id = "plantCare")
    private FluentWebElement plantCare;

    @FindBy(id = "sunSituation")
    private FluentWebElement sun;

    @FindBy(id = "sunWarning")
    private FluentWebElement sunWarning;

    @FindBy(id = "acquisition")
    private FluentWebElement acquisition;

    @FindBy(id = "watered")
    private FluentWebElement watered;

    @FindBy(id = "nextWatered")
    private FluentWebElement nextWatering;

    @FindBy(id = "notes")
    private FluentWebElement notes;

    @FindBy(id = "editButton")
    private FluentWebElement editButton;

    @FindBy(id = "listButton")
    private FluentWebElement listButton;

    public PlantDetailsPage assertNameIs(String name) {
        assertThat(this.name.text()).isEqualTo(name);
        return this;
    }

    public PlantDetailsPage assertPlantCareIs(String plantCare) {
        assertThat(this.plantCare.text()).isEqualTo(plantCare);
        return this;
    }

    public PlantDetailsPage assertSunSituationIs(String sunSituation) {
        assertThat(this.sun.text()).isEqualTo(sunSituation);
        return this;
    }

    public PlantDetailsPage assertSunSituationWarningPresent(boolean present) {
        if (present) {
            assertThat(this.sunWarning.present()).isTrue();
        } else {
            assertThat(this.sunWarning.present()).isFalse();
        }
        return this;
    }

    public PlantDetailsPage assertAcquisitionIs(String acquisition) {
        assertThat(this.acquisition.text()).isEqualTo(acquisition);
        return this;
    }

    public PlantDetailsPage assertWateredIs(String watered) {
        assertThat(this.watered.text()).isEqualTo(watered);
        return this;
    }

    public PlantDetailsPage assertNextWateringIs(String nextWatering) {
        assertThat(this.nextWatering.text()).isEqualTo(nextWatering);
        return this;
    }

    public PlantDetailsPage assertNotesIs(String notes) {
        assertThat(this.notes.text()).isEqualTo(notes);
        return this;
    }

    public PlantDetailsPage pressListButton() {
        this.listButton.click();
        await().atMost(5, TimeUnit.SECONDS).untilPage().isLoaded();
        return this;
    }

    public PlantDetailsPage pressEditButton() {
        this.editButton.click();
        await().atMost(5, TimeUnit.SECONDS).untilPage().isLoaded();
        return this;
    }
}
