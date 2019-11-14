package group11.leafalone;

import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.hook.wait.Wait;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Wait
class IndexTest extends FluentTest {

    @BeforeEach
    void goToIndex() {
        goTo("http://localhost:8080/");
    }

    @Test
    void index_title() {
        assertThat(window().title()).isEqualTo("Hello");
    }

    @Test
    void index_about() {
        assertThat($("#aboutLink").present()).isTrue();
        $("#aboutLink").click();
        assertThat(window().title()).isEqualTo("About Us");
    }

    @Test
    void index_welcomeText() {
        assertThat($("#welcomeText").present()).isTrue();
    }

}
