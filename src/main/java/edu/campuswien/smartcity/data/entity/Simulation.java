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
    private String Name;

    @Column
    private String description;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "simulation", targetEntity = ParkingLog.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ParkingLog> logs;
}
