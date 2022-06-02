package edu.campuswien.smartcity.views.report;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import edu.campuswien.smartcity.config.Constants;
import edu.campuswien.smartcity.data.entity.Job;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.entity.ParkingRecord;
import edu.campuswien.smartcity.data.entity.ParkingSpot;
import edu.campuswien.smartcity.data.enums.ReportAggregationType;
import edu.campuswien.smartcity.data.report.DurationExponential;
import edu.campuswien.smartcity.data.report.DurationMinuteAverage;
import edu.campuswien.smartcity.data.report.RequestNumber;
import edu.campuswien.smartcity.data.service.JobService;
import edu.campuswien.smartcity.data.service.ParkingRecordService;
import edu.campuswien.smartcity.data.service.ParkingSpotService;
import edu.campuswien.smartcity.job.JobUtil;
import edu.campuswien.smartcity.views.MainLayout;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
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

    Logger LOGGER = LoggerFactory.getLogger(JobReportView.class);

    @Id("txtJobName")
    private Span txtJobName;
    @Id("txtJobStatus")
    private Span txtJobStatus;
    @Id("txtJobTime")
    private Span txtJobTime;
    @Id("txtJobTimeLabel")
    private Span txtJobTimeLabel;
    @Id("txtSimStartTime")
    private Span txtSimStartTime;
    @Id("txtSimEndTime")
    private Span txtSimEndTime;
    @Id("txtSimEndTimeLabel")
    private Span txtSimEndTimeLabel;
    @Id("txtJobCapacity")
    private Span txtJobCapacity;
    @Id("txtJobOccupancy")
    private Span txtJobOccupancy;
    @Id("txtJobVacant")
    private Span txtJobVacant;
    @Id("exportCsv")
    private Button exportCsv;
    @Id("downloadLink")
    private Div downloadLink;

    @Id("ddlDurationChartTimes")
    private Select<ReportAggregationType> ddlDurationChartTimes;
    @Id("ddlRequestChartTimes")
    private Select<ReportAggregationType> ddlRequestChartTimes;
    @Id("viewDurationChart")
    private Chart viewDurationChart;
    @Id("viewRequestChart")
    private Chart viewRequestChart;
    @Id("viewExponentialChart")
    private Chart viewExponentialChart;

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
        drawExponentialChartInfo();

        exportCsv.addClickListener(e -> onExportCsv());
        downloadLink.removeAll();
    }

    private void setJobBaseInfo() {
        if(job == null) return;

        txtJobName.setText(job.getSimulation().getName());
        txtJobStatus.setText(job.getStatus().getText());

        switch (job.getStatus()) {
            case Running:
                txtJobStatus.addClassName("text-success");
                txtJobTimeLabel.setText("Job StartTime");
                txtJobTime.setText(job.getJobStartTime().format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT)));
                txtSimStartTime.setText(job.getSimulationStartTime().format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT)));
                txtSimEndTimeLabel.setText("Simu. CurrentTime");
                LocalDateTime currentTime = JobUtil.getCurrentSimulationTime(job.getJobStartTime(),
                        job.getSimulationStartTime(), job.getSimulation().getTimeUnit());
                txtSimEndTime.setText(currentTime.format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT)));
                break;
            case Stopped:
                txtJobStatus.addClassName("text-error");
                txtJobTimeLabel.setText("Job EndTime");
                txtJobTime.setText(job.getJobEndTime().format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT)));
                txtSimStartTime.setText(job.getSimulationStartTime().format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT)));
                txtSimEndTimeLabel.setText("Simu. EndTime");
                txtSimEndTime.setText(job.getSimulationEndTime().format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT)));
                break;
            case Paused:
            case NotYetRun:
                txtJobStatus.addClassName("text-primary");
                txtJobTimeLabel.setText("Job StartTime");
                txtJobTime.setText("--");
                txtSimStartTime.setText("--");
                txtSimEndTimeLabel.setText("Simu. CurrentTime");
                txtSimEndTime.setText("--");
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
        ddlDurationChartTimes.setValue(ReportAggregationType.Days);
        ddlDurationChartTimes.addValueChangeListener(e -> drawDurationAverageChart(e.getValue()));
        drawDurationAverageChart(ddlDurationChartTimes.getValue());

        ddlRequestChartTimes.setItems(ReportAggregationType.values());
        ddlRequestChartTimes.setValue(ReportAggregationType.Days);
        ddlRequestChartTimes.addValueChangeListener(e -> drawRequestNumberChart(e.getValue()));
        drawRequestNumberChart(ddlRequestChartTimes.getValue());
    }

    private void drawDurationAverageChart(ReportAggregationType chartTimeType) {
        List<DurationMinuteAverage> durationResult = recordService.findAverageOfDurations(job, chartTimeType);
        List<DurationMinuteAverage> daysDurationResult = recordService.findAverageOfDurations_Days(job, chartTimeType);
        List<DurationMinuteAverage> nightsDurationResult = recordService.findAverageOfDurations_Nights(job, chartTimeType);

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
        ListSeries dayValues = new ListSeries("In day", daysDurationResult.stream().map(DurationMinuteAverage::getValue).toArray(Number[]::new));
        ListSeries nightValues = new ListSeries("At night", nightsDurationResult.stream().map(DurationMinuteAverage::getValue).toArray(Number[]::new));
        drawChart(viewDurationChart, chartTimeType, labels, values, dayValues, nightValues);
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

        drawChart(viewRequestChart, chartTimeType, labels, values, null, null);
    }

    private void drawChart(Chart chart, ReportAggregationType chartTimeType, String[] labels, ListSeries values, ListSeries dayValues, ListSeries nightValues) {
        Configuration configuration = chart.getConfiguration();
        chart.getConfiguration().getChart().setType(ChartType.LINE);
        if(dayValues != null && nightValues != null) {
            configuration.setSeries(values, nightValues, dayValues);
        } else {
            configuration.setSeries(values);
        }

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

    private void onExportCsv() {
        List<ParkingRecord> records = recordService.list(job.getId());
        try {
            File directory = new File("tempFiles");
            if(!directory.exists()) {
                directory.mkdir();
            }

            String filename = job.getSimulation().getName() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATETIME_FILE_FORMAT)) + ".csv";
            File csvFile = new File(directory.getPath() + "/" + filename);
            FileWriter out = new FileWriter(csvFile);
            CSVFormat format = CSVFormat.DEFAULT.builder()
                    .setHeader("deviceid", "arrivaltime", "departuretime", "durationseconds", "vehiclepresent")
                    .setEscape('\\')
                    .setQuoteMode(QuoteMode.ALL)
                    .build();

            try (CSVPrinter printer = new CSVPrinter(out, format)) {
                records.forEach(item -> {
                    try {
                        printer.printRecord(
                                item.getDeviceId(),
                                item.getArrivalTime(),
                                item.getDepartureTime(),
                                item.getDurationSeconds(),
                                item.getStatus()
                        );
                    } catch (IOException e) {
                        LOGGER.error("CSVPrinter", e);
                    }
                });
            } finally {
                // https://stackoverflow.com/questions/60822729/dynamically-created-content-for-download-without-writing-a-file-on-server-side-i
                Anchor anchor = new Anchor(getStreamResource(filename, csvFile), "Download CSV");
                anchor.getElement().setAttribute("download", true);
                anchor.setId(String.valueOf(System.currentTimeMillis()));
                //Button downloadButton = new Button(new Icon(VaadinIcon.DOWNLOAD_ALT));
                //anchor.add( downloadButton );

                downloadLink.removeAll();
                downloadLink.add(anchor);

                anchor.getElement().addEventListener("click", e -> {
                    anchor.setVisible(false);
                    //csvFile.delete(); TODO remove files of temp directory
                });
            }
        } catch (Exception e) {
            LOGGER.error("onExportCsv()", e);
        }
    }

    public StreamResource getStreamResource(String filename, File content) {
        return new StreamResource(filename, () -> {
            try {
                return new BufferedInputStream(new FileInputStream(content));
            } catch (IOException e) {
                LOGGER.error("getStreamResource()", e);
            }
            return null;
        });
    }

    private void drawExponentialChartInfo() {
        List<DurationExponential> durationResult = recordService.getExponentialDurationTimes(job);
        String[] labels = durationResult.stream().map(e -> e.getDuration().toString()).toArray(String[]::new);
        ListSeries values = new ListSeries("Duration (minutes)", durationResult.stream().map(DurationExponential::getSize).toArray(Number[]::new));

        Configuration configuration = viewExponentialChart.getConfiguration();
        viewExponentialChart.getConfiguration().getChart().setType(ChartType.COLUMN);
        configuration.setSeries(values);

        XAxis xAxis = new XAxis();
        xAxis.setTitle("Minute");
        xAxis.setCategories(labels);
        configuration.removexAxes();
        configuration.addxAxis(xAxis);

        YAxis yAxis = new YAxis();
        yAxis.setTitle("Number");
        configuration.removeyAxes();
        configuration.addyAxis(yAxis);

        Tooltip tooltip = new Tooltip();
        tooltip.setShared(true);
        configuration.setTooltip(tooltip);

        viewExponentialChart.drawChart();
    }
}
