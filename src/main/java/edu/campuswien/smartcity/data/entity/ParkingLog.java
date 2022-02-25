package edu.campuswien.smartcity.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ParkingLog {
    @Id
    @Column(nullable = false)
    private Long timestampId;

    @ManyToOne
    @JoinColumn
    private Simulation simulation;

    @ManyToOne
    @JoinColumn
    private ParkingSpot parkingSpot;

    @Column(nullable = false)
    private Boolean status;
}
