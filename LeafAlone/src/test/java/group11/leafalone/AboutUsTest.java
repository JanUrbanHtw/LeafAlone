package group11.leafalone;

import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.hook.wait.Wait;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Wait
class AboutUsTest extends FluentTest {

    @BeforeEach
    void goToAboutUs() {
        goTo("http://localhost:8080/about");
    }

    @Test
    void aboutUs_title() {
        assertThat(window().title()).isEqualTo("About Us");
    }

    @Test
    void aboutUs_groupMembers() {
        assertThat($("#groupMembers").present()).isTrue();
        assertThat($("#groupMembers").texts().toString().replaceAll("\\s+", "")).containsSequence("Jan", "Urban", "s0563416");
        assertThat($("#groupMembers").texts().toString().replaceAll("\\s+", "")).containsSequence("Corinna", "Bockhop", "s0563861");
        assertThat($("#groupMembers").texts().toString().replaceAll("\\s+", "")).containsSequence("Marie", "Bittiehn", "s0563863");
    }

    @Test
    void aboutUs_time() {
        assertThat($("#time").present()).isTrue();
    }

    @Test
    void aboutUs_description() {
        assertThat($("#projectdescription").present()).isTrue();
    }
}
