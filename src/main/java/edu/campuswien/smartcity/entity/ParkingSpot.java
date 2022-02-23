package edu.campuswien.smartcity.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(unique = true)
    // generated name as
    private String deviceId;

    @ManyToOne
    @JoinColumn()
    private ParkingLot parkingLot;
}
