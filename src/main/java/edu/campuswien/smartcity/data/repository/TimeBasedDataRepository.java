package edu.campuswien.smartcity.data.repository;

import edu.campuswien.smartcity.data.entity.TimeBasedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeBasedDataRepository extends JpaRepository<TimeBasedData, Long> {

    public List<TimeBasedData> findAllByParentIdAndParentFieldName(long parentId, String parentFieldName);
    public List<TimeBasedData> findAllByParentId(long parentId);
}
