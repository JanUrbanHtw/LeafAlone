package group11.leafalone.Auth;

import group11.leafalone.Email.LeafAloneEmailService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class AuthController {

    private LeafAloneUserDetailsService userService;
    private LeafAloneEmailService emailService;

    //testing
    public AuthController(LeafAloneUserDetailsService userService, LeafAloneEmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken))
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/login?logout";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("leafAloneUser", new LeafAloneUser());
        return "register.html";
    }

    @PostMapping("/register")
    public String registerSubmit(@Valid LeafAloneUser user, BindingResult bindingResult, Model model, HttpServletRequest request) {
        bindingResult = userService.validateUser(user, bindingResult);
        if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
            return "register";
        }
        userService.save(user);
        emailService.sendRegistrationMail(user.getUsername(), user.getEmail());
        try {
            request.login(user.getUsername(), user.getConfirmPassword());
        } catch (ServletException e) {
            return "redirect:/?message=Auto-Login failed, but either way: Welcome "+user.getUsername()+"!";
        }
        return "redirect:/?message=Welcome "+user.getUsername()+"!";

    }

    //only useful if getting code out of navbar makes sense
    public boolean isUser() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().equals(new SimpleGrantedAuthority("USER"));
    }

    public boolean isContributor() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().equals(new SimpleGrantedAuthority("CONTRIBUTOR"));
    }

    public boolean canAddPlant() {
        if (isUser() || isContributor()) return true;
        return false;
    }

    public boolean canAddPlantCare() {
        return isUser();
    }
}
