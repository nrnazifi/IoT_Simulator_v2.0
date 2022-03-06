package edu.campuswien.smartcity.data.repository;

import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    public List<ParkingSpot> findAllByParkingLot(ParkingLot parkingLot);
}
