package edu.campuswien.smartcity.data.report;

import edu.campuswien.smartcity.data.enums.ReportAggregationType;

import java.time.LocalDate;

public interface RequestNumber {
    ReportAggregationType getType();
    LocalDate getDate();
    Integer getWeek();
    String getDayOfWeek();
    Integer getValue();
}
