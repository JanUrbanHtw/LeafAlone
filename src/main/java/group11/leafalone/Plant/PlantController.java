package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUser;
import group11.leafalone.Auth.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
public class PlantController {

    private UserRepository userRepository;
    private PlantRepository plantRepository;
    private PlantCareRepository plantCareRepository;

    private PlantCare plantCare;

    public PlantController(UserRepository userRepository,
                           PlantRepository plantRepository,
                           PlantCareRepository plantCareRepository) {
        this.userRepository = userRepository;
        this.plantRepository = plantRepository;
        this.plantCareRepository = plantCareRepository;

    }

    //AddPlant

    @GetMapping("/plants/add")
    public String addPlantForm(Model model) {
        model.addAttribute("plant", new Plant());
        model.addAttribute("sunSituations", SunSituation.values());
        model.addAttribute("plantCares", plantCareRepository.findAll());
        return "plants/add";
    }

    @PostMapping("/plants/add")
    public String addPlantSubmit(@Valid Plant plant, BindingResult bindingResult, Model model) {
        if (plant.getName().length() > 255) {
            FieldError error = new FieldError("plant", "name", "Required to be shorter than 256 characters");
            bindingResult.addError(error);
        }

        Date today = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        try {
            if (plant.getAcquisition().after(today)) {
                FieldError error = new FieldError("plant", "acquisition", "Required to be before today or today");
                bindingResult.addError(error);
            }
        } catch (NullPointerException e) {
            plant.setAcquisition(today);
        }

        try {
            if (plant.getWatered().after(today)) {
                FieldError error = new FieldError("plant", "watered", "Required to be before today or today");
                bindingResult.addError(error);
            }
        } catch (NullPointerException e) {
            plant.setWatered(today);
        }

        if (plant.getWatered().before(plant.getAcquisition())) {
            FieldError error = new FieldError("plant", "watered", "Required to be after acquisition-date or acquisition-date");
            bindingResult.addError(error);
        }

        if (plant.getNotes() != null) if (plant.getNotes().length() > 255) {
            FieldError error = new FieldError("plant", "notes", "Required to be shorter than 256 characters");
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
            model.addAttribute("plant", plant);
            model.addAttribute("sunSituations", SunSituation.values());
            model.addAttribute("plantCares", plantCareRepository.findAll());
            return "plants/add";
        }
        plant.setLeafAloneUser(getCurrentUser());
        plantRepository.save(plant);
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
        if (plantCare != null) {
            if (plantCare.getColloquial() != null) if (plantCare.getColloquial().length() > 255) {
                FieldError error = new FieldError("plant", "colloquial", "Required to be shorter than 256 characters");
                bindingResult.addError(error);
            }

            if (plantCare.getScientific() != null) if (plantCare.getScientific().length() > 255) {
                FieldError error = new FieldError("plant", "scientific", "Required to be shorter than 256 characters");
                bindingResult.addError(error);
            }

            PlantCare repoPlantCare = plantCareRepository.findByScientific(plantCare.getScientific());
            if (repoPlantCare != null) {
                FieldError error = new FieldError("plant", "scientific", "Plant-Regimen already stored in database");
                bindingResult.addError(error);
            }

            if (plantCare.getSoilAdvice() != null) if (plantCare.getSoilAdvice().length() > 255) {
                FieldError error = new FieldError("plant", "soilAdvice", "Required to be shorter than 256 characters");
                bindingResult.addError(error);
            }

            if (plantCare.getDescription() != null) if (plantCare.getDescription().length() > 255) {
                FieldError error = new FieldError("plant", "description", "Required to be shorter than 256 characters");
                bindingResult.addError(error);
            }
        }

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
        plantCare.setContributor(getCurrentUser());
        plantCareRepository.save(plantCare);
        this.plantCare = null;
        return "redirect:/?message=Thank you for your contribution.";
    }

    //List Plant

    @GetMapping("plants/list")
    public String getPlantList(Model model) {
        List<Plant> plantList = plantRepository.findByLeafAloneUser(getCurrentUser());
        model.addAttribute("plants", plantList);
        return "plants/list";

    }

    public LeafAloneUser getCurrentUser() {
        SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

    public Long getCurrentUserID() {
        return getCurrentUser().getId();
    }

}
