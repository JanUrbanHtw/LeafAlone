package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUser;
import group11.leafalone.Auth.LeafAloneUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PlantCareService {
    private PlantCareRepository plantCareRepository;
    private LeafAloneUserDetailsService userService;

    public PlantCareService(PlantCareRepository plantCareRepository, LeafAloneUserDetailsService userService) {
        this.plantCareRepository = plantCareRepository;
        this.userService = userService;
    }

    public Iterable<PlantCare> findAll() {
        return plantCareRepository.findAll();
    }

    public BindingResult validatePlantCare(PlantCare plantCare, BindingResult bindingResult){
        try {
            if (plantCare.getColloquial() != null) if (plantCare.getColloquial().length() > 255) {
                FieldError error = new FieldError("plantCare", "colloquial", "Required to be shorter than 256 characters");
                bindingResult.addError(error);
            }

            if (plantCare.getScientific() != null) if (plantCare.getScientific().length() > 255) {
                FieldError error = new FieldError("plantCare", "scientific", "Required to be shorter than 256 characters");
                bindingResult.addError(error);
            }

            PlantCare repoPlantCare = plantCareRepository.findByScientific(plantCare.getScientific());
            if (repoPlantCare != null) {
                FieldError error = new FieldError("plantCare", "scientific", "Plant-Regimen already stored in database");
                bindingResult.addError(error);
            }

            if (plantCare.getSoilAdvice() != null) if (plantCare.getSoilAdvice().length() > 255) {
                FieldError error = new FieldError("plantCare", "soilAdvice", "Required to be shorter than 256 characters");
                bindingResult.addError(error);
            }

            if (plantCare.getDescription() != null) if (plantCare.getDescription().length() > 255) {
                FieldError error = new FieldError("plantCare", "description", "Required to be shorter than 256 characters");
                bindingResult.addError(error);
            }

        //occurs if user explicitly deletes number from number-fields in form -> input = "" -> fields = null
        } catch (NullPointerException e){}
        return bindingResult;
    }

    public void save(PlantCare plantCare){
        plantCare.setContributor(userService.getCurrentUser());
        plantCareRepository.save(plantCare);
    }

    public void update(PlantCare plantCare, String name) {
        PlantCare repoPlantCare;
        try {
            repoPlantCare = findByScientific(name);
        } catch (ResponseStatusException e) {
            return;
        }
        if (repoPlantCare != null) {

            repoPlantCare.setScientific(plantCare.getScientific());
            repoPlantCare.setColloquial(plantCare.getColloquial());
            repoPlantCare.setSunSituation(plantCare.getSunSituation());
            repoPlantCare.setWaterAmount(plantCare.getWaterAmount());
            repoPlantCare.setWaterCycle(plantCare.getWaterCycle());
            repoPlantCare.setSoilAdvice(plantCare.getSoilAdvice());
            repoPlantCare.setDescription(plantCare.getDescription());
            plantCareRepository.save(repoPlantCare);
            return;
        }
    }

    public List<PlantCare> findByLeafAloneUserOrdered(LeafAloneUser user) {
        return plantCareRepository.findByLeafAloneUserOrdered(user.getId());
    }

    public PlantCare findByScientific(String name) {
        return plantCareRepository.findByScientific(name);
    }

    public PlantCare findByColloquial(String name) {
        return plantCareRepository.findByColloquial(name);
    }
}
