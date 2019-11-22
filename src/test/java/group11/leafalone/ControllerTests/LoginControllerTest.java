package group11.leafalone.ControllerTests;

import group11.leafalone.Auth.LoginController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginControllerTest {

    private static LoginController loginController;

    @BeforeAll
    static void init() {
        loginController = new LoginController();
    }

    @Test
    void shouldRenderLoginView() {
        ModelAndView view = loginController.login();
        assertEquals("login.html", view.getViewName());
    }
}
