package edu.campuswien.smartcity.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ParkingSpot extends AbstractEntity {

    @Column(unique = true, nullable = false)
    // generated name as
    private String deviceId;

    @ManyToOne
    @JoinColumn()
    private ParkingLot parkingLot;
}
