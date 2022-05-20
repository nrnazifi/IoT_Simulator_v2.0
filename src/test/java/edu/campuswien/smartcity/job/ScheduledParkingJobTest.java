package edu.campuswien.smartcity.job;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;

@RunWith(SpringRunner.class)
public class ScheduledParkingJobTest {

    @Test
    public void test_convertTimeByTimeUnit() {
        int currentTimeBasedData = 28;//min
        double timeUnit = 0.1;
        // change the time based on the TimeUnit of the simulation
        long millis = Duration.ofMinutes(currentTimeBasedData).toMillis();
        Assert.assertEquals(1680000, millis);
        double convertedTime = millis * timeUnit;
        Assert.assertEquals(168000.0, convertedTime, 0);
    }


}
