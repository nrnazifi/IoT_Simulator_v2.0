package edu.campuswien.smartcity.views.parking;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.entity.ParkingSpot;
import edu.campuswien.smartcity.data.entity.TimeBasedData;
import edu.campuswien.smartcity.data.entity.TimeTypeEnum;
import edu.campuswien.smartcity.data.service.ParkingLotService;
import edu.campuswien.smartcity.data.service.ParkingSpotService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Tag("parking-form-view")
@JsModule("./views/parking/parking-form-view.ts")
@Uses(Icon.class)
public class ParkingFormView extends LitTemplate {
    private static final long serialVersionUID = -8065584460817633835L;

    // Main fields
    @Id("name")
    private TextField name;
    @Id("description")
    private TextArea description;
    @Id("capacity")
    private IntegerField capacity;
    @Id("startId")
    private IntegerField startId;
    @Id("numberOfOccupiedAtStart")
    private IntegerField numberOfOccupiedAtStart;

    // Average of occupied
    // How long does each vehicle stay in the parking lot on average?
    @Id("occupiedRadio")
    private RadioButtonGroup<TimeTypeEnum> occupiedRadio;
    @Id("generallyOccupiedDetail")
    private Span generallyOccupiedDetail;
    @Id("workRestTimeOccupiedDetail")
    private Span workRestTimeOccupiedDetail;
    @Id("weekdaysOccupiedDetail")
    private Span weekdaysOccupiedDetail;
    @Id("averageOccupied_day")
    private IntegerField averageOccupiedDay;
    @Id("averageOccupied_night")
    private IntegerField averageOccupiedNight;
    @Id("workdayOccupied_day")
    private IntegerField workdayOccupiedDay;
    @Id("workdayOccupied_night")
    private IntegerField workdayOccupiedNight;
    @Id("weekendOccupied_day")
    private IntegerField weekendOccupiedDay;
    @Id("weekendOccupied_night")
    private IntegerField weekendOccupiedNight;
    @Id("holidayOccupied_day")
    private IntegerField holidayOccupiedDay;
    @Id("holidayOccupied_night")
    private IntegerField holidayOccupiedNight;
    @Id("mondayOccupied_day")
    private IntegerField mondayOccupiedDay;
    @Id("mondayOccupied_night")
    private IntegerField mondayOccupiedNight;
    @Id("tuesdayOccupied_day")
    private IntegerField tuesdayOccupiedDay;
    @Id("tuesdayOccupied_night")
    private IntegerField tuesdayOccupiedNight;
    @Id("wednesdayOccupied_day")
    private IntegerField wednesdayOccupiedDay;
    @Id("wednesdayOccupied_night")
    private IntegerField wednesdayOccupiedNight;
    @Id("thursdayOccupied_day")
    private IntegerField thursdayOccupiedDay;
    @Id("thursdayOccupied_night")
    private IntegerField thursdayOccupiedNight;
    @Id("fridayOccupied_day")
    private IntegerField fridayOccupiedDay;
    @Id("fridayOccupied_night")
    private IntegerField fridayOccupiedNight;
    @Id("saturdayOccupied_day")
    private IntegerField saturdayOccupiedDay;
    @Id("saturdayOccupied_night")
    private IntegerField saturdayOccupiedNight;
    @Id("sundayOccupied_day")
    private IntegerField sundayOccupiedDay;
    @Id("sundayOccupied_night")
    private IntegerField sundayOccupiedNight;

    // Average of requests
    // How many spot statuses are changed in each period on average?
    @Id("requestRadio")
    private RadioButtonGroup<TimeTypeEnum> requestRadio;
    @Id("generallyRequestDetail")
    private Span generallyRequestDetail;
    @Id("workRestTimeRequestDetail")
    private Span workRestTimeRequestDetail;
    @Id("weekdaysRequestDetail")
    private Span weekdaysRequestDetail;
    @Id("averageRequest_day")
    private IntegerField averageRequestDay;
    @Id("averageRequest_night")
    private IntegerField averageRequestNight;
    @Id("workdayRequest_day")
    private IntegerField workdayRequestDay;
    @Id("workdayRequest_night")
    private IntegerField workdayRequestNight;
    @Id("weekendRequest_day")
    private IntegerField weekendRequestDay;
    @Id("weekendRequest_night")
    private IntegerField weekendRequestNight;
    @Id("holidayRequest_day")
    private IntegerField holidayRequestDay;
    @Id("holidayRequest_night")
    private IntegerField holidayRequestNight;
    @Id("mondayRequest_day")
    private IntegerField mondayRequestDay;
    @Id("mondayRequest_night")
    private IntegerField mondayRequestNight;
    @Id("tuesdayRequest_day")
    private IntegerField tuesdayRequestDay;
    @Id("tuesdayRequest_night")
    private IntegerField tuesdayRequestNight;
    @Id("wednesdayRequest_day")
    private IntegerField wednesdayRequestDay;
    @Id("wednesdayRequest_night")
    private IntegerField wednesdayRequestNight;
    @Id("thursdayRequest_day")
    private IntegerField thursdayRequestDay;
    @Id("thursdayRequest_night")
    private IntegerField thursdayRequestNight;
    @Id("fridayRequest_day")
    private IntegerField fridayRequestDay;
    @Id("fridayRequest_night")
    private IntegerField fridayRequestNight;
    @Id("saturdayRequest_day")
    private IntegerField saturdayRequestDay;
    @Id("saturdayRequest_night")
    private IntegerField saturdayRequestNight;
    @Id("sundayRequest_day")
    private IntegerField sundayRequestDay;
    @Id("sundayRequest_night")
    private IntegerField sundayRequestNight;

