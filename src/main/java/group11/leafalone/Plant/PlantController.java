package group11.leafalone.Plant;

import group11.leafalone.Auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class PlantController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private PlantCareRepository plantCareRepository;

    @GetMapping("/plants/add")
    public String addPlantForm(Model model) {
        model.addAttribute("plant", new Plant());
        model.addAttribute("sunSituations", SunSituation.values());
        model.addAttribute("plantCares", plantCareRepository.findAll());
        return "plants/add";
    }

    @PostMapping("/plants/add")
    public String addPlantSubmit(@Valid Plant plant, BindingResult bindingResult, Model model) {
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
        if (bindingResult.hasErrors() || bindingResult.hasFieldErrors()) {
            model.addAttribute("plant", plant);
            model.addAttribute("sunSituations", SunSituation.values());
            model.addAttribute("plantCares", plantCareRepository.findAll());
            return "plants/add";
        }
        plant.setLeafAloneUser(getCurrentUser());
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
        if (bindingResult.hasErrors()) {
            model.addAttribute("sunSituations", SunSituation.values());
            return "plants/contribute";
        }
        plantCare.setContributor(getCurrentUser());
        plantCareRepository.save(plantCare);
        return "redirect:../about";
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
