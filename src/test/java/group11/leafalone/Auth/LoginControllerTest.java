package group11.leafalone.Auth;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    private static LoginController loginController;
    private static UserRepository userRepository;

    @BeforeAll
    static void init() {
        userRepository = Mockito.mock(UserRepository.class);
        loginController = new LoginController(userRepository);
    }

    // LOGIN
    @Test
    void shouldRenderLoginView() {
        ModelAndView view = loginController.login();
        assertEquals("login.html", view.getViewName());
    }

    // REGISTER
    @Test
    void getShouldRenderRegisterView() {
        Model model = Mockito.mock(Model.class);
        String viewname = loginController.registerForm(model);
        assertEquals("register", viewname);
    }

    @Test
    void validPostShouldRenderHomeView() {
        LeafAloneUser user = new LeafAloneUser("Dummy", "password", "ROLE_USER", "password");
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Model model = Mockito.mock(Model.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        String viewname = loginController.registerSubmit(user, bindingResult, model, request);
        assertEquals("redirect:/", viewname);
    }

    @Test
    void postWithFalseUsernameShouldAddError() {
        LeafAloneUser user = new LeafAloneUser("Dummy", "password", "ROLE_USER", "password");
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Model model = Mockito.mock(Model.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        Mockito.doReturn(new LeafAloneUser("Dummy", "password", "ROLE_USER", "password"))
                .when(userRepository).findByUsername("Dummy");

        loginController.registerSubmit(user, bindingResult, model, request);
        Mockito.verify(bindingResult).addError(new FieldError("user", "username", "Username already taken"));
    }

    @Test
    void postWithFalsePasswordShouldAddError() {
        LeafAloneUser user = new LeafAloneUser("Dummy", "password", "ROLE_USER", "nope");
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Model model = Mockito.mock(Model.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        loginController.registerSubmit(user, bindingResult, model, request);
        Mockito.verify(bindingResult).addError(new FieldError("user", "confirmPassword", "Required to be identical to password"));
    }

    @Test
    void postWithFalseInputsShouldRenderRegisterView() {
        LeafAloneUser user = new LeafAloneUser("Dummy", "password", "ROLE_USER", "nope");
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        Model model = Mockito.mock(Model.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        String viewname = loginController.registerSubmit(user, bindingResult, model, request);
        assertEquals("register", viewname);
    }
}
