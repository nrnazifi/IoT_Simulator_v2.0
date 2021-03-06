package edu.campuswien.smartcity.data.service;

import edu.campuswien.smartcity.data.entity.Job;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.entity.ParkingSpot;
import edu.campuswien.smartcity.data.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingSpotService  extends CrudService<ParkingSpot, Long> {

    private final ParkingSpotRepository repository;
    public ParkingSpotService(@Autowired ParkingSpotRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<ParkingSpot, Long> getRepository() {
        return this.repository;
    }

    public List<ParkingSpot> list() {
        return this.getRepository().findAll();
    }

    public List<ParkingSpot> list(ParkingLot parkingLot) {
        return this.repository.findAllByParkingLot(parkingLot);
    }

    public List<ParkingSpot> list(Job job) {
        return this.repository.findAllByJob(job);
    }

    public boolean removeAll(ArrayList<ParkingSpot> spots) {
        try {
            for (ParkingSpot spot : spots) {
                this.getRepository().deleteById(spot.getId());
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public ParkingSpot update(ParkingSpot entity) {
        return this.getRepository().saveAndFlush(entity);
    }

    public void delete(ParkingSpot entity) {
        this.getRepository().deleteById(entity.getId());
    }
}
