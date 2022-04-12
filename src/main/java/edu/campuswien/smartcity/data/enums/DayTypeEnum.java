package edu.campuswien.smartcity.data.enums;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public enum DayTypeEnum {
    General(0, "General", DayCategoryEnum.Generally, "General average"),
    Workday(1, "Workday", DayCategoryEnum.WorkRestTime, "Work days"),
    Weekend(2, "Weekend", DayCategoryEnum.WorkRestTime, "Weekends"),
    Holiday(3, "Holiday", DayCategoryEnum.WorkRestTime, "Holidays"),
    Monday(4, "Monday", DayCategoryEnum.WeekDays, "Monday"),
    Tuesday(5, "Tuesday", DayCategoryEnum.WeekDays, "Tuesday"),
    Wednesday(6, "Wednesday", DayCategoryEnum.WeekDays, "Wednesday"),
    Thursday(7, "Thursday", DayCategoryEnum.WeekDays, "Thursday"),
    Friday(8, "Friday", DayCategoryEnum.WeekDays, "Friday"),
    Saturday(9, "Saturday", DayCategoryEnum.WeekDays, "Saturday"),
    Sunday(10, "Sunday", DayCategoryEnum.WeekDays, "Sunday");

    private int id;
    private String name;
    private String description;
    private DayCategoryEnum category;

    DayTypeEnum(int id, String name, DayCategoryEnum category, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public DayCategoryEnum getCategory() {
        return category;
    }

    public static List<DayTypeEnum> getByCategory(DayCategoryEnum category) {
        List<DayTypeEnum> result = new ArrayList<>();
        for (DayTypeEnum type : DayTypeEnum.values()) {
            if (type.category.equals(category)) {
                result.add(type);
            }
        }
        return result;
    }

    public static DayTypeEnum convertDayOfWeek(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return DayTypeEnum.Monday;
            case TUESDAY:
                return DayTypeEnum.Tuesday;
            case WEDNESDAY:
                return DayTypeEnum.Wednesday;
            case THURSDAY:
                return DayTypeEnum.Thursday;
            case FRIDAY:
                return DayTypeEnum.Friday;
            case SATURDAY:
                return DayTypeEnum.Saturday;
            case SUNDAY:
                return DayTypeEnum.Sunday;
        }
        throw new RuntimeException(String.format("Unknown Type Exception (%s) in convertDayOfWeek()", dayOfWeek.name()));
    }
}
