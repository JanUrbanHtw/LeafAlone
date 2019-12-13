package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUser;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "user_plant")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Name is required")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plantcare_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PlantCare plantCare;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LeafAloneUser leafAloneUser;

    protected Plant() {
    }

    public Plant(@NotEmpty(message = "Name is required") String name, PlantCare plantCare, SunSituation sun, Date acquisition, Date watered, String notes, LeafAloneUser leafAloneUser) {
        this.name = name;
        this.plantCare = plantCare;
        this.sun = sun;
        this.acquisition = acquisition;
        this.watered = watered;
        this.notes = notes;
        this.leafAloneUser = leafAloneUser;
    }

    public static class Builder {
        private Long id;
        private String name;
        private PlantCare plantCare;
        private SunSituation sun;
        private Date acquisition;
        private Date watered;
        private String notes;
        private LeafAloneUser leafAloneUser;

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPlantCare(PlantCare plantCare) {
            this.plantCare = plantCare;
            return this;
        }

        public Builder withSun(SunSituation sun) {
            this.sun = sun;
            return this;
        }

        public Builder withAcquisition(Date acquisition) {
            this.acquisition = acquisition;
            return this;
        }

        public Builder withWatered(Date watered) {
            this.watered = watered;
            return this;
        }

        public Builder withNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder withLeafAloneUser(LeafAloneUser leafAloneUser) {
            this.leafAloneUser = leafAloneUser;
            return this;
        }

        public Plant build() {
            Plant plant = new Plant();
            plant.setId(this.id);
            plant.setName(this.name);
            plant.setPlantCare(this.plantCare);
            plant.setSun(this.sun);
            plant.setAcquisition(this.acquisition);
            plant.setWatered(this.watered);
            plant.setNotes(this.notes);
            plant.setLeafAloneUser(this.leafAloneUser);
            return plant;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PlantCare getPlantCare() {
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

    public LeafAloneUser getLeafAloneUser() {
        return leafAloneUser;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlantCare(PlantCare plantCare) {
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

    public void setLeafAloneUser(LeafAloneUser leafAloneUser) {
        this.leafAloneUser = leafAloneUser;
    }
}
