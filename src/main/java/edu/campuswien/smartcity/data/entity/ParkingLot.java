package edu.campuswien.smartcity.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class ParkingLot extends AbstractEntity {
    public static final String FIELD_NAME_HOW_LONG_PARKED = "HowLongParked";
    public static final String FIELD_NAME_HOW_MANY_CHANGED = "HowManyChanged";

    @Column(unique = true, nullable = false)
    private String name;

    /**
     * General details about parking to describe a real one
     */
    @Column
    private String description;

    @Column(nullable = false)
    private Integer capacity;

    /**
     * by default the first id of spots is started from 0, but user can determines startId for spots
     */
    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer startId = 0;

    /**
     * Number of occupied spots at the beginning
     * How many spots are occupied by default when the simulator is started?
     */
    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer numberOfOccupiedAtStart = 0;

    @Column
    private LocalTime daylight;
    @Column
    private LocalTime darkness;

    @Column
    private LocalDateTime lastUpdatedTime;

    @OneToMany(mappedBy = "parkingLot", targetEntity = ParkingSpot.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ParkingSpot> spots;//TODO prevent getter?
}
