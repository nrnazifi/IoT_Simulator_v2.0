package edu.campuswien.smartcity.data.repository;

import edu.campuswien.smartcity.data.entity.TimeBasedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeBasedDataRepository extends JpaRepository<TimeBasedData, Long> {
}
