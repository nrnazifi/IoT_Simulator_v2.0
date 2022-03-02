package edu.campuswien.smartcity.data.service;

import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ParkingLotService extends CrudService<ParkingLot, Long> {

    private ParkingLotRepository repository;
    public ParkingLotService(@Autowired ParkingLotRepository repository) {
        this.repository = repository;
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

        return this.getRepository().save(entity);
    }

    public void delete(ParkingLot entity) {
        this.getRepository().deleteById(entity.getId());
    }
}
