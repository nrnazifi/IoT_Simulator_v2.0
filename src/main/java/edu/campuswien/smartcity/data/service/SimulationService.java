package edu.campuswien.smartcity.data.service;

import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.entity.Simulation;
import edu.campuswien.smartcity.data.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class SimulationService extends CrudService<Simulation, Long> {
    private SimulationRepository repository;

    public SimulationService(@Autowired SimulationRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Simulation, Long> getRepository() {
        return this.repository;
    }

    public List<Simulation> list() {
        return this.getRepository().findAll();
    }

    @Override
    @Transactional
    public Simulation update(Simulation entity) {
        return this.getRepository().saveAndFlush(entity);
    }

    public void delete(Simulation entity) {
        this.getRepository().deleteById(entity.getId());
    }
}
