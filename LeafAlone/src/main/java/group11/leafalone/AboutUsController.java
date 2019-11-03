package group11.leafalone;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutUsController {

    @RequestMapping("/")
    public ModelAndView hello () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello.html");
        return modelAndView;
    }

    @RequestMapping("/aboutus")
    public ModelAndView aboutUs(@ModelAttribute Clock clock) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("aboutUs.html");
        modelAndView.addObject("clock", clock);
        return modelAndView;
    }
}
