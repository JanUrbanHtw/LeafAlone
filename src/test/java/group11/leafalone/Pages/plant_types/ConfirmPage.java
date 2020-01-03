package group11.leafalone.Pages.plant_types;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

@PageUrl("/plants/confirm")
public class ConfirmPage extends FluentPage {
    public static final String TITLE = "Confirm Inputs";

    @FindBy(id = "colloquial")
    private FluentWebElement colloquial;

    @FindBy(id = "scientific")
    private FluentWebElement scientific;

    @FindBy(id = "sun")
    private FluentWebElement sun;

    @FindBy(id = "waterCycle")
    private FluentWebElement waterCycle;

    @FindBy(id = "waterAmount")
    private FluentWebElement waterAmount;

    @FindBy(id = "soilAdvice")
    private FluentWebElement soilAdvice;

    @FindBy(id = "description")
    private FluentWebElement description;

    @FindBy(id = "revertButton")
    private FluentWebElement revertButton;

    @FindBy(id = "okButton")
    private FluentWebElement okButton;

    public ConfirmPage assertIsColloquial(String colloquial) {
        assertThat(colloquial).isEqualTo(this.colloquial.text());
        return this;
    }

    public ConfirmPage assertIsScientific(String scientific) {
        assertThat(scientific).isEqualTo(this.scientific.text());
        return this;
    }

    public ConfirmPage assertIsSun(String sun) {
        assertThat(sun).isEqualTo(this.sun.text());
        return this;
    }

    public ConfirmPage assertIsWaterCycle(String waterCycle) {
        assertThat(waterCycle).isEqualTo(this.waterCycle.text());
        return this;
    }

    public ConfirmPage assertIsWaterAmount(String waterAmount) {
        assertThat(waterAmount).isEqualTo(this.waterAmount.text());
        return this;
    }

    public ConfirmPage assertIsSoilAdvice(String soilAdvice) {
        assertThat(soilAdvice).isEqualTo(this.soilAdvice.text());
        return this;
    }

    public ConfirmPage assertIsDescription(String description) {
        assertThat(description).isEqualTo(this.description.text());
        return this;
    }

    public ConfirmPage revert() {
        revertButton.click();
        return this;
    }

    public ConfirmPage ok() {
        okButton.click();
        return this;
    }
}
