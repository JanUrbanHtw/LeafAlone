package group11.leafalone.Pages;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;

import static org.assertj.core.api.Assertions.assertThat;

@PageUrl("http://localhost:8080/")
public class IndexPage extends FluentPage {
    public static final String TITLE = "Hello";
    public static final String WELCOME = "#welcomeText";

    public IndexPage assertIsPresent(String string) {
        assertThat(el(string).present()).isTrue();
        return this;
    }

    public IndexPage assertNotPresent(String string) {
        assertThat(el(string).present()).isFalse();
        return this;
    }


}
