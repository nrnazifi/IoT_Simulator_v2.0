package edu.campuswien.smartcity.data.repository;

import edu.campuswien.smartcity.data.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
}
