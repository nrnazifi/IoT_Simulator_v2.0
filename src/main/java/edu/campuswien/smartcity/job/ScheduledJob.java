package edu.campuswien.smartcity.job;

import edu.campuswien.smartcity.config.Constants;
import org.apache.commons.math3.distribution.ExponentialDistribution;

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
                if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
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
            executorService = Executors.newScheduledThreadPool(100);
        }
        executorService.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    public int random(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * Generates an exponential random number, because the average of the duration in time series data is normally exponentially distributed
     */
    public double randomExponential(double mean) {
        ExponentialDistribution distribution = new ExponentialDistribution(Constants.RANDOM_GENERATOR_ALGORITHM, mean);

        return distribution.sample() + 1; //location is 1
    }

}
