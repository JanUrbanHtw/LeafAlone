package group11.leafalone.Auth;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import group11.leafalone.Email.LeafAloneEmailService;
import group11.leafalone.Plant.PlantRepository;
import group11.leafalone.Plant.PlantService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
class AuthControllerTest {

    private MockMvc mockMVC;

    private UserRepository userRepository;
    private LeafAloneUserDetailsService userService;
    @Autowired
    private LeafAloneEmailService emailService;
    private AuthController authController;

    @BeforeEach
    void init() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new LeafAloneUserDetailsService(userRepository, new BCryptPasswordEncoder());
//        emailService = new LeafAloneEmailService(new PlantService(Mockito.mock(PlantRepository.class), userService));
        authController = new AuthController(userService, emailService);

        mockMVC = MockMvcBuilders.standaloneSetup(authController).build();
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

    @Nested
            //Why? because that way I can do a (new, additional) @BeforeEach. The other methods dont need it
            // and itll be time-intensive, but i also dont wanna paste it each time!
            //if I find a cleaner way to do this (maybe put it into its own helper class? EmailTest needs this too after all!) I will - cbo
    class AuthControllerRegisterPost {

        private GreenMail greenMail;

        @BeforeEach
        public void startMailServer() {
            ServerSetup setup = new ServerSetup(3025, "localhost", "smtp");
            greenMail = new GreenMail(setup).withConfiguration(GreenMailConfiguration.aConfig().withDisabledAuthentication());
            greenMail.start();
        }

        @AfterEach
        public void stopMailServer() {
            greenMail.stop();
        }

        //Can't use mockMVC because the method login in MockHttpServletRequest throws an Error
        @Test
        void validPostShouldRenderHomeView() {
            LeafAloneUser user = new LeafAloneUser("Dummy", "password", "ROLE_USER", "leafalone@mail.de", "password");
            BindingResult bindingResult = Mockito.mock(BindingResult.class);
            Model model = Mockito.mock(Model.class);
            HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

            String viewname = authController.registerSubmit(user, bindingResult, model, request);
            assertEquals("redirect:/?message=Welcome Dummy!", viewname);
        }


        @Test
        void postWithFalseUsernameShouldAddError() {
            LeafAloneUser user = new LeafAloneUser("Dummy", "password", "ROLE_USER", "leafalone@mail.de", "password");
            BindingResult bindingResult = Mockito.mock(BindingResult.class);
            Model model = Mockito.mock(Model.class);
            HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

            Mockito.doReturn(new LeafAloneUser("Dummy", "password", "ROLE_USER", "leafalone@mail.de", "password"))
                    .when(userRepository).findByUsername("Dummy");

            authController.registerSubmit(user, bindingResult, model, request);
            Mockito.verify(bindingResult).addError(new FieldError("user", "username", "Username already taken"));
        }

        @Test
        void postWithFalsePasswordShouldAddError() {
            LeafAloneUser user = new LeafAloneUser("Dummy", "password", "ROLE_USER", "leafalone@mail.de", "nope");
            BindingResult bindingResult = Mockito.mock(BindingResult.class);
            Model model = Mockito.mock(Model.class);
            HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

            authController.registerSubmit(user, bindingResult, model, request);
            Mockito.verify(bindingResult).addError(new FieldError("user", "confirmPassword", "Required to be identical to password"));
        }

        //TODO
        @Test
        void postWithFalseEmailShouldAddError() {

        }


    }

    @Test
    void postWithFalseInputsShouldRenderRegisterView() {
        LeafAloneUser user = new LeafAloneUser("Dummy", "password", "ROLE_USER", "leafalone@mail.de", "nope");
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        Model model = Mockito.mock(Model.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        String viewname = authController.registerSubmit(user, bindingResult, model, request);
        assertEquals("register", viewname);
    }


}
