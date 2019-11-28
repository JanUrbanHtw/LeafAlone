package group11.leafalone.PageTests;

import group11.leafalone.Pages.AboutPage;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.hook.wait.Wait;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Wait
class AboutUsTest extends FluentTest {

    @Page
    AboutPage aboutPage;

    @Test
    void groupMembersPresent() {
        goTo(aboutPage)
                .assertIsPresent(AboutPage.GROUP_MEMBERS)
                .assertIsContainedInGroupMembers("Jan")
                .assertIsContainedInGroupMembers("Corinna")
                .assertIsContainedInGroupMembers("Marie");
    }

    @Test
    void ClockPresent() {
        goTo(aboutPage)
                .assertIsPresent(AboutPage.TIME);
    }

    @Test
    void DescriptionPresent() {
        goTo(aboutPage)
                .assertIsPresent(AboutPage.PROJECT_DESCRIPTION);
    }

    @Test
    void IsTitleCorrect() {
        goTo(aboutPage);
        assertThat(window().title()).isEqualTo(AboutPage.TITLE);
    }
}
