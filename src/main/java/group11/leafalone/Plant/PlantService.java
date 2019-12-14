package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUser;
import group11.leafalone.Auth.LeafAloneUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class PlantService {
    private PlantRepository plantRepository;
    private LeafAloneUserDetailsService userService;

    public PlantService (PlantRepository plantRepository, LeafAloneUserDetailsService userService) {
        this.plantRepository = plantRepository;
        this.userService = userService;
    }

    public void save(Plant plant) {
        Date today = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        if(plant.getAcquisition() == null) {
            plant.setAcquisition(today);
        }
        if(plant.getWatered() == null) {
            plant.setWatered(today);
        }
        plant.setLeafAloneUser(userService.getCurrentUser());
        plantRepository.save(plant);
    }

    public List<Plant> findByLeafAloneUser(LeafAloneUser leafAloneUser){
        return plantRepository.findByLeafAloneUser(leafAloneUser);
    }

    public BindingResult validatePlant(Plant plant, BindingResult bindingResult) {
        if (plant.getName().length() > 255) {
            FieldError error = new FieldError("plant", "name", "Required to be shorter than 256 characters");
            bindingResult.addError(error);
        }

        List<String> savedPlants = plantRepository.findNamesByLeafAloneUser(userService.getCurrentUser());
        if(savedPlants.contains(plant.getName())) {
            FieldError error = new FieldError("plant", "name", "Name already in use");
            bindingResult.addError(error);
        }

        Date today = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        try {
            if (plant.getAcquisition().after(today)) {
                FieldError error = new FieldError("plant", "acquisition", "Required to be before today or today");
                bindingResult.addError(error);
            }
        } catch (NullPointerException e) {}

        try {
            if (plant.getWatered().after(today)) {
                FieldError error = new FieldError("plant", "watered", "Required to be before today or today");
                bindingResult.addError(error);
            }
        } catch (NullPointerException e) {}

        try {
            if (plant.getWatered().before(plant.getAcquisition())) {
                FieldError error = new FieldError("plant", "watered", "Required to be after acquisition-date or acquisition-date");
                bindingResult.addError(error);
            }
        } catch (NullPointerException e) {}

        if (plant.getNotes() != null) if (plant.getNotes().length() > 255) {
            FieldError error = new FieldError("plant", "notes", "Required to be shorter than 256 characters");
            bindingResult.addError(error);
        }

        return bindingResult;
    }

}
