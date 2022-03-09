package edu.campuswien.smartcity.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Simulation extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn
    private ParkingLot parkingLot;

    @Column
    private String description;

    @Column
    private LocalDateTime scheduleTime;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column(columnDefinition = "double default 1.0")
    private double timeUnit = 1.0;

    @OneToMany(mappedBy = "simulation", targetEntity = ParkingLog.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ParkingLog> logs;
}
