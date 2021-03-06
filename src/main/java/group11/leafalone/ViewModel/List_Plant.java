package group11.leafalone.ViewModel;

import group11.leafalone.Plant.Plant;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class List_Plant {
    public final static String dateFormat = "dd MMMM yyyy";
    private String name;
    private String type;
    private String nextWateringDate;
    private boolean needsWater;
    private boolean wrongSun;

    public List_Plant(Plant plant) {
        this.name = plant.getName();
        this.type = plant.getPlantCare().getColloquial();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        this.nextWateringDate = sdf.format(Date.from(plant.getNextWatering().atZone(ZoneId.systemDefault()).toInstant()));
        this.needsWater = LocalDateTime.now().isAfter(plant.getNextWatering());
        this.wrongSun = !(plant.getSun().getName().equals(plant.getPlantCare().getSunSituation().getName()));
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

    public String getNextWateringDate() {
        return nextWateringDate;
    }

    public boolean isNeedsWater() {
        return needsWater;
    }

    public boolean isWrongSun() {
        return wrongSun;
    }
}
