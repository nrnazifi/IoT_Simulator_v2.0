package edu.campuswien.smartcity.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private String name;

    @Column(nullable = false)
    private String description;

    @Column
    private Integer capacity;

    @Column(nullable = false)
    //Number of occupied spots at the beginning
    private Integer numberOfOccupiedAtStart;

    @OneToMany(mappedBy = "parkingLot", targetEntity = ParkingSpot.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ParkingSpot> spots;
}
