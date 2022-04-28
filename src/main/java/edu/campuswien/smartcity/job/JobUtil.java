package edu.campuswien.smartcity.job;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class JobUtil {
    public static LocalDateTime getCurrentSimulationTime(LocalDateTime startTime, double timeUnit) {
        long baseTime = startTime.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        long currentTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        long diff = currentTime - baseTime;
        Duration convertedDiff = Duration.ofMillis(Double.valueOf(diff / timeUnit).longValue());

        return startTime.plusSeconds(convertedDiff.toSeconds());
    }
}
