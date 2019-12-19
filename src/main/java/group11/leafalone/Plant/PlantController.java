package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUserDetailsService;
import group11.leafalone.Auth.UserRepository;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PlantController {

    PlantService plantService;
    PlantCareService plantCareService;
    private LeafAloneUserDetailsService userService;

    private PlantCare plantCare;

    public PlantController(PlantService plantService, PlantCareService plantCareService, LeafAloneUserDetailsService userService){
        this.plantService = plantService;
        this.plantCareService = plantCareService;
        this.userService = userService;
    }

    //AddPlant

    @GetMapping("/plants/add")
    public String addPlantForm(Model model) {
        model.addAttribute("plant", new Plant());
        model.addAttribute("sunSituations", SunSituation.values());
        model.addAttribute("plantCares", plantCareService.findAll());
        return "plants/add";
    }

    @PostMapping("/plants/add")
    public String addPlantSubmit(@Valid Plant plant, BindingResult bindingResult, Model model) {
        bindingResult = plantService.validatePlant(plant, bindingResult);

        if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
            model.addAttribute("plant", plant);
            model.addAttribute("sunSituations", SunSituation.values());
            model.addAttribute("plantCares", plantCareService.findAll());
            return "plants/add";
        }
        plantService.save(plant, plant.getName());
        return "redirect:list";
    }

    //Contribute Plant

    @GetMapping("/plants/contribute")
    public String contributePlantForm(Model model) {
        PlantCare plantCare = new PlantCare();
        if(this.plantCare != null) {
            plantCare = this.plantCare;
            this.plantCare = null;
        }
        model.addAttribute("plantCare",plantCare);
        model.addAttribute("sunSituations", SunSituation.values());
        return "plants/contribute";
    }

    @PostMapping("/plants/contribute")
    public String contributePlantSubmit(@Valid PlantCare plantCare, BindingResult bindingResult, Model model) {
        bindingResult = plantCareService.validatePlantCare(plantCare, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("sunSituations", SunSituation.values());
            return "plants/contribute";
        }
        return "forward:/plants/confirm";
    }

    @PostMapping("/plants/confirm")
    public String confirmPlantCare(@Valid PlantCare plantCare, Model model) {
        this.plantCare = plantCare;
        model.addAttribute("plantCare", plantCare);
        return "plants/confirm";
    }

    @GetMapping("/plants/confirm/ok")
    public String confirmPlantCareOk() {
        plantCareService.save(plantCare);
        this.plantCare = null;
        return "redirect:/?message=Thank you for your contribution.";
    }

    //List Plant

    @GetMapping("plants/list")
    public String getPlantList(Model model) {
        List<Plant> plantList = plantService.findByLeafAloneUserOrdered(userService.getCurrentUser());
        model.addAttribute("plants", plantList);
        return "plants/list";

    }

    @GetMapping("plants/watered/{name}")
    public String waterPlant(@PathVariable String name, Model model) {
        plantService.waterPlant(name);
        List<Plant> plantList = plantService.findByLeafAloneUserOrdered(userService.getCurrentUser());
        model.addAttribute("plants", plantList);
        return "redirect:/plants/list?message="+name+" got watered";
    }

    @GetMapping("plants/delete/{name}")
    public String deletePlant(@PathVariable String name, Model model) {
        plantService.deletePlant(name);
        List<Plant> plantList = plantService.findByLeafAloneUserOrdered(userService.getCurrentUser());
        model.addAttribute("plants", plantList);
        return "redirect:/plants/list?message="+name+" got deleted";
    }

    //Edit Plant

    @GetMapping("plants/edit/{name}")
    public String editPlant(@PathVariable String name, Model model) {
        Plant plant = plantService.findByName(name);
        model.addAttribute("plant", plant);
        model.addAttribute("sunSituations", SunSituation.values());
        model.addAttribute("plantCares", plantCareService.findAll());
        return "plants/edit";
    }

    @PostMapping("plants/edit/{name}")
    public String editPlantSubmit(@PathVariable String name, @Valid Plant plant, BindingResult bindingResult, Model model) {
        bindingResult = plantService.validatePlant(plant, bindingResult);

        if (bindingResult.hasFieldErrors("acquisition") || bindingResult.hasFieldErrors("watered") || bindingResult.hasFieldErrors("notes")) {
            model.addAttribute("plant", plant);
            model.addAttribute("sunSituations", SunSituation.values());
            model.addAttribute("plantCares", plantCareService.findAll());
            return "plants/edit";
        }
        plantService.save(plant, name);
        return "redirect:/plants/list";
    }
}
