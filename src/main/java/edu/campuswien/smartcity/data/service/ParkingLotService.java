package edu.campuswien.smartcity.data.service;

import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.entity.TimeBasedData;
import edu.campuswien.smartcity.data.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotService extends CrudService<ParkingLot, Long> {

    private final ParkingLotRepository repository;
    private final TimeBasedDataService timeBasedDataService;

    @Autowired
    public ParkingLotService(ParkingLotRepository repository, TimeBasedDataService timeBasedDataService) {
        this.repository = repository;
        this.timeBasedDataService = timeBasedDataService;
    }

    @Override
    protected JpaRepository<ParkingLot, Long> getRepository() {
        return this.repository;
    }

    public List<ParkingLot> list() {
        return this.getRepository().findAll();
    }

    @Override
    public ParkingLot update(ParkingLot entity) {
        if(entity.getLastUpdatedTime() != null) {
            entity.setLastUpdatedTime(LocalDateTime.now());
        }
        if(entity.getStartId() == null) {
            entity.setStartId(0);
        }
        if(entity.getNumberOfOccupiedAtStart() == null) {
            entity.setNumberOfOccupiedAtStart(0);
        }

        return this.getRepository().saveAndFlush(entity);
    }

    public void delete(ParkingLot entity) {
        this.getRepository().deleteById(entity.getId());
    }

    public List<TimeBasedData> findAllTimeBased4Occupied(ParkingLot parkingLot) {
        if(parkingLot.getId() == null) {
            return new ArrayList<>();
        }

        return timeBasedDataService.findAllByParentIdAndParentFieldName(parkingLot.getId(), ParkingLot.FIELD_NAME_HOW_LONG_PARKED);
    }

    public List<TimeBasedData> findAllTimeBased4Request(ParkingLot parkingLot) {
        if(parkingLot.getId() == null) {
            return new ArrayList<>();
        }

        return timeBasedDataService.findAllByParentIdAndParentFieldName(parkingLot.getId(), ParkingLot.FIELD_NAME_HOW_MANY_CHANGED);
    }

    public boolean removeAllTimeBasedData(ParkingLot parkingLot) {
        return timeBasedDataService.removeAll(parkingLot);
    }
}
