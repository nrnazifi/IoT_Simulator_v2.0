package edu.campuswien.smartcity.data.enums;

public enum DistributionEnum {
    Normal(0, "Normal"),
    Exponential(1, "Exponential"),
    Weibull(2, "Weibull");

    private int id;
    private String description;

    DistributionEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
