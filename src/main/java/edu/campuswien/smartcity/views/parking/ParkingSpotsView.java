package edu.campuswien.smartcity.views.parking;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.*;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.entity.ParkingSpot;
import edu.campuswien.smartcity.data.service.ParkingLotService;
import edu.campuswien.smartcity.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@PageTitle("Parking Spots")
@Route(value = "parking-spots/parking/:parkingId", layout = MainLayout.class)
@Tag("parking-spots-view")
@JsModule("./views/parking/parking-spots-view.ts")
@Uses(Icon.class)
public class ParkingSpotsView extends LitTemplate implements BeforeEnterObserver {
    private static final long serialVersionUID = -2467250523114143958L;

    @Id("grid")
    private GridPro<ParkingSpot> grid;
    private ListDataProvider<ParkingSpot> dataProvider;
    private ParkingLot parkingLot;
    private long parkingLotId;
    private ParkingLotService parkingLotService;

    private Grid.Column<ParkingSpot> deviceIdCol;
    //private Grid.Column<ParkingSpot> parkingLotIdCol;

    @Autowired
    public ParkingSpotsView(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
        this.parkingLot = new ParkingLot();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String id = event.getRouteParameters().get("parkingId").get();
        parkingLotId = Long.valueOf(id);
        Optional<ParkingLot> optParking = parkingLotService.get(parkingLotId);
        if(optParking.isPresent()) {
            parkingLot = optParking.get();
            createGrid();
        } else {

        }
    }

    private void createGrid() {
        createGridComponent();
        addColumnsToGrid();
        //addFiltersToGrid();
    }

    private void createGridComponent() {
        //grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setHeight("100%");

        dataProvider = new ListDataProvider<ParkingSpot>(parkingLot.getSpots());
        grid.setDataProvider(dataProvider);
    }

    private void addColumnsToGrid() {
        createDeviceColumn();
        createParkingLotColumn();
    }

    private void createDeviceColumn() {
        deviceIdCol = grid.addColumn(ParkingSpot::getDeviceId, "deviceId").setHeader("DEVICE ID").setWidth("120px").setFlexGrow(0);
    }

    private void createParkingLotColumn() {
        //parkingLotIdCol = grid.addColumn(ParkingSpot::getParkingLot, "parkingLot").setHeader("CAPACITY").setWidth("120px").setFlexGrow(0);
    }

    private void addFiltersToGrid() {
        /*HeaderRow filterRow = grid.appendHeaderRow();

        TextField nameFilter = new TextField();
        nameFilter.setPlaceholder("Filter");
        nameFilter.setClearButtonVisible(true);
        nameFilter.setWidth("100%");
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.addValueChangeListener(event -> dataProvider.addFilter(
                lot -> StringUtils.containsIgnoreCase(lot.getName(), nameFilter.getValue())));
        filterRow.getCell(nameColumn).setComponent(nameFilter);*/
    }
}