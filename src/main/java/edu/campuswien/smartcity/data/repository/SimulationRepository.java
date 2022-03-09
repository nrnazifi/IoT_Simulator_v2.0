package edu.campuswien.smartcity.data.repository;

import edu.campuswien.smartcity.data.entity.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationRepository  extends JpaRepository<Simulation, Long> {
}
