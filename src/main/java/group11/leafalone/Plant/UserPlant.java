package group11.leafalone.Plant;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "userPlant")
public class UserPlant {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name", unique = true)
    @NotEmpty(message = "Name is required")
    private String name;

    @Column(name = "plantCare")
    @NotEmpty(message = "Plant-Type is required")
    private String plantCare;
    //private PlantCare plantCare; //TODO change to PlantCares scientific attribute - foreign key business

    @Column(name = "sun")
    private SunSituation sun;

    @Column(name = "acquisition")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acquisition;

    @Column(name = "watered")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date watered;

    @Column(name = "notes")
    private String notes;

    @Column(name = "userid")
    private String userid;

    protected UserPlant() {}

    public UserPlant(@NotEmpty(message = "Name is required") String name, @NotEmpty(message = "Plant-Type is required") String plantCare, SunSituation sun, Date acquisition, Date watered, String notes, String userid) {
        this.name = name;
        this.plantCare = plantCare;
        this.sun = sun;
        this.acquisition = acquisition;
        this.watered = watered;
        this.notes = notes;
        this.userid = userid;
    }

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
