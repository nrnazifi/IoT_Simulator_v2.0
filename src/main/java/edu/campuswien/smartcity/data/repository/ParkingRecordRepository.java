package edu.campuswien.smartcity.data.repository;

import edu.campuswien.smartcity.data.entity.ParkingRecord;
import edu.campuswien.smartcity.data.report.DurationMinuteAverage;
import edu.campuswien.smartcity.data.report.RequestNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, Long> {

    public List<ParkingRecord> findAllByJobIdOrderByArrivalTime(Long jobId);

    @Query(value = "SELECT DATE(arrival_time) as date, AVG(duration_seconds)/60 as value, 'Days' as type " +
            "FROM parking_record where job_id = ?1 group by DATE(arrival_time)",
            nativeQuery = true)
    public List<DurationMinuteAverage> findAvgOfDurationPerDate(long jobId);

    @Query(value = "SELECT DATE(arrival_time) as date, AVG(duration_seconds)/60 as value, 'Days' as type " +
            "FROM parking_record where job_id = ?1 and (TIME(arrival_time) >= ?2 and TIME(arrival_time) < ?3) " +
            "group by DATE(arrival_time)",
            nativeQuery = true)
    public List<DurationMinuteAverage> findAvgOfDurationPerDate_Day(long jobId, LocalTime sunrise, LocalTime sunset);

    @Query(value = "SELECT DATE(arrival_time) as date, AVG(duration_seconds)/60 as value, 'Days' as type " +
            "FROM parking_record where job_id = ?1 and (TIME(arrival_time) >= ?3 or TIME(arrival_time) < ?2) " +
            "group by DATE(arrival_time)",
            nativeQuery = true)
    public List<DurationMinuteAverage> findAvgOfDurationPerDate_Night(long jobId, LocalTime sunrise, LocalTime sunset);

    @Query(value = "SELECT WEEK(arrival_time,3) as week, AVG(duration_seconds)/60 as value, 'Weeks' as type " +
            "FROM parking_record where job_id = ?1 group by WEEK(arrival_time,3)",
            nativeQuery = true)
    public List<DurationMinuteAverage> findAvgOfDurationPerWeek(long jobId);

    @Query(value = "SELECT WEEK(arrival_time,3) as week, AVG(duration_seconds)/60 as value, 'Weeks' as type " +
            "FROM parking_record where job_id = ?1 and (TIME(arrival_time) >= ?2 and TIME(arrival_time) < ?3) " +
            "group by WEEK(arrival_time,3)",
            nativeQuery = true)
    public List<DurationMinuteAverage> findAvgOfDurationPerWeek_Day(long jobId, LocalTime sunrise, LocalTime sunset);

    @Query(value = "SELECT WEEK(arrival_time,3) as week, AVG(duration_seconds)/60 as value, 'Weeks' as type " +
            "FROM parking_record where job_id = ?1 and (TIME(arrival_time) >= ?3 or TIME(arrival_time) < ?2) " +
            "group by WEEK(arrival_time,3)",
            nativeQuery = true)
    public List<DurationMinuteAverage> findAvgOfDurationPerWeek_Night(long jobId, LocalTime sunrise, LocalTime sunset);

    @Query(value = "SELECT DAYNAME(arrival_time) as dayOfWeek, AVG(duration_seconds)/60 as value, 'DayOfWeek' as type " +
            "FROM parking_record where job_id = ?1 group by DAYNAME(arrival_time) order by WEEKDAY(arrival_time) asc",
            nativeQuery = true)
    public List<DurationMinuteAverage> findAvgOfDurationPerDayOfWeek(long jobId);

    @Query(value = "SELECT DAYNAME(arrival_time) as dayOfWeek, AVG(duration_seconds)/60 as value, 'DayOfWeek' as type " +
            "FROM parking_record where job_id = ?1 and (TIME(arrival_time) >= ?2 and TIME(arrival_time) < ?3) " +
            "group by DAYNAME(arrival_time) order by WEEKDAY(arrival_time) asc",
            nativeQuery = true)
    public List<DurationMinuteAverage> findAvgOfDurationPerDayOfWeek_Day(long jobId, LocalTime sunrise, LocalTime sunset);

    @Query(value = "SELECT DAYNAME(arrival_time) as dayOfWeek, AVG(duration_seconds)/60 as value, 'DayOfWeek' as type " +
            "FROM parking_record where job_id = ?1 and (TIME(arrival_time) >= ?3 or TIME(arrival_time) < ?2) " +
            "group by DAYNAME(arrival_time) order by WEEKDAY(arrival_time) asc",
            nativeQuery = true)
    public List<DurationMinuteAverage> findAvgOfDurationPerDayOfWeek_Night(long jobId, LocalTime sunrise, LocalTime sunset);

    @Query(value = "SELECT DATE(arrival_time) as date, COUNT(*) as value, 'Days' as type " +
            "FROM parking_record where job_id = ?1 group by DATE(arrival_time)",
            nativeQuery = true)
    public List<RequestNumber> findRequestNumberPerDate(long jobId);

    @Query(value = "SELECT WEEK(arrival_time,3) as week, COUNT(*) as value, 'Weeks' as type " +
            "FROM parking_record where job_id = ?1 group by WEEK(arrival_time,3)",
            nativeQuery = true)
    public List<RequestNumber> findRequestNumberPerWeek(long jobId);

    @Query(value = "SELECT DAYNAME(arrival_time) as dayOfWeek, COUNT(*) as value, 'DayOfWeek' as type " +
            "FROM parking_record where job_id = ?1 group by DAYNAME(arrival_time) order by WEEKDAY(arrival_time) asc",
            nativeQuery = true)
    public List<RequestNumber> findRequestNumberPerDayOfWeek(long jobId);
}
