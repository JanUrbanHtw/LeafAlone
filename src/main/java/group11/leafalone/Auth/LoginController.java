package group11.leafalone.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    public LoginController() {}

    //testing
    protected LoginController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @RequestMapping(value="/logout", method= RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/login?logout";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("leafAloneUser", new LeafAloneUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid LeafAloneUser user, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if(!(user.getPassword().equals(user.getConfirmPassword()))) {
            FieldError error = new FieldError("user", "confirmPassword", "Required to be identical to password");
            bindingResult.addError(error);
        }
        LeafAloneUser repoUser = userRepository.findByUsername(user.getUsername());
        System.out.println(repoUser != null);
        if(repoUser != null) {
            FieldError error = new FieldError("user", "username", "Username already taken");
            bindingResult.addError(error);
        }
        if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
            return "register";
        }
        userRepository.save(user);
        try {
            request.login(user.getUsername(), user.getPassword());
        } catch (ServletException e) {
            //TODO: Customized homepage-redirect with messages, tried to, didn't work
            model.addAttribute("newUser", true);
            model.addAttribute("autoLoginFailed", true);
            return "redirect:/";
        }
        model.addAttribute("newUser", true);
        model.addAttribute("autoLoginFailed", false);
        return "redirect:/";
    }
}
