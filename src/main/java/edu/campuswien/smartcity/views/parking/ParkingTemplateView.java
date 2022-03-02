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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.service.ParkingLotService;
import edu.campuswien.smartcity.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("Parking lot template")
@Route(value = "parking-list", layout = MainLayout.class)
@Tag("parking-template-view")
@JsModule("./views/parking/parking-template-view.ts")
@Uses(Icon.class)
public class ParkingTemplateView extends LitTemplate implements HasComponents, HasStyle {
    private ParkingLotService parkingLotService;

    @Id("btnAdd")
    private Button btnAdd;
    @Id("btnImport")
    private Button btnImport;
    @Id("itemList")
    private OrderedList orderedList;

    private Dialog dialog = new Dialog();
    private ParkingFormView parkingForm;

    public ParkingTemplateView(@Autowired ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;

        addClassNames("parking-template-view", "flex", "flex-col", "h-full");
        //Set icons
        btnAdd.setIcon(new Icon(VaadinIcon.PLUS));
        btnImport.setIcon(new Icon(VaadinIcon.UPLOAD));
        //put icons in the right side
        btnAdd.setIconAfterText(true);
        btnImport.setIconAfterText(true);

        dialog.getElement().setAttribute("aria-label", "Create new employee");
        parkingForm = new ParkingFormView(this, dialog, parkingLotService);
        parkingForm.setParkingLot(null);
        dialog.add(parkingForm);

        //Add listener
        btnAdd.addClickListener(e -> onAdd());

        updateContent();
    }

    private void onAdd() {
        parkingForm.setParkingLot(new ParkingLot());
        dialog.open();
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
        dialog.open();
    }

    protected void onDelete(ParkingLot parkingLot) {
        parkingLotService.delete(parkingLot);
        updateContent();
    }

}