    // Buttons
    @Id("btnSave")
    private Button btnSave;
    @Id("cancel")
    private Button btnCancel;
    @Id("btnDelete")
    private Button btnDelete;
    @Id("btnGenerate")
    private Button btnGenerate;
    @Id("btnShowSpots")
    private Button btnShowSpots;

    private Binder<ParkingLot> binder = new BeanValidationBinder<>(ParkingLot.class);
    private ParkingLotService parkingLotService;
    private ParkingTemplateView mainView;
    private Dialog dialog;

    public ParkingFormView(ParkingTemplateView mainView, Dialog dialog, ParkingLotService parkingLotService) {
        this.mainView = mainView;
        this.dialog = dialog;
        this.parkingLotService =parkingLotService;

        binder.bindInstanceFields(this);

        //Set Icons for buttons
        btnSave.setIcon(new Icon(VaadinIcon.HARDDRIVE_O));
        btnDelete.setIcon(new Icon(VaadinIcon.TRASH));
        btnGenerate.setIcon(new Icon(VaadinIcon.MAGIC));
        btnShowSpots.setIcon(new Icon(VaadinIcon.LINES_LIST));
        //Set icons in right side
        btnSave.setIconAfterText(true);
        btnDelete.setIconAfterText(true);
        btnGenerate.setIconAfterText(true);
        btnShowSpots.setIconAfterText(true);

        btnCancel.addClickListener(e -> closeForm());
        btnSave.addClickListener(e -> saveForm());
        btnDelete.addClickListener(e -> delete());
        btnGenerate.addClickListener(e -> generateSpots());
        btnShowSpots.addClickListener(e -> showSpots());

        createOccupiedDetails();
        createRequestDetails();
    }

    private void createOccupiedDetails() {
        generallyOccupiedDetail.setVisible(true);
        weekdaysOccupiedDetail.setVisible(false);
        workRestTimeOccupiedDetail.setVisible(false);

        occupiedRadio.setItems(TimeTypeEnum.values());
        occupiedRadio.setValue(TimeTypeEnum.Generally);
        occupiedRadio.addValueChangeListener(e -> {
            if(e.getValue().equals(TimeTypeEnum.Generally)) {
                generallyOccupiedDetail.setVisible(true);
                weekdaysOccupiedDetail.setVisible(false);
                workRestTimeOccupiedDetail.setVisible(false);
            } else if(e.getValue().equals(TimeTypeEnum.WeekDays)) {
                generallyOccupiedDetail.setVisible(false);
                weekdaysOccupiedDetail.setVisible(true);
                workRestTimeOccupiedDetail.setVisible(false);
            } else if(e.getValue().equals(TimeTypeEnum.WorkRestTime)) {
                generallyOccupiedDetail.setVisible(false);
                weekdaysOccupiedDetail.setVisible(false);
                workRestTimeOccupiedDetail.setVisible(true);
            }
        });

        averageOccupiedDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        averageOccupiedNight.setPrefixComponent(VaadinIcon.MOON.create());

        weekendOccupiedDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        weekendOccupiedNight.setPrefixComponent(VaadinIcon.MOON.create());
        workdayOccupiedDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        workdayOccupiedNight.setPrefixComponent(VaadinIcon.MOON.create());
        holidayOccupiedDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        holidayOccupiedNight.setPrefixComponent(VaadinIcon.MOON.create());

        mondayOccupiedDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        mondayOccupiedNight.setPrefixComponent(VaadinIcon.MOON.create());
        tuesdayOccupiedDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        tuesdayOccupiedNight.setPrefixComponent(VaadinIcon.MOON.create());
        wednesdayOccupiedDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        wednesdayOccupiedNight.setPrefixComponent(VaadinIcon.MOON.create());
        thursdayOccupiedDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        thursdayOccupiedNight.setPrefixComponent(VaadinIcon.MOON.create());
        fridayOccupiedDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        fridayOccupiedNight.setPrefixComponent(VaadinIcon.MOON.create());
        saturdayOccupiedDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        saturdayOccupiedNight.setPrefixComponent(VaadinIcon.MOON.create());
        sundayOccupiedDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        sundayOccupiedNight.setPrefixComponent(VaadinIcon.MOON.create());
    }

