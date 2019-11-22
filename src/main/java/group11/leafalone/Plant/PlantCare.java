package group11.leafalone.Plant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class PlantCare {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    // colloquial name
    @NotEmpty(message = "Colloquial name is required")
    private String colloquial;

    // scientific
    @NotEmpty(message = "Scientific name is required")
    private String scientific;

    // desired amount of light
    private SunSituation sunSituation;

    // how often the plant needs to be watered
    @Min(1)
    private int waterCycle;

    // amount of water needed
    //in ml?
    @Min(1)
    private int waterAmount;

    // advice on the soil
    private String soilAdvice;

    // plain text description
    private String description;

    // reference to the contributor
    // TODO link to Contributor Model, needs database
    private String conid;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setColloquial(String colloquial) {
        this.colloquial = colloquial;
    }

    public void setScientific(String scientific) {
        this.scientific = scientific;
    }

    public void setSunSituation(SunSituation sunSituation) {
        this.sunSituation = sunSituation;
    }

    public void setWaterCycle(int waterCycle) {
        this.waterCycle = waterCycle;
    }

    public void setWaterAmount(int waterAmount) {
        this.waterAmount = waterAmount;
    }

    public void setSoilAdvice(String soilAdvice) {
        this.soilAdvice = soilAdvice;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setConid(String conid) {
        this.conid = conid;
    }
}
