package edu.campuswien.smartcity.views.parking;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.service.ParkingLotService;
import edu.campuswien.smartcity.views.MainLayout;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Parking lot list")
@Route(value = "parking-list", layout = MainLayout.class)
@Tag("parking-list-view")
@JsModule("./views/parking/parking-list-view.ts")
@Uses(Icon.class)
public class ParkingLotListView extends LitTemplate {
    @Id("grid")
    private GridPro<ParkingLot> grid;
    private ListDataProvider<ParkingLot> dataProvider;
    private ParkingLotService parkingLotService;

    private Grid.Column<ParkingLot> nameColumn;
    private Grid.Column<ParkingLot> capacityColumn;
    private Grid.Column<ParkingLot> occupiedNumberColumn;

    public ParkingLotListView(@Autowired ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
        createGrid();
    }

    private void createGrid() {
        createGridComponent();
        addColumnsToGrid();
        addFiltersToGrid();
    }

    private void createGridComponent() {
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setHeight("100%");

        dataProvider = new ListDataProvider<ParkingLot>(parkingLotService.list());
        grid.setDataProvider(dataProvider);
    }

    private void addColumnsToGrid() {
        createNameColumn();
        createCapacityColumn();
        createOccupiedNumberColumn();
    }

    private void createNameColumn() {
        nameColumn = grid.addColumn(ParkingLot::getName, "name").setHeader("NAME").setWidth("120px").setFlexGrow(0);
    }

    private void createCapacityColumn() {
        capacityColumn = grid.addColumn(ParkingLot::getCapacity, "capacity").setHeader("CAPACITY").setWidth("120px").setFlexGrow(0);
    }

    private void createOccupiedNumberColumn() {
        occupiedNumberColumn = grid.addColumn(ParkingLot::getNumberOfOccupiedAtStart, "occupiedNumber").setHeader("NumberOfOccupiedAtStart").setWidth("120px").setFlexGrow(0);
    }

    private void addFiltersToGrid() {
        HeaderRow filterRow = grid.appendHeaderRow();

        TextField nameFilter = new TextField();
        nameFilter.setPlaceholder("Filter");
        nameFilter.setClearButtonVisible(true);
        nameFilter.setWidth("100%");
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.addValueChangeListener(event -> dataProvider.addFilter(
                lot -> StringUtils.containsIgnoreCase(lot.getName(), nameFilter.getValue())));
        filterRow.getCell(nameColumn).setComponent(nameFilter);

        TextField capacityFilter = new TextField();
        capacityFilter.setPlaceholder("Filter");
        capacityFilter.setClearButtonVisible(true);
        capacityFilter.setWidth("100%");
        capacityFilter.setValueChangeMode(ValueChangeMode.EAGER);
        capacityFilter.addValueChangeListener(event -> dataProvider.addFilter(
                lot -> StringUtils.containsIgnoreCase(Integer.toString(lot.getCapacity()), capacityFilter.getValue())));
        filterRow.getCell(capacityColumn).setComponent(capacityFilter);

        TextField occupiedNumberFilter = new TextField();
        occupiedNumberFilter.setPlaceholder("Filter");
        occupiedNumberFilter.setClearButtonVisible(true);
        occupiedNumberFilter.setWidth("100%");
        occupiedNumberFilter.setValueChangeMode(ValueChangeMode.EAGER);
        occupiedNumberFilter.addValueChangeListener(event -> dataProvider.addFilter(
                lot -> StringUtils.containsIgnoreCase(Integer.toString(lot.getNumberOfOccupiedAtStart()), occupiedNumberFilter.getValue())));
        filterRow.getCell(occupiedNumberColumn).setComponent(occupiedNumberFilter);
    }
}
