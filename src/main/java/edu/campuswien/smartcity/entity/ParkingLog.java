package edu.campuswien.smartcity.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ParkingLog {
    @Id
    @Column
    private Long timestampId;

    @ManyToOne
    @JoinColumn
    private Simulation simulation;

    @ManyToOne
    @JoinColumn
    private ParkingSpot parkingSpot;

    @Column
    private Boolean status;
}
