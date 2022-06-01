package edu.campuswien.smartcity.data.entity;

import edu.campuswien.smartcity.data.enums.DataFormatEnum;
import edu.campuswien.smartcity.data.enums.ProtocolEnum;
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

    @Column(columnDefinition = "boolean default true")
    private boolean scheduleNow = true; //true if job will be run immediately (now)
    @Column
    private LocalDateTime scheduleTime;

    @Column(columnDefinition = "boolean default false")
    private boolean customizeSimulationTime = false; //true if job must simulate data in another time (in the future/past)
    @Column
    private LocalDateTime simulationStartTime;
    @Column
    private LocalDateTime simulationEndTime;

    @Column(columnDefinition = "double default 1.0")
    private double timeUnit = 1.0;

    @Column(columnDefinition = "boolean default true")
    private boolean onDatabase = true;

    @Column(columnDefinition = "boolean default false")
    private boolean onServer = false;

    @Column
    private String endpointUri;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private ProtocolEnum protocol;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private DataFormatEnum dataFormat;

    @OneToMany(mappedBy = "simulation", targetEntity = Job.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Job> jobs;
}
