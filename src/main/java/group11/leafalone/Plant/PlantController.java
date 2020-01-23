package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUserDetailsService;
import group11.leafalone.ViewModel.Details_Plant;
import group11.leafalone.ViewModel.List_Plant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PlantController {

    PlantService plantService;
    PlantCareService plantCareService;
    private LeafAloneUserDetailsService userService;

    private PlantCare plantCare;

    public PlantController(PlantService plantService, PlantCareService plantCareService, LeafAloneUserDetailsService userService) {
        this.plantService = plantService;
        this.plantCareService = plantCareService;
        this.userService = userService;
    }

    //AddPlant

    @GetMapping("plants/add")
    public String addPlantForm(Model model) {
        model.addAttribute("plant", new Plant());
        model.addAttribute("sunSituations", SunSituation.values());
        model.addAttribute("plantCares", plantCareService.findAll());
        return "plants/add";
    }

    @PostMapping("plants/add")
    public String addPlantSubmit(@Valid Plant plant, BindingResult bindingResult, Model model) {
        bindingResult = plantService.validatePlant(plant, bindingResult);

        if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
            model.addAttribute("plant", plant);
            model.addAttribute("sunSituations", SunSituation.values());
            model.addAttribute("plantCares", plantCareService.findAll());
            return "plants/add";
        }
        plantService.save(plant);
        return "redirect:/plants/list";
    }

    //Add Plant-Type

    @GetMapping("plant_types/add")
    public String addPlantTypForm(Model model) {
        PlantCare plantCare = new PlantCare();
        if (this.plantCare != null) {
            plantCare = this.plantCare;
            this.plantCare = null;
        }
        model.addAttribute("plantCare", plantCare);
        model.addAttribute("sunSituations", SunSituation.values());
        return "plant_types/add";
    }

    @PostMapping("plant_types/add")
    public String contributePlantSubmit(@Valid PlantCare plantCare, BindingResult bindingResult, Model model) {
        bindingResult = plantCareService.validatePlantCare(plantCare, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("sunSituations", SunSituation.values());
            return "plant_types/add";
        }
        return "forward:/plant_types/confirm";
    }

    @PostMapping("plant_types/confirm")
    public String confirmPlantCare(@Valid PlantCare plantCare, Model model) {
        this.plantCare = plantCare;
        model.addAttribute("plantCare", plantCare);
        return "plant_types/confirm";
    }

    @GetMapping("plant_types/confirm/ok")
    public String confirmPlantCareOk() {
        plantCareService.save(plantCare);
        this.plantCare = null;
        return "redirect:/?message=Thank you for your contribution.";
    }

    //List Plants

    @GetMapping("plants/list")
    public String getPlantList(Model model) {
        List<List_Plant> plantList =
                List_Plant.plantListToListPlant(plantService.findByLeafAloneUserOrderByNextWatering(userService.getCurrentUser()));
        model.addAttribute("plants", plantList);
        return "plants/list";

    }

    @GetMapping("plants/watered/{name}")
    public String waterPlant(@PathVariable String name, Model model) {
        plantService.waterPlant(name);
        List<List_Plant> plantList =
                List_Plant.plantListToListPlant(plantService.findByLeafAloneUserOrderByNextWatering(userService.getCurrentUser()));
        model.addAttribute("plants", plantList);
        return "redirect:/plants/list?message=" + name + " got watered";
    }

    @GetMapping("plants/delete/{name}")
    public String deletePlant(@PathVariable String name, Model model) {
        plantService.deletePlant(name);
        List<List_Plant> plantList =
                List_Plant.plantListToListPlant(plantService.findByLeafAloneUserOrderByNextWatering(userService.getCurrentUser()));
        model.addAttribute("plants", plantList);
        return "redirect:/plants/list?message=" + name + " got deleted";
    }

    @GetMapping("plants/details/{name}")
    public String getPlantDetails(@PathVariable String name, Model model) {
        Details_Plant plant = new Details_Plant(plantService.findByName(name));
        model.addAttribute("plant", plant);
        return "plants/details";
    }

    //List Plant-Types

    @GetMapping("plant_types/list")
    public String getTypesList(Model model) {
        List<PlantCare> plantCareList = plantCareService.findByLeafAloneUserOrdered(userService.getCurrentUser());
        model.addAttribute("plantCares", plantCareList);
        return "plant_types/list";
    }

    @GetMapping("plant_types/details/{name}")
    public String getDetails(@PathVariable String name, Model model) {
        PlantCare plantCare = plantCareService.findByScientific(name);
        model.addAttribute("plantCare", plantCare);
        return "plant_types/details";
    }

    //Edit Plant

    @GetMapping("plants/edit/{name}")
    public String editPlant(@PathVariable String name, Model model) {
        Plant plant = plantService.findByNameWithDates(name);
        model.addAttribute("plant", plant);
        model.addAttribute("sunSituations", SunSituation.values());
        model.addAttribute("plantCares", plantCareService.findAll());
        model.addAttribute("edit", true);
        return "plants/edit";
    }

    @PostMapping("plants/edit/{name}")
    public String editPlantSubmit(@PathVariable String name, @Valid Plant plant, BindingResult bindingResult, Model model) {
        bindingResult = plantService.validatePlant(plant, bindingResult);

        if (bindingResult.hasFieldErrors("acquisition") || bindingResult.hasFieldErrors("watered") || bindingResult.hasFieldErrors("notes")) {
            model.addAttribute("plant", plant);
            model.addAttribute("sunSituations", SunSituation.values());
            model.addAttribute("plantCares", plantCareService.findAll());
            model.addAttribute("edit", true);
            return "plants/edit";
        }
        plantService.save(plant);
        return "redirect:/plants/details/" + name;
    }

    //Edit Plant Types

    @GetMapping("plant_types/edit/{name}")
    public String editPlantType(@PathVariable String name, Model model) {
        PlantCare plantCare = plantCareService.findByScientific(name);
        model.addAttribute("plantCare", plantCare);
        model.addAttribute("sunSituations", SunSituation.values());
        return "plant_types/edit";
    }

    @PostMapping("plant_types/edit/{name}")
    public String editPlantTypeSubmit(@PathVariable String name, @Valid PlantCare plantCare, BindingResult bindingResult, Model model) {
        bindingResult = plantCareService.validatePlantCare(plantCare, bindingResult);

        if (bindingResult.hasFieldErrors("water_cycle") || bindingResult.hasFieldErrors("water_amount")) {
            model.addAttribute("plantCare", plantCare);
            model.addAttribute("sunSituations", SunSituation.values());
            return "plant_types/edit";
        }

        plantCareService.update(plantCare, name);
        return "redirect:/plant_types/details/" + plantCare.getScientific();
    }
}
