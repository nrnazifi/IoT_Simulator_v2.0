package edu.campuswien.smartcity.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class ParkingLot extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private Integer capacity;

    @Column
    //Number of occupied spots at the beginning
    private Integer numberOfOccupiedAtStart;

    @OneToMany(mappedBy = "parkingLot", targetEntity = ParkingSpot.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ParkingSpot> spots;
}
