package edu.campuswien.smartcity.views.job;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import edu.campuswien.smartcity.data.entity.Job;
import edu.campuswien.smartcity.data.service.JobService;
import edu.campuswien.smartcity.data.service.SimulationService;
import edu.campuswien.smartcity.job.ScheduledParkingJob;
import edu.campuswien.smartcity.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@PageTitle("Job")
@Route(value = "job", layout = MainLayout.class)
@Tag("job-view")
@JsModule("./views/job/job-view.ts")
@Uses(Icon.class)
public class JobView extends LitTemplate implements HasComponents, HasStyle {
    private static final long serialVersionUID = -461959846515973914L;

    @Id("grid")
    private GridPro<Job> grid;

    private final SimulationService simulationService;
    private final JobService jobService;
    private final ScheduledParkingJob parkingJob;

    private Grid.Column<Job> simulationColumn;
    private Grid.Column<Job> intervalColumn;
    private Grid.Column<Job> statusColumn;
    private Grid.Column<Job> startTimeColumn;
    private Grid.Column<Job> endTimeColumn;
    private Grid.Column<Job> actionColumn;
    private Grid.Column<Job> resultColumn;

    @Autowired
    public JobView(SimulationService simulationService, JobService jobService, ScheduledParkingJob parkingJob) {
        this.simulationService = simulationService;
        this.jobService = jobService;
        this.parkingJob = parkingJob;

        createGrid();
    }

    private void createGrid() {
        createGridComponent();
        addColumnsToGrid();
    }

    private void createGridComponent() {
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setWidthFull();
        grid.setHeight("100%");

        ListDataProvider<Job> dataProvider = new ListDataProvider<Job>(jobService.list());
        grid.setDataProvider(dataProvider);
    }

    private void addColumnsToGrid() {
        createSimulationColumn();
        createStatusColumn();
        createTimeColumn();
        createActionColumn();
        createResultColumn();
    }

    private void createSimulationColumn() {
        simulationColumn = grid
                .addColumn(new TextRenderer<>(job -> job.getSimulation().getName()))
                .setComparator(job -> job.getSimulation().getName()).setHeader("Simulation")
                .setFrozen(true).setAutoWidth(true).setFlexGrow(0);

        intervalColumn = grid
                .addColumn(new NumberRenderer<>(job -> job.getSimulation().getTimeUnit(), NumberFormat.getNumberInstance(Locale.US)))
                .setComparator(job -> job.getSimulation().getTimeUnit()).setHeader("Time Unit").setAutoWidth(true).setFlexGrow(0);
    }

    private void createStatusColumn() {
        statusColumn = grid.addColumn(new ComponentRenderer<>(job -> {
            Span span = new Span();
            span.setText(job.getStatus().getText());
            span.addClassNames("job-status", "job-" + job.getStatus().toString().toLowerCase());
            return span;
        })).setComparator(job -> job.getStatus()).setHeader("Status").setAutoWidth(true).setFlexGrow(0);
    }

    private void createTimeColumn() {
        startTimeColumn = grid
                .addColumn(new LocalDateTimeRenderer<>(job -> job.getStartTime(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                .setComparator(job -> job.getStartTime()).setHeader("Start Time").setAutoWidth(true).setFlexGrow(0);
        endTimeColumn = grid
                .addColumn(new LocalDateTimeRenderer<>(job -> job.getEndTime(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                .setComparator(job -> job.getEndTime()).setHeader("End Time").setAutoWidth(true).setFlexGrow(0);
    }

    private void createActionColumn() {
        actionColumn = grid.addComponentColumn(job -> {
            Button startBtn = new Button("Start");
            startBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
            Button stopBtn = new Button("Stop");
            stopBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
            Button pauseBtn = new Button("Pause");
            pauseBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
            Button resumeBtn = new Button("Resume");
            resumeBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
            Button restartBtn = new Button("Restart");
            restartBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

            startBtn.addClickListener(e -> startJob(job));
            stopBtn.addClickListener(e -> stopJob(job));
            //pauseBtn.addClickListener(e -> pauseJob());
            //resumeBtn.addClickListener(e -> resumeJob());
            restartBtn.addClickListener(e -> restartJob(job));

            switch (job.getStatus()) {
                case NotYetRun:
                    return startBtn;
                case Running:
                    return new HorizontalLayout(stopBtn, pauseBtn);
                case Paused:
                    return resumeBtn;
                case Stopped:
                    return restartBtn;
            }
            return null;
        }).setHeader("Action").setAutoWidth(true).setFlexGrow(0);
    }

    private void createResultColumn() {
        resultColumn = grid.addComponentColumn(job -> {
            RouterLink link = new RouterLink();
            link.addClassNames("flex", "mx-s", "p-s", "relative");
            link.setRoute(JobView.class);
            Span text = new Span("Explore");
            text.addClassNames("font-medium", "text-s");
            link.add(new MainLayout.MenuItemInfo.LineAwesomeIcon("la la-chart-area"), text);
            return link;
        }).setHeader("Result").setAutoWidth(true).setFlexGrow(0);
    }

    private void startJob(Job job) {
        parkingJob.start(job);
        UI.getCurrent().getPage().reload();
    }

    private void restartJob(Job job) {
        parkingJob.start(job);
        UI.getCurrent().getPage().reload();
    }

    private void stopJob(Job job) {
        parkingJob.stop(job);
        UI.getCurrent().getPage().reload();
    }


}
