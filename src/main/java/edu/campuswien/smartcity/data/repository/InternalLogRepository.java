package edu.campuswien.smartcity.data.repository;

import edu.campuswien.smartcity.data.entity.InternalLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalLogRepository extends JpaRepository<InternalLog, Long> {
}
