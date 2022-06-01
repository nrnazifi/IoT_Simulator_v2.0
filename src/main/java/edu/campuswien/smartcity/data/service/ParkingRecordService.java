package edu.campuswien.smartcity.data.service;

import edu.campuswien.smartcity.data.entity.Job;
import edu.campuswien.smartcity.data.entity.ParkingRecord;
import edu.campuswien.smartcity.data.enums.ReportAggregationType;
import edu.campuswien.smartcity.data.report.DurationMinuteAverage;
import edu.campuswien.smartcity.data.report.RequestNumber;
import edu.campuswien.smartcity.data.repository.ParkingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingRecordService extends CrudService<ParkingRecord, Long> {

    private ParkingRecordRepository repository;
    public ParkingRecordService(@Autowired ParkingRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<ParkingRecord, Long> getRepository() {
        return this.repository;
    }

    @Override
    public ParkingRecord update(ParkingRecord entity) {
        return this.getRepository().saveAndFlush(entity);
    }

    public List<ParkingRecord> list(Long jobId) {
        return repository.findAllByJobIdOrderByArrivalTime(jobId);
    }

    public List<DurationMinuteAverage> findAverageOfDurations(Job job, ReportAggregationType aggregationType) {
        if(job == null || job.getId() == null) {
            return new ArrayList<>();
        }

        switch (aggregationType) {
            case Days:
                return repository.findAvgOfDurationPerDate(job.getId());
            case Weeks:
                return repository.findAvgOfDurationPerWeek(job.getId());
            case DayOfWeek:
                return repository.findAvgOfDurationPerDayOfWeek(job.getId());
        }
        return new ArrayList<>();
    }

    public List<DurationMinuteAverage> findAverageOfDurations_Days(Job job, ReportAggregationType aggregationType) {
        if(job == null || job.getId() == null) {
            return new ArrayList<>();
        }

        LocalTime sunrise = job.getSimulation().getParkingLot().getDaylight();
        LocalTime sunset = job.getSimulation().getParkingLot().getDarkness();

        switch (aggregationType) {
            case Days:
                return repository.findAvgOfDurationPerDate_Day(job.getId(), sunrise, sunset);
            case Weeks:
                return repository.findAvgOfDurationPerWeek_Day(job.getId(), sunrise, sunset);
            case DayOfWeek:
                return repository.findAvgOfDurationPerDayOfWeek_Day(job.getId(), sunrise, sunset);
        }
        return new ArrayList<>();
    }

    public List<DurationMinuteAverage> findAverageOfDurations_Nights(Job job, ReportAggregationType aggregationType) {
        if(job == null || job.getId() == null) {
            return new ArrayList<>();
        }

        LocalTime sunrise = job.getSimulation().getParkingLot().getDaylight();
        LocalTime sunset = job.getSimulation().getParkingLot().getDarkness();

        switch (aggregationType) {
            case Days:
                return repository.findAvgOfDurationPerDate_Night(job.getId(), sunrise, sunset);
            case Weeks:
                return repository.findAvgOfDurationPerWeek_Night(job.getId(), sunrise, sunset);
            case DayOfWeek:
                return repository.findAvgOfDurationPerDayOfWeek_Night(job.getId(), sunrise, sunset);
        }
        return new ArrayList<>();
    }

    public List<RequestNumber> findRequestNumbers(Job job, ReportAggregationType aggregationType) {
        if(job == null || job.getId() == null) {
            return new ArrayList<>();
        }

        switch (aggregationType) {
            case Days:
                return repository.findRequestNumberPerDate(job.getId());
            case Weeks:
                return repository.findRequestNumberPerWeek(job.getId());
            case DayOfWeek:
                return repository.findRequestNumberPerDayOfWeek(job.getId());
        }
        return new ArrayList<>();
    }

}
