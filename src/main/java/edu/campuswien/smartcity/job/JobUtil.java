package edu.campuswien.smartcity.job;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class JobUtil {
    public static LocalDateTime getCurrentSimulationTime(LocalDateTime jobStartTime, LocalDateTime simStartTime, double timeUnit) {
        long baseTime = jobStartTime.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        long currentTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        long diff = currentTime - baseTime;
        double converted = convertBackWithTimeUnit(diff, timeUnit);
        Duration convertedDiff = Duration.ofMillis(Double.valueOf(converted).longValue());

        return simStartTime.plusSeconds(convertedDiff.toSeconds());
    }

    /**
     * adjusts given time with simulation timeUnit
     * if TimeUnit is 0.1 and the given time is 10000 ms (10 sec) in the system, therefore it returns 1000 (1 sec)
     */
    public static double convertWithTimeUnit(double millis, double timeUnit) {
        return millis * timeUnit;
    }

    /**
     * converts back given time to real time bz using simulation timeUnit
     * if TimeUnit is 0.1 and the given time is 1000 ms (1 sec) in the simulation, therefore it returns 10000 (10 sec)
     */
    public static double convertBackWithTimeUnit(long millis, double timeUnit) {
        return millis / timeUnit;
    }
}
