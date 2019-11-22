package group11.leafalone.Plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PlantController {

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private PlantCareRepository plantCareRepository;

    @GetMapping("/plants/add")
    public String addPlantForm(Model model) {
        model.addAttribute("plant", new UserPlant());
        model.addAttribute("sunSituations", SunSituation.values());
        return "plants/add";
    }

    @PostMapping("/plants/add")
    public String addPlantSubmit(@Valid UserPlant plant, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("plant", plant);
            model.addAttribute("sunSituations", SunSituation.values());
            return "plants/add";
        }
        plantRepository.save(plant);
        return "redirect:../about";
    }


    @GetMapping("/plants/contribute")
    public String contributePlantForm(Model model) {
        model.addAttribute("plantCare", new PlantCare());
        model.addAttribute("sunSituations", SunSituation.values());
        return "plants/contribute";
    }

    @PostMapping("/plants/contribute")
    public String contributePlantSubmit(@Valid PlantCare plantCare, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "plants/contribute";
        }
        plantCareRepository.save(plantCare);
        return "redirect:../about";
    }

}
