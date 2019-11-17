package group11.leafalone.Plant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class PlantCare {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    // colloquial name
    @NotNull(message = "is required")
    private String colloquial;

    // scientific
    @NotNull(message = "is required")
    private String scientific;

    // desired amount of light
    private SunSituation sunSituation;

    // how often the plant needs to be watered
    @NotNull(message = "is required")
    private int waterCycle;

    // amount of water needed
    //in ml?
    @NotNull(message = "is required")
    private int waterAmount;

    // advice on the soil
    private String soilAdvice;

    // plain text description
    private String description;

    // reference to the contributor
    // TODO link to Contributor Model, needs database
    private String conid;

    //probably needed for contributePlantController
    public PlantCare(){

    }

    public PlantCare(String colloquial, String scientific, String plantPicture, SunSituation sunSituation, int waterCycle, int waterAmount, String soilAdvice, String description, String id) {
        this.colloquial = colloquial;
        this.scientific = scientific;
        this.sunSituation = sunSituation;
        this.waterCycle = waterCycle;
        this.waterAmount = waterAmount;
        this.soilAdvice = soilAdvice;
        this.description = description;
        this.conid = id;
    }
  
    public String getColloquial() {
        return colloquial;
    }

    public String getScientific() {
        return scientific;
    }

    public SunSituation getSunSituation() {
        return sunSituation;
    }

    public int getWaterCycle() {
        return waterCycle;
    }

    public int getWaterAmount() {
        return waterAmount;
    }

    public String getSoilAdvice() {
        return soilAdvice;
    }

    public String getDescription() {
        return description;
    }

    public String getConid(){return conid;
    }
    public Long getId() {
        return id;
    }

}
