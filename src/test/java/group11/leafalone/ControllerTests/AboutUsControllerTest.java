package group11.leafalone.ControllerTests;

import group11.leafalone.AboutUsController;
import group11.leafalone.Clock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AboutUsControllerTest {

    private static AboutUsController aboutUsController;

    @BeforeAll
    static void init() {
        aboutUsController = new AboutUsController();
    }

    //index

    @Test
    void Index_shouldRenderIndexView() {
        ModelAndView view = aboutUsController.hello();
        assertEquals("index.html", view.getViewName());
    }

    //about

    @Test
    void About_shouldRenderAboutView() {
        Clock clock = Mockito.mock(Clock.class);
        ModelAndView view = aboutUsController.aboutUs(clock);
        assertEquals("aboutUs.html", view.getViewName());
    }
}