package group11.leafalone.Plant;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
public class UserPlant {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Plant-Type is required")
    private String plantCare;
    //private PlantCare plantCare; //TODO change to PlantCares scientific attribute - foreign key business

    private SunSituation sun;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acquisition;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date watered;

    private String notes;

    private String userid;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlantCare() {
        return plantCare;
    }

    public SunSituation getSun() {
        return sun;
    }

    public Date getAcquisition() {
        return acquisition;
    }

    public Date getWatered() {
        return watered;
    }

    public String getNotes() {
        return notes;
    }

    public String getUserid() {
        return userid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlantCare(String plantCare) {
        this.plantCare = plantCare;
    }

    public void setSun(SunSituation sun) {
        this.sun = sun;
    }

    public void setAcquisition(Date acquisition) {
        this.acquisition = acquisition;
    }

    public void setWatered(Date watered) {
        this.watered = watered;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
