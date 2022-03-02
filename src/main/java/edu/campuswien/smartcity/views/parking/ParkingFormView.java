package edu.campuswien.smartcity.views.parking;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.service.ParkingLotService;
import edu.campuswien.smartcity.views.MainLayout;

import java.time.LocalDateTime;

@PageTitle("Parking lot template")
@Route(value = "parking-form", layout = MainLayout.class)
@Tag("parking-form-view")
@JsModule("./views/parking/parking-form-view.ts")
@Uses(Icon.class)
public class ParkingFormView extends LitTemplate {
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
    }

    public void setParkingLot(ParkingLot parkingLot) {
        binder.setBean(parkingLot);

        if (parkingLot == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();

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

    private void closeForm() {
        dialog.close();
        setParkingLot(null);
    }

    private void saveForm(){
        ParkingLot parkingLot = binder.getBean();
        parkingLot.setLastUpdatedTime(LocalDateTime.now());
        parkingLotService.update(parkingLot);

        closeForm();
        mainView.updateContent();
        Notification.show(parkingLot.getName() + " is stored!");
    }

    private void delete() {
        ParkingLot parkingLot = binder.getBean();
        parkingLotService.delete(parkingLot);

        closeForm();
        mainView.updateContent();
        Notification.show(parkingLot.getName() + " is deleted!");
    }
}