    private void createRequestDetails() {
        generallyRequestDetail.setVisible(true);
        weekdaysRequestDetail.setVisible(false);
        workRestTimeRequestDetail.setVisible(false);

        requestRadio.setItems(TimeTypeEnum.values());
        requestRadio.setValue(TimeTypeEnum.Generally);
        requestRadio.addValueChangeListener(e -> {
            if(e.getValue().equals(TimeTypeEnum.Generally)) {
                generallyRequestDetail.setVisible(true);
                weekdaysRequestDetail.setVisible(false);
                workRestTimeRequestDetail.setVisible(false);
            } else if(e.getValue().equals(TimeTypeEnum.WeekDays)) {
                generallyRequestDetail.setVisible(false);
                weekdaysRequestDetail.setVisible(true);
                workRestTimeRequestDetail.setVisible(false);
            } else if(e.getValue().equals(TimeTypeEnum.WorkRestTime)) {
                generallyRequestDetail.setVisible(false);
                weekdaysRequestDetail.setVisible(false);
                workRestTimeRequestDetail.setVisible(true);
            }
        });

        averageRequestDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        averageRequestNight.setPrefixComponent(VaadinIcon.MOON.create());

        weekendRequestDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        weekendRequestNight.setPrefixComponent(VaadinIcon.MOON.create());
        workdayRequestDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        workdayRequestNight.setPrefixComponent(VaadinIcon.MOON.create());
        holidayRequestDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        holidayRequestNight.setPrefixComponent(VaadinIcon.MOON.create());

        mondayRequestDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        mondayRequestNight.setPrefixComponent(VaadinIcon.MOON.create());
        tuesdayRequestDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        tuesdayRequestNight.setPrefixComponent(VaadinIcon.MOON.create());
        wednesdayRequestDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        wednesdayRequestNight.setPrefixComponent(VaadinIcon.MOON.create());
        thursdayRequestDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        thursdayRequestNight.setPrefixComponent(VaadinIcon.MOON.create());
        fridayRequestDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        fridayRequestNight.setPrefixComponent(VaadinIcon.MOON.create());
        saturdayRequestDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        saturdayRequestNight.setPrefixComponent(VaadinIcon.MOON.create());
        sundayRequestDay.setPrefixComponent(VaadinIcon.SUN_O.create());
        sundayRequestNight.setPrefixComponent(VaadinIcon.MOON.create());
    }

    public void setParkingLot(ParkingLot parkingLot) {
        binder.setBean(parkingLot);

        if (parkingLot == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
            setOccupiedDetails(parkingLot);
            setRequestDetails(parkingLot);

            if(parkingLot.getId() == null) {
                //new: disable buttons
                btnDelete.setVisible(false);
                btnGenerate.setVisible(false);
                btnShowSpots.setEnabled(false);
            } else {
                //edit: enable buttons
                btnDelete.setVisible(true);
                btnGenerate.setVisible(true);
                if(parkingLot.getSpots().isEmpty()) {
                    btnShowSpots.setEnabled(false);
                } else {
                    btnShowSpots.setEnabled(true);
                }
            }
        }

    }

