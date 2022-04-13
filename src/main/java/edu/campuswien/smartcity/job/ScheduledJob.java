package edu.campuswien.smartcity.job;

import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class ScheduledJob {

    private ScheduledExecutorService executorService;

    public abstract boolean start();

    public abstract boolean stop();

    protected void resetTimer() {
        if(executorService != null) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
            executorService = null;
        }
    }

    public void schedule(TimerTask task, long delay) {
        if (executorService == null) {
            executorService = Executors.newScheduledThreadPool(2);
        }
        executorService.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    public int random(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
