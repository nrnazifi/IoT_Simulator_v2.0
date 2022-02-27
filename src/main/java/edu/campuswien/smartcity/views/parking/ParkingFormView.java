package edu.campuswien.smartcity.views.parking;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.service.ParkingLotService;
import edu.campuswien.smartcity.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Id("numberOfOccupiedAtStart")
    private IntegerField numberOfOccupiedAtStart;

    @Id("save")
    private Button save;
    @Id("cancel")
    private Button cancel;

    private Binder<ParkingLot> binder = new Binder(ParkingLot.class);
    private ParkingLotService parkingLotService;

    public ParkingFormView(@Autowired ParkingLotService parkingLotService) {
        this.parkingLotService =parkingLotService;

        binder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> saveForm());
    }

    private void clearForm() {
        binder.setBean(new ParkingLot());
    }

    private void saveForm(){
        parkingLotService.update(binder.getBean());
        Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
        clearForm();
    }
}
