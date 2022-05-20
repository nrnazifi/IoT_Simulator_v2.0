package edu.campuswien.smartcity.data.service;

import edu.campuswien.smartcity.data.entity.InternalLog;
import edu.campuswien.smartcity.data.repository.InternalLogRepository;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.time.LocalDateTime;

@Service
public class InternalLogger extends CrudService<InternalLog, Long> {
    private final InternalLogRepository repository;
    private final Environment env;

    @Autowired
    public InternalLogger(InternalLogRepository repository, Environment env) {
        this.repository = repository;
        this.env = env;
    }

    @Override
    protected JpaRepository<InternalLog, Long> getRepository() {
        return this.repository;
    }

    @Override
    public InternalLog update(InternalLog entity) {
        return this.getRepository().saveAndFlush(entity);
    }

    public void log(InternalLog info, Level level) {
        Level envLevel = Level.INFO;
        if (env.getProperty("logging.level.app.internal.db") != null) {
            envLevel = Level.valueOf(env.getProperty("logging.level.app.internal.db"));
        }

        if(level.toInt() >= envLevel.toInt()) {
            info.setTime(LocalDateTime.now());
            update(info);
        }
    }

}
