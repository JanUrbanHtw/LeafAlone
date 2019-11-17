package group11.leafalone.Plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PlantController {

//    @Autowired
//    PlantService plantservice;

    @RequestMapping(value = "/plants/add", method = RequestMethod.GET)
    public ModelAndView addPlantForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("plant", new UserPlant());
        modelAndView.setViewName("plants/add.html");
        return modelAndView;
    }

    @RequestMapping(value = "/plants/add", method = RequestMethod.POST)
    public String addPlantSubmit() {
        return "redirect:../about";
    }


    @GetMapping("/plants/contribute")
    public String contributePlantForm(Model model) {
        model.addAttribute("plantdescription", new PlantCare());
        return "plants/contribute";
    }

    @PostMapping("/plants/contribute")
    public String contributePlantSubmit(@ModelAttribute PlantCare plantdescription) {
        //plantservice.addPlantCare();
        return "redirect:../about";
    }

}
