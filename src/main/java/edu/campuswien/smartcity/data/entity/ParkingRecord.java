package edu.campuswien.smartcity.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ParkingRecord extends AbstractEntity {

    @Column(nullable = false)
    private Long jobId;

    @Column(nullable = false)
    private Long spotId;

    @Column(nullable = false)
    private String deviceId;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private Long durationSeconds;

    @Column(nullable = false)
    private Boolean status;
}
