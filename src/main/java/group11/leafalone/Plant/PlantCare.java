package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUser;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "plantCare")
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
    @Column(name = "sunSituation")
    private SunSituation sunSituation;

    // how often the plant needs to be watered
    @Column(name = "waterCycle")
    @Min(value = 1, message = "must be greater than 0")
    private int waterCycle;

    // amount of water needed
    //in ml?
    @Column(name = "waterAmount")
    @Min(value = 1, message = "must be greater than 0")
    private int waterAmount;

    // advice on the soil
    @Column(name = "soilAdvice")
    @NotEmpty(message = "soil advice is required")
    private String soilAdvice;

    // plain text description
    @Column(name = "description")
    @NotEmpty(message = "description is required")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contributor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LeafAloneUser contributor;

    protected PlantCare() {
    }

    public PlantCare(@NotEmpty(message = "Colloquial name is required") String colloquial, @NotEmpty(message = "Scientific name is required") String scientific, SunSituation sunSituation, @Min(1) int waterCycle, @Min(1) int waterAmount, String soilAdvice, String description, LeafAloneUser contributor) {
        this.colloquial = colloquial;
        this.scientific = scientific;
        this.sunSituation = sunSituation;
        this.waterCycle = waterCycle;
        this.waterAmount = waterAmount;
        this.soilAdvice = soilAdvice;
        this.description = description;
        this.contributor = contributor;
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
