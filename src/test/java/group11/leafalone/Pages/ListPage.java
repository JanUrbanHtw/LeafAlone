package group11.leafalone.Pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

@PageUrl("http://localhost:8080/plants/list")
public class ListPage extends FluentPage {
    public static final String TITLE = "Your Plants";

    @FindBy(id = "plantList")
    private FluentWebElement list;

    public ListPage isContainedInList(String name) {
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


}
