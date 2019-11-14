package group11.leafalone;

import java.util.Date;

public class Plant {

    private String name;

    private PlantDescription type;

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

    public PlantDescription getType() {
        return type;
    }

    public void setType(PlantDescription type) {
        this.type = type;
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
