package edu.campuswien.smartcity.data.repository;

import edu.campuswien.smartcity.data.entity.ParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, Long> {

}
