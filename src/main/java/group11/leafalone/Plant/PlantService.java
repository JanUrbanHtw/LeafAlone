package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUser;
import group11.leafalone.Auth.LeafAloneUserDetailsService;
import group11.leafalone.LeafAloneUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PlantService {
    private PlantRepository plantRepository;
    private LeafAloneUserDetailsService userService;

    public PlantService (PlantRepository plantRepository, LeafAloneUserDetailsService userService) {
        this.plantRepository = plantRepository;
        this.userService = userService;
    }

    public void save(Plant plant) {
        LocalDateTime today = LocalDateTime.now();
        if(plant.getAcquisition() == null) {
            plant.setAcquisition(today);
        }
        if(plant.getWatered() == null) {
            plant.setWatered(today);
        }
        plant.setNextWatering(calculateNextWatering(plant, plant.getWatered()));

        Plant repoPlant;
        try {
            repoPlant = findByName(plant.getName());
        } catch (ResponseStatusException e) {
            plant.setLeafAloneUser(userService.getCurrentUser());
            plantRepository.save(plant);
            return;
        }
        if(repoPlant != null) {
            repoPlant.setPlantCare(plant.getPlantCare());
            repoPlant.setSun(plant.getSun());
            repoPlant.setAcquisition(plant.getAcquisition());
            repoPlant.setWatered(plant.getWatered());
            repoPlant.setNotes(plant.getNotes());
            repoPlant.setNextWatering(plant.getNextWatering());
            plantRepository.save(repoPlant);
            return;
        }

    }

    public List<Plant> findByLeafAloneUser(LeafAloneUser leafAloneUser){
        return plantRepository.findByLeafAloneUser(leafAloneUser);
    }

    public BindingResult validatePlant(Plant plant, BindingResult bindingResult) {
        if (plant.getName() != null) if (plant.getName().length() > 255) {
            FieldError error = new FieldError("plant", "name", "Required to be shorter than 256 characters");
            bindingResult.addError(error);
        }

        List<String> savedPlants = plantRepository.findNamesByLeafAloneUser(userService.getCurrentUser());
        if(plant.getName() != null) if (savedPlants.contains(plant.getName())) {
            FieldError error = new FieldError("plant", "name", "Name already in use");
            bindingResult.addError(error);
        }

        LocalDateTime today = LocalDateTime.now();
        try {
            plant.setAcquisition(plant.getAcquisitionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            if (plant.getAcquisition().isAfter(today)) {
                FieldError error = new FieldError("plant", "acquisition", "Required to be before today or today");
                bindingResult.addError(error);
            }
        } catch (NullPointerException e) {
            plant.setAcquisition(null);
        }

        try {
            plant.setWatered(plant.getWateredDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            if (plant.getWatered().isAfter(today)) {
                FieldError error = new FieldError("plant", "watered", "Required to be before today or today");
                bindingResult.addError(error);
            }
        } catch (NullPointerException e) {
            plant.setWatered(null);
        }

        try {
            if (plant.getWatered().isBefore(plant.getAcquisition())) {
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

    public void waterPlant(String name) throws ResponseStatusException {
        Plant plant = findByName(name);
        LocalDateTime today = LocalDateTime.now();
        plant.setWatered(today);
        plant.setNextWatering(calculateNextWatering(plant, today));
        plantRepository.save(plant);
    }

    LocalDateTime calculateNextWatering(Plant plant, LocalDateTime watered) {
        if (plant.getPlantCare() == null || plant.getPlantCare().getWaterCycle() <= 0) {
            return null;
        }

        LocalDateTime nextWatering = watered.plusDays(plant.getPlantCare().getWaterCycle());
        return nextWatering;
//        try {
//            return LeafAloneUtil.stripHoursAndMinutes(Date.from(todayDateTime.atZone(ZoneId.systemDefault()).toInstant()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return Date.from(todayDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Plant findByName(String name) throws ResponseStatusException {
        if(name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional<Plant> plant = plantRepository.findByName(userService.getCurrentUser().getId(), name);
        if (!plant.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plant not found");
        }
        return plant.get();
    }

    public Plant findByNameWithDates(String name) throws ResponseStatusException {
        Plant plant = findByName(name);
        plant.setAcquisitionDate(Date.from(plant.getAcquisition().atZone(ZoneId.systemDefault()).toInstant()));
        plant.setWateredDate(Date.from(plant.getWatered().atZone(ZoneId.systemDefault()).toInstant()));
        return plant;
    }

    public List<Plant> findByLeafAloneUserOrderByNextWatering(LeafAloneUser user) {
        return plantRepository.findByLeafAloneUserOrderByNextWatering(user.getId());
    }

    public void deletePlant(String name) {
        Plant plant = findByName(name);
        plantRepository.deleteById(plant.getId());
    }

    public List<Plant> getPlantsToWaterToday() {
        try {
            return plantRepository.findByNextWatering(LeafAloneUtil.stripHoursAndMinutes(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return plantRepository.findByNextWatering(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
    }

    public List<Plant> getPlantsToWaterTodayOrderedByUser() {
        try {
            return plantRepository.findByNextWateringOrderedByUser(LeafAloneUtil.getTodayAtMidnight());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return plantRepository.findByNextWatering(LeafAloneUtil.getTodayRightNow());
    }

    public List<Plant> findPlantsWateredNextDaysByUser(LeafAloneUser user, int days) {
        List<Plant> plantList = findByLeafAloneUser(user);
        List<Plant> result = new ArrayList<>();
        LocalDate day = LocalDate.now().plusDays(days);

        for(Plant plant : plantList) {
            LocalDate nextWatering = plant.getNextWatering().toLocalDate();
            if(nextWatering.equals(day)) result.add(plant);
        }
        return result;
    }

    public Map<String, List> findPlantsWateredNextWeekByUser(LeafAloneUser user) {
        Map<String, List> lists = new LinkedHashMap<>();
        List<Plant> monday = findPlantsWateredNextDaysByUser(user, 1);
        List<Plant> tuesday = findPlantsWateredNextDaysByUser(user, 2);
        List<Plant> wednesday = findPlantsWateredNextDaysByUser(user, 3);
        List<Plant> thursday = findPlantsWateredNextDaysByUser(user, 4);
        List<Plant> friday = findPlantsWateredNextDaysByUser(user, 5);
        List<Plant> saturday = findPlantsWateredNextDaysByUser(user, 6);
        List<Plant> sunday = findPlantsWateredNextDaysByUser(user, 7);

        lists.put("Monday", monday);
        lists.put("Tuesday", tuesday);
        lists.put("Wednesday", wednesday);
        lists.put("Thursday", thursday);
        lists.put("Friday", friday);
        lists.put("Saturday", saturday);
        lists.put("Sunday", sunday);

        return lists;
    }

    public List<Plant> findPlantsWateredBeforeToday(LeafAloneUser user) {
        List<Plant> plantList = findByLeafAloneUser(user);
        List<Plant> result = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for(Plant plant : plantList) {
            LocalDate nextWatering = plant.getNextWatering().toLocalDate();
            if(nextWatering.isBefore(today) || nextWatering.equals(today)) result.add(plant);
        }
        return result;
    }
}
