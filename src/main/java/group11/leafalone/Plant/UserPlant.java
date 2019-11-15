package group11.leafalone.Plant;

import java.util.Date;

public class UserPlant {

    private String name;

    private PlantCare plantCare;

    private SunSituation sun;

    private Date acquisition;

    private Date watered;

    private String notes;

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlantCare getPlantCare() {
        return plantCare;
    }

    public void setPlantCare(PlantCare plantCare) {
        this.plantCare = plantCare;
    }

    public SunSituation getSun() {
        return sun;
    }

    public void setSun(SunSituation sun) {
        this.sun = sun;
    }

    public Date getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(Date acquisition) {
        this.acquisition = acquisition;
    }

    public Date getWatered() {
        return watered;
    }

    public void setWatered(Date watered) {
        this.watered = watered;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getId() {
        return id;
    }
}
