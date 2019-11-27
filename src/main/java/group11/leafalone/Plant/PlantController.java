package group11.leafalone.Plant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PlantController {

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
        return "redirect:../about";
    }

    @GetMapping("plants/list")
    public ModelAndView getPlantList() {
        ModelAndView modelAndView = new ModelAndView();
        List<UserPlant> plantList = new ArrayList<UserPlant>();
        //TODO read data from the database

//        UserPlant dummyPlant = new UserPlant();
//        dummyPlant.setName("dummyPlant");
//        dummyPlant.setPlantCare(new PlantCare("typeDummy", null, null, null,
//                0, 0, null,null, null));
//        dummyPlant.setSun(SunSituation.DARK);
//        dummyPlant.setAcquisition(new Date());
//        dummyPlant.setWatered(new Date());
//        plantList.add(dummyPlant);
        modelAndView.addObject("plants", plantList);
        modelAndView.setViewName("plants/list.html");
        return modelAndView;
    }

}
