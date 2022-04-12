package edu.campuswien.smartcity.data.service;

import edu.campuswien.smartcity.data.entity.ParkingRecord;
import edu.campuswien.smartcity.data.repository.ParkingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class ParkingRecordService extends CrudService<ParkingRecord, Long> {

    private ParkingRecordRepository repository;
    public ParkingRecordService(@Autowired ParkingRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<ParkingRecord, Long> getRepository() {
        return this.repository;
    }

    @Override
    public ParkingRecord update(ParkingRecord entity) {
        return this.getRepository().saveAndFlush(entity);
    }

}
