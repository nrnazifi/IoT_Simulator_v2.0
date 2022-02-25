package edu.campuswien.smartcity.data.service;

import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;
import java.util.UUID;

@Service
public class ParkingLotService extends CrudService<ParkingLot, UUID> {

    private ParkingLotRepository repository;
    public ParkingLotService(@Autowired ParkingLotRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<ParkingLot, UUID> getRepository() {
        return this.repository;
    }

    public List<ParkingLot> list() {
        return this.getRepository().findAll();
    }
}
