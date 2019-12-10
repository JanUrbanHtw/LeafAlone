package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUser;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "plant_care")
public class PlantCare {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // colloquial name
    @Column(name = "colloquial")
    @NotEmpty(message = "Colloquial name is required")
    private String colloquial;

    // scientific
    @Column(name = "scientific", unique = true)
    @NotEmpty(message = "Scientific name is required")
    private String scientific;

    // desired amount of light
    @Column(name = "sun_situation")
    private SunSituation sunSituation;

    // how often the plant needs to be watered
    @Column(name = "water_cycle")
    @Min(value = 1, message = "Required to be greater than 0")
    private int waterCycle;

    // amount of water needed
    //in ml?
    @Column(name = "water_amount")
    @Min(value = 1, message = "Required to be greater than 0")
    private int waterAmount;

    // advice on the soil
    @Column(name = "soil_advice")
    @NotEmpty(message = "Soil advice is required")
    private String soilAdvice;

    // plain text description
    @Column(name = "description")
    @NotEmpty(message = "Description is required")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contributor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LeafAloneUser contributor;

    protected PlantCare() {
    }

    public PlantCare(@NotEmpty(message = "Colloquial name is required") String colloquial, @NotEmpty(message = "Scientific name is required") String scientific, SunSituation sunSituation, @Min(value = 1, message = "Required to be greater than 0") int waterCycle, @Min(value = 1, message = "Required to be greater than 0") int waterAmount, @NotEmpty(message = "Soil advice is required") String soilAdvice, @NotEmpty(message = "Description is required") String description, LeafAloneUser contributor) {
        this.colloquial = colloquial;
        this.scientific = scientific;
        this.sunSituation = sunSituation;
        this.waterCycle = waterCycle;
        this.waterAmount = waterAmount;
        this.soilAdvice = soilAdvice;
        this.description = description;
        this.contributor = contributor;
    }

    public static class Builder {
        private String colloquial;
        private String scientific;
        private SunSituation sunSituation;
        private int waterCycle;
        private int waterAmount;
        private String soilAdvice;
        private String description;
        private LeafAloneUser contributor;


        public Builder withColloquial(String colloquial) {
            this.colloquial = colloquial;
            return this;
        }

        public Builder withScientific(String scientific) {
            this.scientific = scientific;
            return this;
        }

        public Builder withSunSituation(SunSituation sunSituation) {
            this.sunSituation = sunSituation;
            return this;
        }

        public Builder withWaterCycle(int waterCycle) {
            this.waterCycle = waterCycle;
            return this;
        }

        public Builder withWaterAmount(int waterAmount) {
            this.waterAmount = waterAmount;
            return this;
        }

        public Builder withSoilAdvice(String soilAdvice) {
            this.soilAdvice = soilAdvice;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withContributor(LeafAloneUser contributor) {
            this.contributor = contributor;
            return this;
        }

        public PlantCare build() {
            PlantCare plantCare = new PlantCare();
            plantCare.setColloquial(this.colloquial);
            plantCare.setScientific(this.scientific);
            plantCare.setSunSituation(this.sunSituation);
            plantCare.setWaterCycle(this.waterCycle);
            plantCare.setWaterAmount(this.waterAmount);
            plantCare.setSoilAdvice(this.soilAdvice);
            plantCare.setDescription(this.description);
            plantCare.setContributor(this.contributor);
            return plantCare;
        }
    }

    public String getColloquial() {
        return colloquial;
    }

    public String getScientific() {
        return scientific;
    }

    public LeafAloneUser getContributor() {
        return contributor;
    }

    public void setContributor(LeafAloneUser contributor) {
        this.contributor = contributor;
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
}
