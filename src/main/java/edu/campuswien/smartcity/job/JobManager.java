package edu.campuswien.smartcity.job;

import edu.campuswien.smartcity.data.entity.Job;
import edu.campuswien.smartcity.data.enums.JobStatusEnum;
import edu.campuswien.smartcity.data.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JobManager {
    private static final long serialVersionUID = 5754856770697593197L;

    private static Map<Job, ScheduledJob> jobMap = new HashMap<>();

    private final SimulationService simulationService;
    private final JobService jobService;
    private final ParkingLotService parkingService;
    private final ParkingSpotService spotService;
    private final ParkingRecordService recordService;
    private final InternalLogger dbLogger;

    @Autowired
    public JobManager(SimulationService simulationService, JobService jobService, ParkingLotService parkingService,
                      ParkingSpotService spotService, ParkingRecordService recordService, InternalLogger dbLogger) {
        this.simulationService = simulationService;
        this.jobService = jobService;
        this.parkingService = parkingService;
        this.spotService = spotService;
        this.recordService = recordService;
        this.dbLogger = dbLogger;
    }

    public ScheduledJob getScheduledJob(Job job) {
        ScheduledJob scheduledJob;
        if(jobMap.containsKey(job)) {
            scheduledJob = jobMap.get(job);
        } else {
            //TODO which typ of ScheduledJob must instanced
            scheduledJob = new ScheduledParkingJob(job, jobService, parkingService, spotService, recordService, dbLogger);
            jobMap.put(job, scheduledJob);
        }

        return scheduledJob;
    }

    public boolean startJob(Job job) {
        ScheduledJob scheduledJob = getScheduledJob(job);

        if(!job.getStatus().equals(JobStatusEnum.Running)) {
            return scheduledJob.start();
        }
        return false;
    }

    public boolean stopJob(Job job) {
        ScheduledJob scheduledJob = getScheduledJob(job);

        if(job.getStatus().equals(JobStatusEnum.Running)) {
            return scheduledJob.stop();
        }
        return false;
    }
}
