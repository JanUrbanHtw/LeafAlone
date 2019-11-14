package group11.leafalone;

import com.gargoylesoftware.htmlunit.html.Html;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class addPlantController {

    @RequestMapping(value = "/addPlant", method = RequestMethod.GET)
    public ModelAndView addPlantForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("plant", new Plant());
        modelAndView.setViewName("addPlant.html");
        return modelAndView;
    }

    @RequestMapping(value = "/addPlant", method = RequestMethod.POST)
    public String addPlantSubmit() {
        return "redirect:about";
    }
}