    private void setOccupiedDetails(ParkingLot parkingLot) {
        if (parkingLot.getHowLongParked() != null) {
            TimeBasedData occupied = parkingLot.getHowLongParked();
            occupiedRadio.setValue(occupied.getTimeType());

            averageOccupiedDay.setValue(occupied.getAverageDay());
            averageOccupiedNight.setValue(occupied.getAverageNight());

            weekendOccupiedDay.setValue(occupied.getWeekendDay());
            weekendOccupiedNight.setValue(occupied.getWeekendNight());
            workdayOccupiedDay.setValue(occupied.getWorkdayDay());
            workdayOccupiedNight.setValue(occupied.getWorkdayNight());
            holidayOccupiedDay.setValue(occupied.getHolidayDay());
            holidayOccupiedNight.setValue(occupied.getHolidayNight());

            mondayOccupiedDay.setValue(occupied.getMondayDay());
            mondayOccupiedNight.setValue(occupied.getMondayNight());
            tuesdayOccupiedDay.setValue(occupied.getTuesdayDay());
            tuesdayOccupiedNight.setValue(occupied.getTuesdayNight());
            wednesdayOccupiedDay.setValue(occupied.getWednesdayDay());
            wednesdayOccupiedNight.setValue(occupied.getWednesdayNight());
            thursdayOccupiedDay.setValue(occupied.getThursdayDay());
            thursdayOccupiedNight.setValue(occupied.getThursdayNight());
            fridayOccupiedDay.setValue(occupied.getFridayDay());
            fridayOccupiedNight.setValue(occupied.getFridayNight());
            saturdayOccupiedDay.setValue(occupied.getSaturdayDay());
            saturdayOccupiedNight.setValue(occupied.getSaturdayNight());
            sundayOccupiedDay.setValue(occupied.getSundayDay());
            sundayOccupiedNight.setValue(occupied.getSundayNight());
        }
    }

    private void setRequestDetails(ParkingLot parkingLot) {
        if (parkingLot.getHowManyChanged() != null) {
            TimeBasedData request = parkingLot.getHowManyChanged();
            requestRadio.setValue(request.getTimeType());

            averageRequestDay.setValue(request.getAverageDay());
            averageRequestNight.setValue(request.getAverageNight());

            weekendRequestDay.setValue(request.getWeekendDay());
            weekendRequestNight.setValue(request.getWeekendNight());
            workdayRequestDay.setValue(request.getWorkdayDay());
            workdayRequestNight.setValue(request.getWorkdayNight());
            holidayRequestDay.setValue(request.getHolidayDay());
            holidayRequestNight.setValue(request.getHolidayNight());

            mondayRequestDay.setValue(request.getMondayDay());
            mondayRequestNight.setValue(request.getMondayNight());
            tuesdayRequestDay.setValue(request.getTuesdayDay());
            tuesdayRequestNight.setValue(request.getTuesdayNight());
            wednesdayRequestDay.setValue(request.getWednesdayDay());
            wednesdayRequestNight.setValue(request.getWednesdayNight());
            thursdayRequestDay.setValue(request.getThursdayDay());
            thursdayRequestNight.setValue(request.getThursdayNight());
            fridayRequestDay.setValue(request.getFridayDay());
            fridayRequestNight.setValue(request.getFridayNight());
            saturdayRequestDay.setValue(request.getSaturdayDay());
            saturdayRequestNight.setValue(request.getSaturdayNight());
            sundayRequestDay.setValue(request.getSundayDay());
            sundayRequestNight.setValue(request.getSundayNight());
        }
    }

    private void closeForm() {
        dialog.close();
        setParkingLot(null);
    }

