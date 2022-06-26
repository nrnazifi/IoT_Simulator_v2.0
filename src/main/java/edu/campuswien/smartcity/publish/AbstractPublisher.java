package edu.campuswien.smartcity.publish;

import edu.campuswien.smartcity.data.entity.IRecord;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPublisher {

    private ScheduledExecutorService publishExecutorService;

    public AbstractPublisher() {
        if (publishExecutorService == null) {
            publishExecutorService = Executors.newScheduledThreadPool(100);
        }
    }

    protected void runner(Runnable task) {
        publishExecutorService.schedule(task, 0, TimeUnit.MILLISECONDS);
    }

    public abstract void publish(IRecord record);
}
