package edu.campuswien.smartcity.job;

import edu.campuswien.smartcity.data.entity.*;
import edu.campuswien.smartcity.data.enums.DayCategoryEnum;
import edu.campuswien.smartcity.data.enums.DayTypeEnum;
import edu.campuswien.smartcity.data.enums.JobStatusEnum;
import edu.campuswien.smartcity.data.service.*;
import edu.campuswien.smartcity.publish.ParkingPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimerTask;

public class ScheduledParkingJob extends ScheduledJob {

    Logger LOGGER = LoggerFactory.getLogger(ScheduledParkingJob.class);

    private Job job;
    private Simulation simulation;
    private ParkingLot parkingLot;

    private final JobService jobService;
    private final ParkingLotService parkingService;
    private final ParkingSpotService spotService;
    private final ParkingRecordService recordService;
    private final InternalLogger dbLogger;
    private final ParkingPublisher parkingPublisher;

    public ScheduledParkingJob(Job job, JobService jobService, ParkingLotService parkingService, ParkingSpotService spotService,
                               ParkingRecordService recordService, InternalLogger dbLogger) {
        setJob(job);
        this.jobService = jobService;
        this.parkingService = parkingService;
        this.spotService = spotService;
        this.recordService = recordService;
        this.dbLogger = dbLogger;

        parkingPublisher = new ParkingPublisher(simulation);
    }

    public void setJob(Job job) {
        this.job = job;
        this.simulation = job.getSimulation();
        this.parkingLot = simulation.getParkingLot();
    }

    @Override
    public boolean start() {
        if(simulation.isScheduleNow()) {
            job.setJobStartTime(LocalDateTime.now());
            job.setJobEndTime(null);
            if(simulation.isCustomizeSimulationTime() && simulation.getSimulationStartTime() != null) {
                job.setSimulationStartTime(simulation.getSimulationStartTime());
            } else {
                job.setSimulationStartTime(job.getJobStartTime());
            }
            job.setSimulationEndTime(simulation.getSimulationEndTime());
            job.setStatus(JobStatusEnum.Running);
            jobService.update(job);

            try {
                initSpotsStatus();
                scheduleAllSpots();
                scheduleStopTime();
                return true;
            } catch (Exception e) {
                LOGGER.error("ScheduledParkingJob.start()", e);
                return false;
            }
        } else {
            //TODO must be implemented a TimerTask for future!
        }
        return false;
    }

    @Override
    public boolean stop() {
        try {
            job.setJobEndTime(LocalDateTime.now());
            job.setSimulationEndTime(getSimulationTime());
            job.setStatus(JobStatusEnum.Stopped);
            jobService.update(job);

            resetTimer();
            return true;
        } catch (Exception e) {
            LOGGER.error("ScheduledParkingJob.stop()", e);
            return false;
        }
    }

    /**
     * Initializes the spots for the given job. It means, it generates spots for it and set some of them as occupied spots
     */
    private void initSpotsStatus() {
        generateSpots();

        // As a default, some spots are set to occupied (status=true).
        //to prevent to choose a spot two times
        List<Long> selectedSpots = new ArrayList<>();
        for (int i = 0; i < parkingLot.getNumberOfOccupiedAtStart();) {
            int rndSpot = random(0, parkingLot.getCapacity() - 1);
            ParkingSpot spot = job.getSpots().get(rndSpot);
            if (selectedSpots.contains(spot.getId())) {
                continue;
            }

            selectedSpots.add(spot.getId());
            spot.setStatus(true);
            spotService.update(spot);
            i++;
        }
    }

    /**
     * Generates spots for given job and parking lot. If the given job has spots, then removes them and generates again
     */
    private void generateSpots() {
        if (!job.getSpots().isEmpty()) {
            job.getSpots().clear();
        }

        String name = parkingLot.getName();
        List<ParkingSpot> spots = new ArrayList<>();
        for (long i = parkingLot.getStartId(); i < parkingLot.getCapacity() + parkingLot.getStartId(); i++) {
            ParkingSpot spot = new ParkingSpot();
            spot.setJob(job);
            spot.setParkingLot(parkingLot);
            spot.setDeviceId(name + "_" + i);
            spot.setStatus(false);
            spot.setLastChangedTime(job.getSimulationStartTime());
            spot.setScheduledTimeInMillis(0l);
            spots.add(spot);
            spotService.update(spot);
        }

        job.getSpots().addAll(spots);
        jobService.update(job);
        Optional<Job> reloadJob = jobService.get(job.getId());
        job = reloadJob.get();
    }

    private void scheduleStopTime() {
        if(simulation.isCustomizeSimulationTime() && simulation.getSimulationEndTime() != null) {
            //TODO run a TimerTask for stopping the job
        }
    }

    private LocalDateTime getSimulationTime() {
        return JobUtil.getCurrentSimulationTime(job.getJobStartTime(), job.getSimulationStartTime(), simulation.getTimeUnit());
    }

    private int getAverageOfOccupiedTime() {
        List<TimeBasedData> occupiedTimes = parkingService.findAllTimeBased4Occupied(parkingLot);
        return findCurrentTimeBasedData(occupiedTimes);
    }

    private int getAverageOfRequestNumber() {
        List<TimeBasedData> requestNumbers = parkingService.findAllTimeBased4Request(parkingLot);
        return findCurrentTimeBasedData(requestNumbers);
    }

