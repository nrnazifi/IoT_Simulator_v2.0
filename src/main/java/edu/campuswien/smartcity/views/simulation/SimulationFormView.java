package edu.campuswien.smartcity.views.simulation;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import edu.campuswien.smartcity.data.enums.DataFormatEnum;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.enums.ProtocolEnum;
import edu.campuswien.smartcity.data.entity.Simulation;
import edu.campuswien.smartcity.data.service.ParkingLotService;
import edu.campuswien.smartcity.data.service.SimulationService;
import edu.campuswien.smartcity.views.job.JobView;
import edu.campuswien.smartcity.views.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

@Tag("simulation-form-view")
@JsModule("./views/simulation/simulation-form-view.ts")
@Uses(Icon.class)
public class SimulationFormView extends LitTemplate {
    private static final long serialVersionUID = 7738014728161920822L;

    private static final String SIMULATION_WITH_START_TIME = "Current";
    private static final String SIMULATION_WITH_CUSTOMIZE_TIME = "Customize";
    private static final String SCHEDULING_NOW = "Now";
    private static final String SCHEDULING_LATER = "Later";
    private static final String STORAGE_ON_DATABASE = "on Database";
    private static final String STORAGE_ON_SERVER = "on Server";

    @Id("name")
    private TextField name;
    @Id("description")
    private TextArea description;
    @Id("parkingLots")
    private ComboBox<ParkingLot> parkingLots;
    @Id("timeUnit")
    private NumberField timeUnit;
    @Id("calculationTimeUnit")
    private Span calculationTimeUnit;
    @Id("simulationTimeRadio")
    private RadioButtonGroup<String> simulationTimeRadio;
    @Id("simulationStartTime")
    private DateTimePicker simulationStartTime;
    @Id("simulationEndTime")
    private DateTimePicker simulationEndTime;
    @Id("schedulingRadio")
    private RadioButtonGroup<String> schedulingRadio;
    @Id("schedulingDateTime")
    private DateTimePicker schedulingDateTime;
    @Id("storageCheckBox")
    private CheckboxGroup<String> storageCheckBox;
    @Id("storageDetails")
    private Details storageDetails;
    @Id("endpointUri")
    private TextField endpointUri;
    @Id("protocolRadio")
    private RadioButtonGroup<ProtocolEnum> protocolRadio;
    @Id("dataFormatRadio")
    private RadioButtonGroup<DataFormatEnum> dataFormatRadio;

    @Id("btnSave")
    private Button btnSave;
    @Id("btnSaveRun")
    private Button btnSaveRun;
    @Id("cancel")
    private Button btnCancel;
    @Id("btnDelete")
    private Button btnDelete;

    private final Binder<Simulation> binder = new BeanValidationBinder<>(Simulation.class);
    private final SimulationService simulationService;
    private final SimulationView mainView;
    private final Dialog dialog;

    public SimulationFormView(SimulationView mainView, Dialog dialog, SimulationService simulationService,
                              ParkingLotService parkingLotService) {
        this.mainView = mainView;
        this.dialog = dialog;
        this.simulationService = simulationService;

        binder.bindInstanceFields(this);

        parkingLots.setItems(parkingLotService.list());
        parkingLots.setItemLabelGenerator(ParkingLot::getName);

        //Set Icons for buttons
        btnSave.setIcon(new Icon(VaadinIcon.HARDDRIVE_O));
        btnSaveRun.setIcon(new Icon(VaadinIcon.PLAY_CIRCLE_O));
        btnDelete.setIcon(new Icon(VaadinIcon.TRASH));
        //Set icons in right side
        btnSave.setIconAfterText(true);
        btnSaveRun.setIconAfterText(true);
        btnDelete.setIconAfterText(true);

        btnCancel.addClickListener(e -> closeForm());
        btnSave.addClickListener(e -> saveForm());
        btnSaveRun.addClickListener(e -> saveAndRun());
        btnDelete.addClickListener(e -> delete());

        simulationTimeRadio.setItems(SIMULATION_WITH_START_TIME, SIMULATION_WITH_CUSTOMIZE_TIME);
        simulationTimeRadio.setRenderer(new ComponentRenderer<>(item -> new Text(item + " time")));
        simulationStartTime.setVisible(false);
        simulationEndTime.setVisible(false);
        simulationTimeRadio.addValueChangeListener(e -> {
            simulationStartTime.setVisible(e.getValue().equals(SIMULATION_WITH_CUSTOMIZE_TIME));
            simulationEndTime.setVisible(e.getValue().equals(SIMULATION_WITH_CUSTOMIZE_TIME));
        });

        schedulingRadio.setItems(SCHEDULING_NOW, SCHEDULING_LATER);
        schedulingRadio.setRenderer(new ComponentRenderer<>(item -> new Text("Schedule " + item)));
        schedulingDateTime.setVisible(false);
        schedulingRadio.addValueChangeListener(e -> {
            schedulingDateTime.setVisible(e.getValue().equals(SCHEDULING_LATER));
        });

        storageCheckBox.setItems(STORAGE_ON_DATABASE, STORAGE_ON_SERVER);
        //storageCheckBox.select("on Database");
        storageDetails.setVisible(false);
        storageCheckBox.addValueChangeListener(event -> storageDetails.setVisible(event.getValue().contains(STORAGE_ON_SERVER)));

        protocolRadio.setItems(ProtocolEnum.values());
        protocolRadio.setItemEnabledProvider(e -> !e.equals(ProtocolEnum.MQTT));
        dataFormatRadio.setItems(DataFormatEnum.values());
        dataFormatRadio.setItemEnabledProvider(e -> !e.equals(DataFormatEnum.CSV) && !e.equals(DataFormatEnum.XML));

        timeUnit.addValueChangeListener(e -> calculateAndShowTimeUnit(e.getValue()));
    }

