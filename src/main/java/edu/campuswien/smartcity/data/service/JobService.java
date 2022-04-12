package edu.campuswien.smartcity.data.service;

import edu.campuswien.smartcity.data.entity.Job;
import edu.campuswien.smartcity.data.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.artur.helpers.CrudService;

import java.util.List;

@Service
public class JobService extends CrudService<Job, Long> {
    private final JobRepository repository;

    public JobService(@Autowired JobRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Job, Long> getRepository() {
        return this.repository;
    }

    public List<Job> list() {
        return this.getRepository().findAll();
    }

    @Override
    public Job update(Job entity) {
        return this.getRepository().saveAndFlush(entity);
    }

    public void delete(Job entity) {
        this.getRepository().deleteById(entity.getId());
    }
}
