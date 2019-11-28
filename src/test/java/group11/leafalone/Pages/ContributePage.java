package group11.leafalone.Pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withText;

@PageUrl("http://localhost:8080/plants/contribute")
public class ContributePage extends FluentPage {
    public static final String TITLE = "Contribute your plant knowledge!";

    @FindBy(className = "errorMessage")
    private FluentWebElement errorMessage;

    @FindBy(id = "colloquial")
    private FluentWebElement colloquialInput;

    @FindBy(id = "scientific")
    private FluentWebElement scientificInput;

    @FindBy(id = "sunSituation")
    private FluentWebElement sunSituationInput;

    @FindBy(id = "waterCycle")
    private FluentWebElement waterCycleInput;

    @FindBy(id = "waterAmount")
    private FluentWebElement waterAmountInput;

    @FindBy(id = "soilAdvice")
    private FluentWebElement soilAdviceInput;

    @FindBy(id = "description")
    private FluentWebElement descriptionInput;

    @FindBy(id = "submitButton")
    private FluentWebElement submitButton;

    public ContributePage inputColloquial(String colloquial) {
        colloquialInput.write(colloquial);
        return this;
    }

    public ContributePage inputScientific(String scientific) {
        scientificInput.write(scientific);
        return this;
    }

    public ContributePage inputSunSituation(String sunSituation) {
        sunSituationInput.find("option", withText(sunSituation)).click();
        return this;
    }

    public ContributePage inputWaterCycle(String cycleDuration) {
        waterCycleInput.write(cycleDuration);
        return this;
    }

    public ContributePage inputWaterAmount(String waterAmount) {
        waterAmountInput.write(waterAmount);
        return this;
    }

    public ContributePage inputSoilAdvice(String soilAdvice) {
        soilAdviceInput.write(soilAdvice);
        return this;
    }

    public ContributePage inputDescription(String description) {
        descriptionInput.write(description);
        return this;
    }

    public ContributePage submitContributePlantForm() {
        submitButton.click();
        await().untilPage().isLoaded();
        return this;
    }

    public ContributePage assertPresent(String element) {
        assertThat(el(element).present()).isTrue();
        return this;
    }

    public ContributePage assertErrorPresent() {
        assertThat(errorMessage.present()).isTrue();
        return this;
    }
}
