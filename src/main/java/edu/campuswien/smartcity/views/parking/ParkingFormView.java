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
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import edu.campuswien.smartcity.component.TimeDayNightField;
import edu.campuswien.smartcity.data.entity.*;
import edu.campuswien.smartcity.data.enums.DayCategoryEnum;
import edu.campuswien.smartcity.data.enums.DayTypeEnum;
import edu.campuswien.smartcity.data.service.ParkingLotService;
import edu.campuswien.smartcity.data.service.ParkingSpotService;
import edu.campuswien.smartcity.data.service.TimeBasedDataService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Tag("parking-form-view")
@JsModule("./views/parking/parking-form-view.ts")
@Uses(Icon.class)
public class ParkingFormView extends LitTemplate {
    private static final long serialVersionUID = -8065584460817633835L;
    public static final String PREFIX_OCCUPIED_ID = "occupied_";
    public static final String PREFIX_REQUEST_ID = "request_";

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

    // Time fields
    @Id("daylight")
    private TimePicker daylight;
    @Id("darkness")
    private TimePicker darkness;

    // Average of occupied
    // How long does each vehicle stay in the parking lot on average?
    @Id("occupiedRadio")
    private RadioButtonGroup<DayCategoryEnum> occupiedRadio;
    @Id("generallyOccupiedDetail")
    private Span generallyOccupiedDetail;
    @Id("workRestTimeOccupiedDetail")
    private Span workRestTimeOccupiedDetail;
    @Id("weekdaysOccupiedDetail")
    private Span weekdaysOccupiedDetail;
    private List<TimeDayNightField> occupiedFields;

    // Average of requests
    // How many spot statuses are changed in each period on average?
    @Id("requestRadio")
    private RadioButtonGroup<DayCategoryEnum> requestRadio;
    @Id("generallyRequestDetail")
    private Span generallyRequestDetail;
    @Id("workRestTimeRequestDetail")
    private Span workRestTimeRequestDetail;
    @Id("weekdaysRequestDetail")
    private Span weekdaysRequestDetail;
    private List<TimeDayNightField> requestFields;

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

    private final Binder<ParkingLot> binder = new BeanValidationBinder<>(ParkingLot.class);
    private final ParkingLotService parkingLotService;
    private final ParkingTemplateView mainView;
    private final Dialog dialog;

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

        occupiedRadio.setItems(DayCategoryEnum.values());
        occupiedRadio.setValue(DayCategoryEnum.Generally);
        occupiedRadio.addValueChangeListener(e -> {
            if(e.getValue().equals(DayCategoryEnum.Generally)) {
                generallyOccupiedDetail.setVisible(true);
                weekdaysOccupiedDetail.setVisible(false);
                workRestTimeOccupiedDetail.setVisible(false);
            } else if(e.getValue().equals(DayCategoryEnum.WeekDays)) {
                generallyOccupiedDetail.setVisible(false);
                weekdaysOccupiedDetail.setVisible(true);
                workRestTimeOccupiedDetail.setVisible(false);
            } else if(e.getValue().equals(DayCategoryEnum.WorkRestTime)) {
                generallyOccupiedDetail.setVisible(false);
                weekdaysOccupiedDetail.setVisible(false);
                workRestTimeOccupiedDetail.setVisible(true);
            }
        });

