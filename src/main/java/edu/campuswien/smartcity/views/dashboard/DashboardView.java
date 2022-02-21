package edu.campuswien.smartcity.views.dashboard;


import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.PlotOptionsArea;
import com.vaadin.flow.component.charts.model.PointPlacement;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.campuswien.smartcity.views.MainLayout;
import edu.campuswien.smartcity.views.dashboard.ServiceHealth.Status;

@PageTitle("Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
@Tag("dashboard-view")
@JsModule("./views/dashboard/dashboard-view.ts")
public class DashboardView extends LitTemplate {

    @Id
    private Span currentUsers;

    @Id
    private Span currentUsersChange;

    @Id
    private Span viewEvents;

    @Id
    private Span viewEventsChange;

    @Id
    private Span conversionRate;

    @Id
    private Span conversionRateChange;

    @Id
    private Span customMetric;

    @Id
    private Span customMetricChange;

    @Id
    private Select year;

    @Id
    private Chart viewEventsChart;

    @Id
    private Grid<ServiceHealth> serviceHealthGrid;

    @Id
    private Chart responseTimesChart;

    public DashboardView() {
        addClassNames("flex", "flex-col");
        populateHighlights();
        populateViewEvents();
        populateServiceHealth();
        populateResponseTimes();
    }

    private void populateHighlights() {
        currentUsers.setText("745");
        currentUsersChange.add(createIcon(VaadinIcon.ARROW_UP), new Span("+33.7%"));
        currentUsersChange.getElement().getThemeList().add("success");

        viewEvents.setText("54.6k");
        viewEventsChange.add(createIcon(VaadinIcon.ARROW_DOWN), new Span("-112.45%"));
        viewEventsChange.getElement().getThemeList().add("error");

        conversionRate.setText("18%");
        conversionRateChange.add(createIcon(VaadinIcon.ARROW_UP), new Span("+3.9%"));
        conversionRateChange.getElement().getThemeList().add("success");

        customMetric.setText("-123.45");
        customMetricChange.add(createIcon(VaadinIcon.ARROW_UP), new Span("±0.0%"));
    }

    private Icon createIcon(VaadinIcon icon) {
        Icon i = icon.create();
        i.addClassNames("box-border", "p-xs");
        return i;
    }

    private void populateViewEvents() {
        year.setItems("2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021");
        year.setValue("2021");

        Configuration conf = viewEventsChart.getConfiguration();
        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);
        conf.getyAxis().setTitle("Values");
        PlotOptionsArea plotOptions = new PlotOptionsArea();
        plotOptions.setPointPlacement(PointPlacement.ON);
        conf.addPlotOptions(plotOptions);
        conf.addSeries(new ListSeries("Berlin", 189, 191, 191, 196, 201, 203, 209, 212, 229, 242, 244, 247));
        conf.addSeries(new ListSeries("London", 138, 146, 148, 148, 152, 153, 163, 173, 178, 179, 185, 187));
        conf.addSeries(new ListSeries("New York", 65, 65, 66, 71, 93, 102, 108, 117, 127, 129, 135, 136));
        conf.addSeries(new ListSeries("Tokyo", 0, 11, 17, 23, 30, 42, 48, 49, 52, 54, 58, 62));
    }

    private void populateServiceHealth() {
        TemplateRenderer<ServiceHealth> template = TemplateRenderer.<ServiceHealth>of(
                "<span aria-label$=\"Status: [[item.status]]\" theme$=\"[[item.theme]]\" title$=\"Status: [[item.status]]\"></span>")
                .withProperty("status", this::getStatusDisplayName).withProperty("theme", this::getStatusTheme);

        serviceHealthGrid.addColumn(template).setHeader("").setFlexGrow(0).setAutoWidth(true);
        serviceHealthGrid.addColumn(ServiceHealth::getCity).setHeader("City").setFlexGrow(1);
        serviceHealthGrid.addColumn(ServiceHealth::getInput).setHeader("Input").setAutoWidth(true)
                .setTextAlign(ColumnTextAlign.END);
        serviceHealthGrid.addColumn(ServiceHealth::getOutput).setHeader("Output").setAutoWidth(true)
                .setTextAlign(ColumnTextAlign.END);

        serviceHealthGrid.setItems(new ServiceHealth(Status.EXCELLENT, "Münster", 324, 1540),
                new ServiceHealth(Status.OK, "Cluj-Napoca", 311, 1320),
                new ServiceHealth(Status.FAILING, "Ciudad Victoria", 300, 1219));
    }

    private void populateResponseTimes() {
        Configuration conf = responseTimesChart.getConfiguration();
        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("System 1", 12.5));
        series.add(new DataSeriesItem("System 2", 12.5));
        series.add(new DataSeriesItem("System 3", 12.5));
        series.add(new DataSeriesItem("System 4", 12.5));
        series.add(new DataSeriesItem("System 5", 12.5));
        series.add(new DataSeriesItem("System 6", 12.5));
        conf.addSeries(series);
    }

    private String getStatusDisplayName(ServiceHealth serviceHealth) {
        Status status = serviceHealth.getStatus();
        if (status == Status.OK) {
            return "Ok";
        } else if (status == Status.FAILING) {
            return "Failing";
        } else if (status == Status.EXCELLENT) {
            return "Excellent";
        } else {
            return status.toString();
        }
    }

    private String getStatusTheme(ServiceHealth serviceHealth) {
        Status status = serviceHealth.getStatus();
        String theme = "badge primary small";
        if (status == Status.EXCELLENT) {
            theme += " success";
        } else if (status == Status.FAILING) {
            theme += " error";
        }
        return theme;
    }

}
