package group11.leafalone.Plant;

import group11.leafalone.Auth.LeafAloneUser;

public class PlantCareBuilder {
    private String colloquial;
    private String scientific;
    private SunSituation sunSituation;
    private int waterCycle;
    private int waterAmount;
    private String soilAdvice;
    private String description;
    private LeafAloneUser contributor;

    //public PlantCare(@NotEmpty(message = "Colloquial name is required") String colloquial,
    // @NotEmpty(message = "Scientific name is required") String scientific,
    // SunSituation sunSituation,
    // @Min(1) int waterCycle,
    // @Min(1) int waterAmount,
    // String soilAdvice,
    // String description,
    // String conid)

    public PlantCareBuilder(String colloquial, String scientific, SunSituation sunSituation, int waterCycle, int waterAmount, String soilAdvice, String description, LeafAloneUser contributor) {
        this.colloquial = colloquial;
        this.scientific = scientific;
        this.sunSituation = sunSituation;
        this.waterCycle = waterCycle;
        this.waterAmount = waterAmount;
        this.soilAdvice = soilAdvice;
        this.description = description;
        this.contributor = contributor;
    }

    public PlantCare build() {
        return new PlantCare(this.colloquial, this.scientific, this.sunSituation, this.waterCycle, this.waterAmount, this.soilAdvice, this.description, this.contributor);
    }

    public void colloquial(String colloquial) {
        this.colloquial = colloquial;
    }

    public void scientific(String scientific) {
        this.scientific = scientific;
    }

    public void sunSituation(SunSituation sunSituation) {
        this.sunSituation = sunSituation;
    }

    public void waterCycle(int waterCycle) {
        this.waterCycle = waterCycle;
    }

    public void waterAmount(int waterAmount) {
        this.waterAmount = waterAmount;
    }

    public void soilAdvice(String soilAdvice) {
        this.soilAdvice = soilAdvice;
    }

    public void description(String description) {
        this.description = description;
    }

    public void contributor(LeafAloneUser contributor) {
        this.contributor = contributor;
    }
}
