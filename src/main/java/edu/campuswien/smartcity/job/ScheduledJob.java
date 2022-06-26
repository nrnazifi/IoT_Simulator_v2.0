package edu.campuswien.smartcity.job;

import com.vaadin.flow.router.NotFoundException;
import edu.campuswien.smartcity.data.enums.DistributionEnum;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.WeibullDistribution;

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
     * Generates an exponential random number (default distribution)
     * because the average of the duration in time series data is normally exponentially distributed
     */
    public double randomExponential(double mean) {
        ExponentialDistribution distribution = new ExponentialDistribution(mean);
        return distribution.sample(); //location is 0
    }

    public double randomNormal(double mean, double sd) {
        NormalDistribution distribution = new NormalDistribution(mean, sd);
        return distribution.sample();
    }

    public double randomWeibull(double alpha, double beta) {
        WeibullDistribution distribution = new WeibullDistribution(alpha, beta);
        return distribution.sample();
    }

    public double randomNumber(DistributionEnum distribution, double... args) {
        switch (distribution) {
            case Normal:
                return randomNormal(args[0], args[1]);
            case Exponential:
                return randomExponential(args[0]);
            case Weibull:
                return randomWeibull(args[0], args[1]);
        }
        throw new NotFoundException("Distribution function not found!");
    }

}
