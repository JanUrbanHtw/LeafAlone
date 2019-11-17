package group11.leafalone.Plant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class UserPlant {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotNull(message = "is required")
    private String name;

    @NotNull(message = "is required")
    private String plantCare; //TODO change to PlantCares scientific attribute - foreign key business

    private SunSituation sun;

    private Date acquisition;

    private Date watered;

    private String notes;

    private String userid;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlantCare() {
        return plantCare;
    }

    public void setPlantCare(String plantCare) {
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
