package edu.campuswien.smartcity.views.parking;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.select.Select;
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

    @Id("import")
    private Button btnImport;

    public ParkingTemplateView(@Autowired ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;

        addClassNames("parking-template-view", "flex", "flex-col", "h-full");
//        import = new Button("Left", new Icon(VaadinIcon.ARROW_LEFT));
//        import.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        btnImport.addClickListener(e -> {
           System.out.println("ddd");
        });


        List<ParkingLot> parkinglots = parkingLotService.list();
        for (ParkingLot lot : parkinglots) {
            add(new ParkingTemplateViewCard(lot));
        }
    }

}
