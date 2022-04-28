package edu.campuswien.smartcity.job;

import edu.campuswien.smartcity.data.entity.*;
import edu.campuswien.smartcity.data.enums.DayCategoryEnum;
import edu.campuswien.smartcity.data.enums.DayTypeEnum;
import edu.campuswien.smartcity.data.enums.JobStatusEnum;
import edu.campuswien.smartcity.data.service.JobService;
import edu.campuswien.smartcity.data.service.ParkingLotService;
import edu.campuswien.smartcity.data.service.ParkingRecordService;
import edu.campuswien.smartcity.data.service.ParkingSpotService;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimerTask;

public class ScheduledParkingJob extends ScheduledJob {

    private Job job;
    private Simulation simulation;
    private ParkingLot parkingLot;

    private final JobService jobService;
    private final ParkingLotService parkingService;
    private final ParkingSpotService spotService;
    private final ParkingRecordService recordService;

    public ScheduledParkingJob(Job job, JobService jobService, ParkingLotService parkingService, ParkingSpotService spotService,
                               ParkingRecordService recordService) {
        setJob(job);
        this.jobService = jobService;
        this.parkingService = parkingService;
        this.spotService = spotService;
        this.recordService = recordService;
    }

    public void setJob(Job job) {
        this.job = job;
        this.simulation = job.getSimulation();
        this.parkingLot = simulation.getParkingLot();
    }

    @Override
    public boolean start() {
        job.setStartTime(LocalDateTime.now());
        job.setStatus(JobStatusEnum.Running);
        jobService.update(job);

        try {
            initSpotsStatus();

            schedule(new ParkingTimerTask(), getAverageOfOccupiedTime());
            return true;
        } catch (Exception e) {
            //TODO exception
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean stop() {
        try {
            job.setEndTime(getSimulationTime());
            job.setStatus(JobStatusEnum.Stopped);
            jobService.update(job);

            resetTimer();
            return true;
        } catch (Exception e) {
            //TODO exception
            e.printStackTrace();
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
        LocalDateTime dateTime = LocalDateTime.now();
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
            spot.setLastChangedTime(dateTime);
            spots.add(spot);
            spotService.update(spot);
        }

        job.getSpots().addAll(spots);
        jobService.update(job);
        Optional<Job> reloadJob = jobService.get(job.getId());
        job = reloadJob.get();
    }

    private LocalDateTime getSimulationTime() {
        return JobUtil.getCurrentSimulationTime(job.getStartTime(), simulation.getTimeUnit());
    }

    private long getAverageOfOccupiedTime() {
        List<TimeBasedData> occupiedTimes = parkingService.findAllTimeBased4Occupied(parkingLot);
        int currentTimeBasedData = findCurrentTimeBasedData(occupiedTimes);
        // change the time based on the TimeUnit of the simulation
        long millis = Duration.ofMinutes(currentTimeBasedData).toMillis();
        long convertedTime = Double.valueOf(millis * simulation.getTimeUnit()).longValue();
        return convertedTime;
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
                //Daylight
                return currentTimeData.getValueDay();
            } else {
                //Darkness
                return currentTimeData.getValueNight();
            }
        }

        throw new RuntimeException("Not found value: getAverageOfOccupiedTime()");
    }

    protected class ParkingTimerTask extends TimerTask {
        @Override
        public void run() {
            if (job.getSpots() == null || job.getSpots().isEmpty()) {
                throw new RuntimeException("No spot is generated for parking lot " + parkingLot.getName());
            }

            // updates/changes number of spots
            int numberOfVehicles = getAverageOfRequestNumber();
            //to prevent to choose a spot two times
            List<Long> selectedSpots = new ArrayList<>();
            for (int i = 0; i < numberOfVehicles;) {
                int rndSpot = random(0, parkingLot.getCapacity() - 1);
                ParkingSpot spot = job.getSpots().get(rndSpot);
                if (selectedSpots.contains(spot.getId())) {
                    continue;
                }

                selectedSpots.add(spot.getId());
                LocalDateTime currentTime = getSimulationTime();
                LocalDateTime previousTime = spot.getLastChangedTime();
                Duration duration = Duration.between(previousTime, currentTime);
                Boolean prevStatus = spot.getStatus();

                // update spot of this job
                spot.setStatus(!prevStatus); //toggle status
                spot.setLastChangedTime(currentTime);
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
                recordService.update(record);

                i++;
            }

            // at the end calls schedule() again
            schedule(new ParkingTimerTask(), getAverageOfOccupiedTime());
        }
    }
}
