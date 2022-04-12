package edu.campuswien.smartcity.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ParkingSpot extends AbstractEntity {

    @Column
    private String deviceId;//TODO generated name as

    @ManyToOne
    private ParkingLot parkingLot;

    @ManyToOne
    private Job job;

    @Column
    private Boolean status;

    @Column
    private LocalDateTime lastChangedTime;
}
