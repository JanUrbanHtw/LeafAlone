package group11.leafalone.Plant;

public enum SunSituation {
    SUNNY("sunny"), DARK("dark"), SHADOW("shadow");

    private String name;

    private SunSituation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
