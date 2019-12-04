package group11.leafalone.Auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@Controller
public class LoginController {

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