        occupiedFields = new ArrayList<>();
        for (DayTypeEnum timeType : DayTypeEnum.values()) {
            TimeDayNightField field = new TimeDayNightField(PREFIX_OCCUPIED_ID + timeType.getName(), timeType);
            occupiedFields.add(field);
            if(timeType.getCategory().equals(DayCategoryEnum.Generally)) {
                generallyOccupiedDetail.add(field);
            } else if(timeType.getCategory().equals(DayCategoryEnum.WorkRestTime)) {
                workRestTimeOccupiedDetail.add(field);
            } else if(timeType.getCategory().equals(DayCategoryEnum.WeekDays)) {
                weekdaysOccupiedDetail.add(field);
            }
        }
    }

    private void createRequestDetails() {
        generallyRequestDetail.setVisible(true);
        weekdaysRequestDetail.setVisible(false);
        workRestTimeRequestDetail.setVisible(false);

        requestRadio.setItems(DayCategoryEnum.values());
        requestRadio.setValue(DayCategoryEnum.Generally);
        requestRadio.addValueChangeListener(e -> {
            if(e.getValue().equals(DayCategoryEnum.Generally)) {
                generallyRequestDetail.setVisible(true);
                weekdaysRequestDetail.setVisible(false);
                workRestTimeRequestDetail.setVisible(false);
            } else if(e.getValue().equals(DayCategoryEnum.WeekDays)) {
                generallyRequestDetail.setVisible(false);
                weekdaysRequestDetail.setVisible(true);
                workRestTimeRequestDetail.setVisible(false);
            } else if(e.getValue().equals(DayCategoryEnum.WorkRestTime)) {
                generallyRequestDetail.setVisible(false);
                weekdaysRequestDetail.setVisible(false);
                workRestTimeRequestDetail.setVisible(true);
            }
        });

        requestFields = new ArrayList<>();
        for (DayTypeEnum timeType : DayTypeEnum.values()) {
            TimeDayNightField field = new TimeDayNightField(PREFIX_REQUEST_ID + timeType.getName(), timeType);
            requestFields.add(field);
            if(timeType.getCategory().equals(DayCategoryEnum.Generally)) {
                generallyRequestDetail.add(field);
            } else if(timeType.getCategory().equals(DayCategoryEnum.WorkRestTime)) {
                workRestTimeRequestDetail.add(field);
            } else if(timeType.getCategory().equals(DayCategoryEnum.WeekDays)) {
                weekdaysRequestDetail.add(field);
            }
        }
    }

    public void setParkingLot(ParkingLot parkingLot) {
        binder.setBean(parkingLot);

        if (parkingLot == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();

            daylight.setValue(parkingLot.getDaylight() != null ? parkingLot.getDaylight() : LocalTime.of(6,0));
            darkness.setValue(parkingLot.getDarkness() != null ? parkingLot.getDarkness(): LocalTime.of(18,0));
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
        List<TimeBasedData> howLongParked = parkingLotService.findAllTimeBased4Occupied(parkingLot);
        if (howLongParked == null) {
            howLongParked = new ArrayList<>();
        }

        DayCategoryEnum category = setDetailsFields(occupiedFields, howLongParked, PREFIX_OCCUPIED_ID);
        occupiedRadio.setValue(category);
    }

    private void setRequestDetails(ParkingLot parkingLot) {
        List<TimeBasedData> howManyChanged = parkingLotService.findAllTimeBased4Request(parkingLot);
        if (howManyChanged == null) {
            howManyChanged = new ArrayList<>();
        }

        DayCategoryEnum category = setDetailsFields(requestFields, howManyChanged, PREFIX_REQUEST_ID);
        requestRadio.setValue(category);
    }

    private DayCategoryEnum setDetailsFields(List<TimeDayNightField> fields, List<TimeBasedData> tableTimes, String prefixId) {
        DayCategoryEnum category = DayCategoryEnum.Generally;

        for (TimeDayNightField field : fields) {
            boolean exist = false;
            for (TimeBasedData time : tableTimes) {
                if (field.getId().get().equals(prefixId + time.getDayType().getName())) {
                    category = time.getDayType().getCategory();
                    field.getDay().setValue(time.getValueDay());
                    field.getNight().setValue(time.getValueNight());
                    exist = true;
                    break;
                }
            }
            if(!exist) {
                field.getDay().setValue(null);
                field.getNight().setValue(null);
            }
        }
        return category;
    }

    private void closeForm() {
        dialog.close();
        setParkingLot(null);
    }

    private void saveForm(){
        ParkingLot parkingLot = binder.getBean();
        parkingLot.setLastUpdatedTime(LocalDateTime.now());
        parkingLotService.update(parkingLot);
        parkingLotService.removeAllTimeBasedData(parkingLot);
        saveOccupiedDetails(parkingLot);
        saveRequestDetails(parkingLot);

        closeForm();
        mainView.updateContent();
        Notification notification = Notification.show(parkingLot.getName() + " is stored!", 5000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void saveOccupiedDetails(ParkingLot parkingLot) {
        saveDetailsFields(occupiedFields, occupiedRadio.getValue(), parkingLot.getId(), ParkingLot.FIELD_NAME_HOW_LONG_PARKED);
    }

    private void saveRequestDetails(ParkingLot parkingLot) {
        saveDetailsFields(requestFields, requestRadio.getValue(), parkingLot.getId(), ParkingLot.FIELD_NAME_HOW_MANY_CHANGED);
    }

    private void saveDetailsFields(List<TimeDayNightField> fields, DayCategoryEnum category, long parkingId, String parkingFieldName) {
        TimeBasedDataService service = mainView.getTimeBasedDataService();
        for (TimeDayNightField field : fields) {
            if(field.getDayType().getCategory().equals(category)) {
                TimeBasedData time = new TimeBasedData();
                time.setParentId(parkingId);
                time.setParentFieldName(parkingFieldName);
                time.setDayType(field.getDayType());
                time.setValueDay(field.getDay().getValue() != null ? field.getDay().getValue() : 0);
                time.setValueNight(field.getNight().getValue() != null ? field.getNight().getValue() : 0);
                service.update(time);
            }
        }
    }

    private void delete() {
        ParkingLot parkingLot = binder.getBean();
        parkingLotService.delete(parkingLot);
        parkingLotService.removeAllTimeBasedData(parkingLot);

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
