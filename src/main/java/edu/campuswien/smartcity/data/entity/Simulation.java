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

    @Column(columnDefinition = "boolean default true")
    private boolean scheduleNow = true;
    @Column
    private LocalDateTime scheduleTime;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

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

    @OneToMany(mappedBy = "simulation", targetEntity = ParkingLog.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ParkingLog> logs;
}
