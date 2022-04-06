package edu.campuswien.smartcity.data.repository;

import edu.campuswien.smartcity.data.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
