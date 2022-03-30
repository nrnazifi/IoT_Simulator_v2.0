package edu.campuswien.smartcity.data.service;

import edu.campuswien.smartcity.data.entity.TimeBasedData;
import edu.campuswien.smartcity.data.repository.TimeBasedDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.artur.helpers.CrudService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimeBasedDataService extends CrudService<TimeBasedData, Long> {

    private TimeBasedDataRepository repository;
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
}
