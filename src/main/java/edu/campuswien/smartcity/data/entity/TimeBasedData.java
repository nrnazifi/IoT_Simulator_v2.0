package edu.campuswien.smartcity.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class TimeBasedData extends AbstractEntity {

    @OneToOne(mappedBy = "howLongParked", cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    private ParkingLot parkingLot_howLongParked;

    @OneToOne(mappedBy = "howManyChanged", cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    private ParkingLot parkingLot_howManyChanged;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private TimeTypeEnum timeType;

    @Column
    private Integer averageDay = 0;
    @Column
    private Integer averageNight = 0;

    @Column
    private Integer workdayDay = 0;
    @Column
    private Integer workdayNight = 0;
    @Column
    private Integer weekendDay = 0;
    @Column
    private Integer weekendNight = 0;
    @Column
    private Integer holidayDay = 0;
    @Column
    private Integer holidayNight = 0;

    @Column
    private Integer mondayDay = 0;
    @Column
    private Integer mondayNight = 0;
    @Column
    private Integer tuesdayDay = 0;
    @Column
    private Integer tuesdayNight = 0;
    @Column
    private Integer wednesdayDay = 0;
    @Column
    private Integer wednesdayNight = 0;
    @Column
    private Integer thursdayDay = 0;
    @Column
    private Integer thursdayNight = 0;
    @Column
    private Integer fridayDay = 0;
    @Column
    private Integer fridayNight = 0;
    @Column
    private Integer saturdayDay = 0;
    @Column
    private Integer saturdayNight = 0;
    @Column
    private Integer sundayDay = 0;
    @Column
    private Integer sundayNight = 0;
}
