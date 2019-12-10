package group11.leafalone;

import group11.leafalone.Pages.AboutPage;
import org.fluentlenium.adapter.junit.jupiter.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeafaloneApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AboutUsTest extends FluentTest {

    @Page
    AboutPage aboutPage;

    @Test
    void everythingPresent() {
        goTo(aboutPage)
                //GroupMembers
                .assertIsPresent(AboutPage.GROUP_MEMBERS)
                .assertIsContainedInGroupMembers("Jan")
                .assertIsContainedInGroupMembers("Corinna")
                .assertIsContainedInGroupMembers("Marie")
                //Time
                .assertIsPresent(AboutPage.TIME)
                //Description
                .assertIsPresent(AboutPage.PROJECT_DESCRIPTION);
        assertThat(window().title()).isEqualTo(AboutPage.TITLE);
    }
}
