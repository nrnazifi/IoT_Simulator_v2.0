package edu.campuswien.smartcity.views.report;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.*;
import edu.campuswien.smartcity.config.Constants;
import edu.campuswien.smartcity.data.entity.Job;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.entity.ParkingSpot;
import edu.campuswien.smartcity.data.enums.ReportAggregationType;
import edu.campuswien.smartcity.data.report.DurationMinuteAverage;
import edu.campuswien.smartcity.data.report.RequestNumber;
import edu.campuswien.smartcity.data.service.JobService;
import edu.campuswien.smartcity.data.service.ParkingRecordService;
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

    @Id("ddlDurationChartTimes")
    private Select<ReportAggregationType> ddlDurationChartTimes;
    @Id("ddlRequestChartTimes")
    private Select<ReportAggregationType> ddlRequestChartTimes;
    @Id("viewDurationChart")
    private Chart viewDurationChart;
    @Id("viewRequestChart")
    private Chart viewRequestChart;

    private Job job;
    private final JobService jobService;
    private final ParkingSpotService spotService;
    private final ParkingRecordService recordService;

    @Autowired
    public JobReportView(JobService jobService, ParkingSpotService spotService, ParkingRecordService recordService) {
        this.jobService = jobService;
        this.spotService = spotService;
        this.recordService = recordService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<String> optJobId = event.getRouteParameters().get("jobId");
        if(optJobId.isPresent()) {
            long jobId = Long.parseLong(optJobId.get());
            Optional<Job> optJob = jobService.get(jobId);
            if(optJob.isPresent()) {
                job = optJob.get();
                makeView();
                return;
            }
        }

        throw new NotFoundException("Job not found!");
    }

    private void makeView() {
        setJobBaseInfo();
        setChartInfo();
    }

    private void setJobBaseInfo() {
        if(job == null) return;

        txtJobName.setText(job.getSimulation().getName());
        txtJobStatus.setText(job.getStatus().getText());
        txtJobStartTime.setText(job.getStartTime().format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT)));

        switch (job.getStatus()) {
            case Running:
                txtJobStatus.addClassName("text-success");
                txtJobEndTimeLabel.setText("Current time");
                LocalDateTime currentTime = JobUtil.getCurrentSimulationTime(job.getStartTime(), job.getSimulation().getTimeUnit());
                txtJobEndTime.setText(currentTime.format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT)));
                break;
            case Stopped:
                txtJobStatus.addClassName("text-error");
                txtJobEndTimeLabel.setText("End time");
                txtJobEndTime.setText(job.getEndTime().format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT)));
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

    private void setChartInfo() {
        ddlDurationChartTimes.setItems(ReportAggregationType.values());
        ddlDurationChartTimes.setValue(ReportAggregationType.DayOfWeek);
        ddlDurationChartTimes.addValueChangeListener(e -> drawDurationAverageChart(e.getValue()));
        drawDurationAverageChart(ddlDurationChartTimes.getValue());

        ddlRequestChartTimes.setItems(ReportAggregationType.values());
        ddlRequestChartTimes.setValue(ReportAggregationType.DayOfWeek);
        ddlRequestChartTimes.addValueChangeListener(e -> drawRequestNumberChart(e.getValue()));
        drawRequestNumberChart(ddlRequestChartTimes.getValue());
    }

    private void drawDurationAverageChart(ReportAggregationType chartTimeType) {
        List<DurationMinuteAverage> durationResult = recordService.findAverageOfDurations(job, chartTimeType);

        String[] labels = {};
        switch (chartTimeType) {
            case Days:
                labels = durationResult.stream().map(e -> e.getDate().format(DateTimeFormatter.ofPattern(Constants.DAY_DATE_FORMAT))).toArray(String[]::new);
                break;
            case DayOfWeek:
                labels = durationResult.stream().map(DurationMinuteAverage::getDayOfWeek).toArray(String[]::new);
                break;
            case Weeks:
                labels = durationResult.stream().map(e -> String.valueOf(e.getWeek())).toArray(String[]::new);
                break;
        }

        ListSeries values = new ListSeries("Duration average (min)", durationResult.stream().map(DurationMinuteAverage::getValue).toArray(Number[]::new));
        drawChart(viewDurationChart, chartTimeType, labels, values);
    }

    private void drawRequestNumberChart(ReportAggregationType chartTimeType) {
        List<RequestNumber> requestResult = recordService.findRequestNumbers(job, chartTimeType);

        String[] labels = {};
        switch (chartTimeType) {
            case Days:
                labels = requestResult.stream().map(e -> e.getDate().format(DateTimeFormatter.ofPattern(Constants.DAY_DATE_FORMAT))).toArray(String[]::new);
                break;
            case DayOfWeek:
                labels = requestResult.stream().map(RequestNumber::getDayOfWeek).toArray(String[]::new);
                break;
            case Weeks:
                labels = requestResult.stream().map(e -> String.valueOf(e.getWeek())).toArray(String[]::new);
                break;
        }
        ListSeries values = new ListSeries("Request number", requestResult.stream().map(RequestNumber::getValue).toArray(Number[]::new));

        drawChart(viewRequestChart, chartTimeType, labels, values);
    }

    private void drawChart(Chart chart, ReportAggregationType chartTimeType, String[] labels, ListSeries values) {
        Configuration configuration = chart.getConfiguration();
        chart.getConfiguration().getChart().setType(ChartType.LINE);
        configuration.setSeries(values);

        XAxis xAxis = new XAxis();
        xAxis.setTitle(chartTimeType.toString());
        //x.setCrosshair(new Crosshair());
        xAxis.setCategories(labels);
        configuration.removexAxes();
        configuration.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Values");
        configuration.removeyAxes();
        configuration.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setShared(true);
        configuration.setTooltip(tooltip);

        chart.drawChart();
    }

}
