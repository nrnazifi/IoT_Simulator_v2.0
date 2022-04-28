package edu.campuswien.smartcity.views.report;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.*;
import edu.campuswien.smartcity.data.entity.Job;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.entity.ParkingSpot;
import edu.campuswien.smartcity.data.service.JobService;
import edu.campuswien.smartcity.data.service.ParkingSpotService;
import edu.campuswien.smartcity.job.JobUtil;
import edu.campuswien.smartcity.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@PageTitle("Job Report View")
@Route(value = "report/job/:jobId", layout = MainLayout.class)
@Tag("job-report-view")
@JsModule("./views/report/job-report-view.ts")
public class JobReportView extends LitTemplate implements BeforeEnterObserver {
    private static final long serialVersionUID = 3450816484053818388L;

    @Id("txtJobName")
    private Span txtJobName;
    @Id("txtJobStatus")
    private Span txtJobStatus;
    @Id("txtJobStartTime")
    private Span txtJobStartTime;
    @Id("txtJobEndTimeLabel")
    private Span txtJobEndTimeLabel;
    @Id("txtJobEndTime")
    private Span txtJobEndTime;
    @Id("txtJobCapacity")
    private Span txtJobCapacity;
    @Id("txtJobOccupancy")
    private Span txtJobOccupancy;
    @Id("txtJobVacant")
    private Span txtJobVacant;

    private long jobId;
    private Job job;
    private final JobService jobService;
    private final ParkingSpotService spotService;

    @Autowired
    public JobReportView(JobService jobService, ParkingSpotService spotService) {
        this.jobService = jobService;
        this.spotService = spotService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String id = event.getRouteParameters().get("jobId").get();
        jobId = Long.valueOf(id);
        Optional<Job> optJob = jobService.get(jobId);
        if(optJob.isPresent()) {
            job = optJob.get();
            setJobBaseInfo();
        } else {
            throw new NotFoundException("Not found job id!");
        }
    }

    private void setJobBaseInfo() {
        if(job == null) return;

        txtJobName.setText(job.getSimulation().getName());
        txtJobStatus.setText(job.getStatus().getText());
        txtJobStartTime.setText(job.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        switch (job.getStatus()) {
            case Running:
                txtJobStatus.addClassName("text-success");
                txtJobEndTimeLabel.setText("Current time");
                LocalDateTime currentTime = JobUtil.getCurrentSimulationTime(job.getStartTime(), job.getSimulation().getTimeUnit());
                txtJobEndTime.setText(currentTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
                break;
            case Stopped:
                txtJobStatus.addClassName("text-error");
                txtJobEndTimeLabel.setText("End time");
                txtJobEndTime.setText(job.getEndTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
                break;
            case Paused:
            case NotYetRun:
                txtJobStatus.addClassName("text-primary");
                txtJobEndTimeLabel.setText("");
                txtJobEndTime.setText("");
                break;
        }

        ParkingLot parkingLot = job.getSimulation().getParkingLot();
        txtJobCapacity.setText(parkingLot.getCapacity().toString());
        List<ParkingSpot> spots = spotService.list(job);
        if(spots == null || spots.isEmpty()) {
            txtJobOccupancy.setText(parkingLot.getNumberOfOccupiedAtStart().toString());
            txtJobVacant.setText(String.valueOf(parkingLot.getCapacity() - parkingLot.getNumberOfOccupiedAtStart()));
        } else {
            int occupied = 0;
            int vacant = 0;
            for (ParkingSpot spot : spots) {
                if(spot.getStatus()) {
                    occupied++;
                } else {
                    vacant++;
                }
            }
            txtJobOccupancy.setText(String.valueOf(occupied));
            txtJobVacant.setText(String.valueOf(vacant));
        }
    }
}
