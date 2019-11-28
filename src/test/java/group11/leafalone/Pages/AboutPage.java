package group11.leafalone.Pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;

import static org.assertj.core.api.Assertions.assertThat;

@PageUrl("http://localhost:8080/about")
public class AboutPage extends FluentPage {

    public static final String TITLE = "About Us";
    public static final String GROUP_MEMBERS = "#groupMembers";
    public static final String TIME = "#time";
    public static final String PROJECT_DESCRIPTION = "#projectdescription";

    public AboutPage assertIsContainedInGroupMembers(String string) {
        assertThat($(GROUP_MEMBERS).texts().toString()).contains(string);
        return this;
    }

    public AboutPage assertIsPresent(String element) {
        assertThat(el(element).present()).isTrue();
        return this;
    }
}
