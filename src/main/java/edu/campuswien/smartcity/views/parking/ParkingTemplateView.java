package edu.campuswien.smartcity.views.parking;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.littemplate.LitTemplate;
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

    public ParkingTemplateView(@Autowired ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;

        addClassNames("parking-template-view", "flex", "flex-col", "h-full");
        btnAdd.setIcon(new Icon(VaadinIcon.PLUS));
        btnImport.setIcon(new Icon(VaadinIcon.UPLOAD));
        btnAdd.addClickListener(e -> {
            UI.getCurrent().navigate(ParkingFormView.class);
        });
        btnImport.addClickListener(e -> {
           //Import temp
        });


        List<ParkingLot> parkingLots = parkingLotService.list();
        for (ParkingLot lot : parkingLots) {
            add(new ParkingTemplateViewCard(lot));
        }
    }

}
