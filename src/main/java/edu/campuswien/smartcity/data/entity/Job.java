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
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private JobStatusEnum status;

    //@Column
    //private Integer success/failed/pending

    @OneToMany(mappedBy = "job", targetEntity = ParkingSpot.class, fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ParkingSpot> spots;

    /*public void removeAllSpots() {
        ArrayList<ParkingSpot> spotList = new ArrayList<>(spots);
        for (ParkingSpot spot : spotList) {
            spot.setJob(null);
            this.spots.remove(spot);
        }
        spotList.clear();
    }*/

}