    private int findCurrentTimeBasedData(List<TimeBasedData> parkingTimes) {
        LocalDateTime simDateTime = getSimulationTime();
        LocalTime simTime = simDateTime.toLocalTime();

        if (parkingTimes != null && !parkingTimes.isEmpty()) {
            // By default
            TimeBasedData currentTimeData = parkingTimes.get(0);
            DayCategoryEnum category = currentTimeData.getDayType().getCategory();

            DayTypeEnum dayType = null;
            if (category.equals(DayCategoryEnum.Generally)) {
                dayType = DayTypeEnum.General;
            } else if (category.equals(DayCategoryEnum.WorkRestTime)) {
                //TODO: add holidays later
                /*if (condition) {
                    dayType = DayTypeEnum.Holiday;
                } else*/
                if (simDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) || simDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                    dayType = DayTypeEnum.Weekend;
                } else {
                    dayType = DayTypeEnum.Workday;
                }
            } else if (category.equals(DayCategoryEnum.WeekDays)) {
                dayType = DayTypeEnum.convertDayOfWeek(simDateTime.getDayOfWeek());
            }

            for (TimeBasedData timeData : parkingTimes) {
                if (timeData.getDayType().equals(dayType)) {
                    currentTimeData = timeData;
                    break;
                }
            }

            if ((simTime.isAfter(parkingLot.getDaylight()) || simTime.equals(parkingLot.getDaylight()))
                    && simTime.isBefore(parkingLot.getDarkness())) {
                //Daylight or in day
                return currentTimeData.getValueDay();
            } else {
                //Darkness or at night
                return currentTimeData.getValueNight();
            }
        }

        throw new RuntimeException("Not found value: findCurrentTimeBasedData()");
    }

    /**
     * @param mean is in minute
     * @return a random number in millisecond
     */
    private double getRandomTime(int mean) {
        double expRndTime = randomExponential(mean);
        dbLogger.log(new InternalLog(expRndTime, null), Level.INFO);

        double millis = expRndTime * 60 * 1000; // Min to Mills
        return millis;
    }

    /**
     * schedules all spots with an exp random time by using the current mean
     * and runs a TimerTask with a delay of minimum generated random time to check all spots after that time
     */
    private void scheduleAllSpots() {
        int mean = getAverageOfOccupiedTime();
        long minTime = Integer.MAX_VALUE;
        for(ParkingSpot spot : job.getSpots()) {
            double expRndTime = getRandomTime(mean);

            double convertedTime = JobUtil.convertWithTimeUnit(expRndTime, simulation.getTimeUnit());
            long roundedTime = Math.round(convertedTime);

            spot.setScheduledTimeInMillis(Math.round(expRndTime));
            spotService.update(spot);

            if(roundedTime < minTime) {
                minTime = roundedTime;
            }
        }

        schedule(new ParkingTimerTask(), minTime);
    }

    protected class ParkingTimerTask extends TimerTask {
        @Override
        public void run() {
            if (job.getSpots() == null || job.getSpots().isEmpty()) {
                throw new RuntimeException("No spot is generated for parking lot " + parkingLot.getName());
            }

            LocalDateTime currentTime = getSimulationTime();
            int mean = getAverageOfOccupiedTime();
            long minTime = Integer.MAX_VALUE;

            for(ParkingSpot spot : job.getSpots()) {
                LocalDateTime previousTime = spot.getLastChangedTime();
                Duration duration = Duration.between(previousTime, currentTime);
                if (duration.isNegative()) {
                    LOGGER.warn("ParkingTimerTask: Diff of previousTime and currentTime must be always positive!");
                }

                if(spot.getScheduledTimeInMillis() <= duration.toMillis()) {
                    Boolean prevStatus = spot.getStatus();

                    // next random time
                    double expRndTime = getRandomTime(mean);
                    double convertedTime = JobUtil.convertWithTimeUnit(expRndTime, simulation.getTimeUnit());
                    long roundedTime = Math.round(convertedTime);

                    // update spot of this job
                    spot.setStatus(!prevStatus); //toggle status
                    spot.setLastChangedTime(currentTime);
                    spot.setScheduledTimeInMillis(Math.round(expRndTime));
                    spotService.update(spot);

                    // create a new record for this change
                    ParkingRecord record = new ParkingRecord();
                    record.setJobId(job.getId());
                    record.setSpotId(spot.getId());
                    record.setDeviceId(spot.getDeviceId());
                    record.setArrivalTime(previousTime);
                    record.setDepartureTime(currentTime);
                    record.setDurationSeconds(duration.toSeconds());
                    record.setStatus(prevStatus);
                    saveRecord(record);

                    if(roundedTime < minTime) {
                        minTime = roundedTime;
                    }
                    dbLogger.log(new InternalLog(null, duration.toMinutes()), Level.INFO);
                } else {
                    long restTimeForSpot = spot.getScheduledTimeInMillis() - duration.toMillis();
                    if(restTimeForSpot < minTime) {
                        minTime = restTimeForSpot;
                    }
                }
            }

            // at the end calls schedule() again or stop the job
            if(simulation.getSimulationEndTime() != null && simulation.getSimulationEndTime().isBefore(getSimulationTime())) {
                stop();
            } else {
                schedule(new ParkingTimerTask(), minTime);
            }
        }
    }

    private void saveRecord(ParkingRecord record) {
        if(simulation.isOnDatabase()) {
            recordService.update(record);
        }

        if(simulation.isOnServer()) {
            parkingPublisher.publish(record);
        }
    }
}
