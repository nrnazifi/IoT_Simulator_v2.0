package edu.campuswien.smartcity.views.parking;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.*;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.entity.TimeBasedData;
import edu.campuswien.smartcity.data.service.ParkingLotService;
import edu.campuswien.smartcity.data.service.ParkingSpotService;
import edu.campuswien.smartcity.data.service.TimeBasedDataService;
import edu.campuswien.smartcity.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Parking lot template")
@Route(value = "parking-list", layout = MainLayout.class)
@Tag("parking-template-view")
@JsModule("./views/parking/parking-template-view.ts")
@Uses(Icon.class)
public class ParkingTemplateView extends LitTemplate implements HasComponents, HasStyle{
    private static final long serialVersionUID = -15321095775433453L;

    private final ParkingLotService parkingLotService;
    private final ParkingSpotService parkingSpotService;
    private final TimeBasedDataService timeBasedDataService;

    @Id("btnAdd")
    private Button btnAdd;
    @Id("btnImport")
    private Button btnImport;
    @Id("itemList")
    private OrderedList orderedList;

    private final Dialog templateDialog = new Dialog();
    private final ParkingFormView parkingForm;

    @Autowired
    public ParkingTemplateView(ParkingLotService parkingLotService, ParkingSpotService parkingSpotService, TimeBasedDataService timeBasedDataService) {
        this.parkingLotService = parkingLotService;
        this.parkingSpotService = parkingSpotService;
        this.timeBasedDataService = timeBasedDataService;

        addClassNames("parking-template-view", "flex", "flex-col", "h-full");
        //Set icons
        btnAdd.setIcon(new Icon(VaadinIcon.PLUS));
        btnImport.setIcon(new Icon(VaadinIcon.UPLOAD));
        //put icons in the right side
        btnAdd.setIconAfterText(true);
        btnImport.setIconAfterText(true);

        templateDialog.getElement().setAttribute("aria-label", "Create new parking lot");
        parkingForm = new ParkingFormView(this, templateDialog, parkingLotService);
        parkingForm.setParkingLot(null);
        templateDialog.add(parkingForm);

        //Add listener
        btnAdd.addClickListener(e -> onAdd());

        updateContent();
    }

    private void onAdd() {
        parkingForm.setParkingLot(new ParkingLot());
        templateDialog.open();
    }

    protected void updateContent() {
        orderedList.removeAll();

        List<ParkingLot> parkingLots = parkingLotService.list();
        for (ParkingLot lot : parkingLots) {
            orderedList.add(new ParkingTemplateViewCard(this, lot));
        }
    }

    protected void onEdit(ParkingLot parkingLot) {
        parkingForm.setParkingLot(parkingLot);
        templateDialog.open();
    }

    protected void onDelete(ParkingLot parkingLot) {
        parkingLotService.delete(parkingLot);
        parkingLotService.removeAllTimeBasedData(parkingLot);
        updateContent();
    }

    protected void onShowSpots(ParkingLot parkingLot) {
        UI.getCurrent().navigate(ParkingSpotsView.class, new RouteParameters("parkingId", parkingLot.getId().toString()));
    }

    protected void onDuplicate(ParkingLot parkingLot) {
        ParkingLot copy = new ParkingLot();
        copy.setName("Copy of " + parkingLot.getName());
        copy.setCapacity(parkingLot.getCapacity());
        copy.setStartId(parkingLot.getStartId());
        copy.setNumberOfOccupiedAtStart(parkingLot.getNumberOfOccupiedAtStart());
        copy.setDarkness(parkingLot.getDarkness());
        copy.setDaylight(parkingLot.getDaylight());
        copy.setDescription(parkingLot.getDescription());

        parkingLotService.update(copy);

        List<TimeBasedData> occupancyTimes = parkingLotService.findAllTimeBased4Occupied(parkingLot);
        List<TimeBasedData> requestTimes = parkingLotService.findAllTimeBased4Request(parkingLot);
        List<TimeBasedData> timeBasedDataList = new ArrayList<TimeBasedData>(occupancyTimes);
        timeBasedDataList.addAll(requestTimes);

        for (TimeBasedData timeData : timeBasedDataList) {
            TimeBasedData copyTime = new TimeBasedData();
            copyTime.setParentId(copy.getId());
            copyTime.setParentFieldName(timeData.getParentFieldName());
            copyTime.setDayType(timeData.getDayType());
            copyTime.setValueDay(timeData.getValueDay());
            copyTime.setValueNight(timeData.getValueNight());
            timeBasedDataService.update(copyTime);
        }

        updateContent();
    }

    protected ParkingSpotService getParkingSpotService() {
        return parkingSpotService;
    }

    protected TimeBasedDataService getTimeBasedDataService() {
        return timeBasedDataService;
    }

}
