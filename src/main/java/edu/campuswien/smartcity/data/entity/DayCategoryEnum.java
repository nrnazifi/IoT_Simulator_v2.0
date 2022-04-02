package edu.campuswien.smartcity.data.entity;

public enum DayCategoryEnum {
    Generally(0, "Generally"),
    WorkRestTime(1, "Work time and rest time"),
    WeekDays(2, "Days of week");

    private int id;
    private String description;

    DayCategoryEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
