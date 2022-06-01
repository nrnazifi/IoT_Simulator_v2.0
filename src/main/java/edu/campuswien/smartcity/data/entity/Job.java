package edu.campuswien.smartcity.data.entity;

import edu.campuswien.smartcity.data.enums.JobStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Job extends AbstractEntity {

    @ManyToOne
    @JoinColumn
    private Simulation simulation;

    @Column
    private LocalDateTime jobStartTime;
    @Column
    private LocalDateTime jobEndTime;

    @Column
    private LocalDateTime simulationStartTime;
    @Column
    private LocalDateTime simulationEndTime;

    @Column
    private JobStatusEnum status;

    @OneToMany(mappedBy = "job", targetEntity = ParkingSpot.class, fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ParkingSpot> spots;


}
