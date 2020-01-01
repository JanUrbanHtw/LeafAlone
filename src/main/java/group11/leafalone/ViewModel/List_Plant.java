package group11.leafalone.ViewModel;

import group11.leafalone.Plant.Plant;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class List_Plant {
    private String name;
    private String type;
    private String wateringDate;
    private boolean needsWater;

    public List_Plant(Plant plant) {
        this.name = plant.getName();
        this.type = plant.getPlantCare().getColloquial();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        this.wateringDate = sdf.format(plant.getWatered());
        this.needsWater = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()).after(plant.getNextWatering());
    }

    public static List<List_Plant> plantListToListPlant(List<Plant> plants) {
        ArrayList<List_Plant> list = new ArrayList<>();
        for (Plant plant : plants) {
            list.add(new List_Plant(plant));
        }
        return list;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getWateringDate() {
        return wateringDate;
    }

    public boolean isNeedsWater() {
        return needsWater;
    }
}