    public void setSimulation(Simulation simulation) {
        binder.setBean(simulation);

        if (simulation == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
            parkingLots.setValue(simulation.getParkingLot());
            //set simulation timing
            if(simulation.isCustomizeSimulationTime()) {
                simulationTimeRadio.setValue(SIMULATION_WITH_CUSTOMIZE_TIME);
                simulationStartTime.setValue(simulation.getSimulationStartTime());
                simulationEndTime.setValue(simulation.getSimulationEndTime());
            } else {
                simulationTimeRadio.setValue(SIMULATION_WITH_START_TIME);
                simulationStartTime.setValue(null);
                simulationEndTime.setValue(null);
            }
            //set Scheduling
            if(simulation.isScheduleNow()) {
                schedulingRadio.setValue(SCHEDULING_NOW);
                schedulingDateTime.setValue(null);
            } else {
                schedulingRadio.setValue(SCHEDULING_LATER);
                schedulingDateTime.setValue(simulation.getScheduleTime());
            }
            //set Storage
            List<String> selectedStorages = new ArrayList<String>();
            if(simulation.isOnDatabase()) {
                selectedStorages.add(STORAGE_ON_DATABASE);
            }
            if(simulation.isOnServer()) {
                selectedStorages.add(STORAGE_ON_SERVER);
            }
            storageCheckBox.select(selectedStorages);
            //Set Protocol
            protocolRadio.setValue(simulation.getProtocol());
            //Set data format
            dataFormatRadio.setValue(simulation.getDataFormat());

            if(simulation.getId() == null) {
                //new: disable buttons
                btnDelete.setVisible(false);
            } else {
                //edit: enable buttons
                btnDelete.setVisible(true);
            }
        }

    }

    private void closeForm() {
        dialog.close();
        setSimulation(null);
    }

    private Simulation saveForm(){
        Simulation simulation = binder.getBean();
        //update fields in the entity
        simulation.setParkingLot(parkingLots.getValue());

        boolean isSimTimeCustomized = simulationTimeRadio.getValue().equals(SIMULATION_WITH_CUSTOMIZE_TIME);
        simulation.setCustomizeSimulationTime(isSimTimeCustomized);
        simulation.setSimulationStartTime(isSimTimeCustomized ? simulationStartTime.getValue() : null);
        simulation.setSimulationEndTime(isSimTimeCustomized ? simulationEndTime.getValue() : null);

        simulation.setScheduleNow(schedulingRadio.getValue().equals(SCHEDULING_NOW));
        simulation.setScheduleTime(schedulingRadio.getValue().equals(SCHEDULING_NOW) ? null : schedulingDateTime.getValue());
        simulation.setOnDatabase(storageCheckBox.getValue().contains(STORAGE_ON_DATABASE));
        simulation.setOnServer(storageCheckBox.getValue().contains(STORAGE_ON_SERVER));
        if(simulation.isOnServer()) {
            simulation.setProtocol(protocolRadio.getValue());
            simulation.setDataFormat(dataFormatRadio.getValue());
        } else {
            simulation.setEndpointUri(null);
            simulation.setProtocol(null);
            simulation.setDataFormat(null);
        }
        simulationService.update(simulation);

        closeForm();
        mainView.updateContent();
        Notification notification = Notification.show(simulation.getName() + " is stored!", 5000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        return simulation;
    }

    private void saveAndRun() {
        Simulation simulation = saveForm();
        mainView.saveJob(simulation);

        ViewUtil.showNotification("A job is running! You can see it in the job list.",
                NotificationVariant.LUMO_SUCCESS, VaadinIcon.CHECK_CIRCLE, true, "View",
                clickEvent -> {
                    UI.getCurrent().navigate(JobView.class);
                });
    }

    private void delete() {
        Simulation simulation = binder.getBean();
        simulationService.delete(simulation);

        closeForm();
        mainView.updateContent();
        Notification.show(simulation.getName() + " is deleted!", 5000, Notification.Position.TOP_CENTER);
    }

    private void calculateAndShowTimeUnit(Double value) {
        if(value == null) return;

        if (value <= 0 || value > 100) {
            calculationTimeUnit.setText("The value is out of range (must be 0<x<=100).");
            calculationTimeUnit.setClassName("text-error");
            return;
        }

        String text = "A unit of simulation time will take ";
        if (value == 1) {
            text += "one second";
        } else if (value > 1) {
            text += value + " seconds";
        } else {
            text += value + " of a second";
        }
        calculationTimeUnit.setText(text);
        calculationTimeUnit.setClassName("");
    }

}
