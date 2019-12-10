package group11.leafalone.Auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
class LoginControllerTest {

    private MockMvc mockMVC;

    private UserRepository userRepository;
    private LoginController loginController;

    @BeforeEach
    void init() {
        userRepository = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        loginController = new LoginController(userRepository, passwordEncoder);

        mockMVC = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    // LOGIN
    @Test
    void login_GET_shouldRenderLoginView() throws Exception {
        mockMVC.perform(get("/login"))
                .andExpect(view().name("login.html"))
                .andExpect(status().is2xxSuccessful());

    }

    //LOGOUT

    @Test
    void logout_GET_ShouldRedirect() throws Exception {
        mockMVC.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login?logout"));
    }

    // REGISTER

    @Test
    void register_GET_shouldRenderRegister() throws Exception {
        mockMVC.perform(get("/register"))
                .andExpect(view().name("register.html"))
                .andExpect(status().is2xxSuccessful());
    }


    //Can't use mockMVC because the method login in MockHttpServletRequest throws an Error
    @Test
    void validPostShouldRenderHomeView() {
        LeafAloneUser user = new LeafAloneUser("Dummy", "password", "ROLE_USER", "password");
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Model model = Mockito.mock(Model.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        String viewname = loginController.registerSubmit(user, bindingResult, model, request);
        assertEquals("redirect:/?newUser", viewname);
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
