package edu.campuswien.smartcity.data.service;

import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.entity.TimeBasedData;
import edu.campuswien.smartcity.data.repository.TimeBasedDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class TimeBasedDataService extends CrudService<TimeBasedData, Long> {

    private final TimeBasedDataRepository repository;
    public TimeBasedDataService(@Autowired TimeBasedDataRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<TimeBasedData, Long> getRepository() {
        return this.repository;
    }

    @Override
    public TimeBasedData update(TimeBasedData entity) {
        return this.getRepository().saveAndFlush(entity);
    }

    public void delete(TimeBasedData entity) {
        this.getRepository().deleteById(entity.getId());
    }

    public boolean removeAll(ParkingLot parkingLot) {
        try {
            for (TimeBasedData time : findAllByParentId(parkingLot.getId())) {
                this.getRepository().deleteById(time.getId());
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<TimeBasedData> findAllByParentIdAndParentFieldName(long parentId, String parentFieldName) {
        return this.repository.findAllByParentIdAndParentFieldName(parentId, parentFieldName);
    }

    public List<TimeBasedData> findAllByParentId(long parentId) {
        return this.repository.findAllByParentId(parentId);
    }
}