    private void saveForm(){
        ParkingLot parkingLot = binder.getBean();
        parkingLot.setLastUpdatedTime(LocalDateTime.now());
        saveOccupiedDetails(parkingLot);
        saveRequestDetails(parkingLot);
        parkingLotService.update(parkingLot);

        closeForm();
        mainView.updateContent();
        Notification notification = Notification.show(parkingLot.getName() + " is stored!", 5000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void saveOccupiedDetails(ParkingLot parkingLot) {
        if (parkingLot.getHowLongParked() != null && parkingLot.getHowLongParked().getId() != null) {
            mainView.getTimeBasedDataService().delete(parkingLot.getHowLongParked().getId());
        }

        TimeBasedData occupied = new TimeBasedData();
        occupied.setTimeType(occupiedRadio.getValue());

        occupied.setAverageDay(averageOccupiedDay.getValue());
        occupied.setAverageNight(averageOccupiedNight.getValue());

        occupied.setWeekendDay(weekendOccupiedDay.getValue());
        occupied.setWeekendNight(weekendOccupiedNight.getValue());
        occupied.setWorkdayDay(workdayOccupiedDay.getValue());
        occupied.setWorkdayNight(workdayOccupiedNight.getValue());
        occupied.setHolidayDay(holidayOccupiedDay.getValue());
        occupied.setHolidayNight(holidayOccupiedNight.getValue());

        occupied.setMondayDay(mondayOccupiedDay.getValue());
        occupied.setMondayNight(mondayOccupiedNight.getValue());
        occupied.setTuesdayDay(tuesdayOccupiedDay.getValue());
        occupied.setTuesdayNight(tuesdayOccupiedNight.getValue());
        occupied.setWednesdayDay(wednesdayOccupiedDay.getValue());
        occupied.setWednesdayNight(wednesdayOccupiedNight.getValue());
        occupied.setThursdayDay(thursdayOccupiedDay.getValue());
        occupied.setThursdayNight(thursdayOccupiedNight.getValue());
        occupied.setFridayDay(fridayOccupiedDay.getValue());
        occupied.setFridayNight(fridayOccupiedNight.getValue());
        occupied.setSaturdayDay(saturdayOccupiedDay.getValue());
        occupied.setSaturdayNight(saturdayOccupiedNight.getValue());
        occupied.setSundayDay(sundayOccupiedDay.getValue());
        occupied.setSundayNight(sundayOccupiedNight.getValue());

        mainView.getTimeBasedDataService().update(occupied);
        parkingLot.setHowLongParked(occupied);
    }

    private void saveRequestDetails(ParkingLot parkingLot) {
        if (parkingLot.getHowManyChanged() != null && parkingLot.getHowManyChanged().getId() != null) {
            mainView.getTimeBasedDataService().delete(parkingLot.getHowManyChanged().getId());
        }

        TimeBasedData request = new TimeBasedData();
        request.setTimeType(requestRadio.getValue());

        request.setAverageDay(averageRequestDay.getValue());
        request.setAverageNight(averageRequestNight.getValue());

        request.setWeekendDay(weekendRequestDay.getValue());
        request.setWeekendNight(weekendRequestNight.getValue());
        request.setWorkdayDay(workdayRequestDay.getValue());
        request.setWorkdayNight(workdayRequestNight.getValue());
        request.setHolidayDay(holidayRequestDay.getValue());
        request.setHolidayNight(holidayRequestNight.getValue());

        request.setMondayDay(mondayRequestDay.getValue());
        request.setMondayNight(mondayRequestNight.getValue());
        request.setTuesdayDay(tuesdayRequestDay.getValue());
        request.setTuesdayNight(tuesdayRequestNight.getValue());
        request.setWednesdayDay(wednesdayRequestDay.getValue());
        request.setWednesdayNight(wednesdayRequestNight.getValue());
        request.setThursdayDay(thursdayRequestDay.getValue());
        request.setThursdayNight(thursdayRequestNight.getValue());
        request.setFridayDay(fridayRequestDay.getValue());
        request.setFridayNight(fridayRequestNight.getValue());
        request.setSaturdayDay(saturdayRequestDay.getValue());
        request.setSaturdayNight(saturdayRequestNight.getValue());
        request.setSundayDay(sundayRequestDay.getValue());
        request.setSundayNight(sundayRequestNight.getValue());

        mainView.getTimeBasedDataService().update(request);
        parkingLot.setHowManyChanged(request);
    }

    private void delete() {
        ParkingLot parkingLot = binder.getBean();
        parkingLotService.delete(parkingLot);

        closeForm();
        mainView.updateContent();
        Notification.show(parkingLot.getName() + " is deleted!", 5000, Notification.Position.TOP_CENTER);
    }

    private void generateSpots() {
        ParkingSpotService parkingSpotService = mainView.getParkingSpotService();
        ParkingLot parkingLot = binder.getBean();

        if(!parkingLot.getSpots().isEmpty()) {
            parkingLot.getSpots().clear();
            parkingLotService.update(parkingLot);
        }

        String name = parkingLot.getName();
        List<ParkingSpot> spots = new ArrayList<ParkingSpot>();
        for(long i = parkingLot.getStartId(); i < parkingLot.getCapacity() + parkingLot.getStartId(); i++) {
            ParkingSpot spot = new ParkingSpot();
            spot.setParkingLot(parkingLot);
            spot.setDeviceId(name+ "_" +i);
            spots.add(spot);
            parkingSpotService.update(spot);
        }
        parkingLot.getSpots().addAll(spots);
        parkingLot.setLastUpdatedTime(LocalDateTime.now());
        //parkingLotService.update(parkingLot);

        //parkingLot = parkingLotService.get(parkingLot.getId()).get();
        mainView.updateContent();
        Notification notification = Notification.show("Spots of the " + parkingLot.getName() + " are generated!", 5000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        btnShowSpots.setEnabled(true);
        btnGenerate.setEnabled(false);
    }

    private void showSpots() {
        ParkingLot parkingLot = binder.getBean();
        if(parkingLot.getId() != null) {
            closeForm();
            mainView.onShowSpots(parkingLot);
        }
    }

}
