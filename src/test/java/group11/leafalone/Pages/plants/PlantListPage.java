package group11.leafalone.Pages.plants;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@PageUrl("http://localhost:8080/plants/list")
public class PlantListPage extends FluentPage {
    public static final String TITLE = "Your Plants";

    @FindBy(id = "plantList")
    private FluentWebElement list;

    @FindBy(id = "addPlantLink")
    private FluentWebElement plantLink;

    public PlantListPage isContainedInList(String name) {
        boolean found = false;
        assertThat(list.present()).isTrue();
        FluentList<FluentWebElement> elements = list.find("tbody").first().find("tr");
        for (FluentWebElement element : elements) {
            if (element.find("th").first().text().equals(name)) {
                found = true;
                break;
            }
        }
        assertThat(found).withFailMessage("entry is not in list").isTrue();
        return this;
    }

    public PlantListPage clickPlantLink() {
        plantLink.click();
        await().atMost(5, TimeUnit.SECONDS).untilPage().isLoaded();
        return this;
    }
}
