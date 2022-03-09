package edu.campuswien.smartcity.views.simulation;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.entity.Simulation;
import edu.campuswien.smartcity.data.service.ParkingLotService;
import edu.campuswien.smartcity.data.service.SimulationService;
import edu.campuswien.smartcity.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("Simulation")
@Route(value = "simulation", layout = MainLayout.class)
@Tag("simulation-view")
@JsModule("./views/simulation/simulation-view.ts")
@Uses(Icon.class)
public class SimulationView extends LitTemplate implements HasComponents, HasStyle{
    private static final long serialVersionUID = -1553207620632898350L;

    private SimulationService simulationService;
    private SimulationFormView simulationForm;

    @Id("btnNew")
    private Button btnNew;
    @Id("itemList")
    private OrderedList itemList;

    private Dialog dialog = new Dialog();

    @Autowired
    public SimulationView(SimulationService simulationService, ParkingLotService parkingLotService) {
        this.simulationService = simulationService;

        addClassNames("simulation-view", "flex", "flex-col", "h-full");
        //Set icons
        btnNew.setIcon(new Icon(VaadinIcon.PLUS));
        //put icons in the right side
        btnNew.setIconAfterText(true);

        dialog.getElement().setAttribute("aria-label", "Create new simulation");
        simulationForm = new SimulationFormView(this, dialog, simulationService, parkingLotService);
        simulationForm.setSimulation(null);
        dialog.add(simulationForm);

        //Add listener
        btnNew.addClickListener(e -> onNew());

        updateContent();
    }

    private void onNew() {
        simulationForm.setSimulation(new Simulation());
        dialog.open();
    }

    protected void updateContent() {
        itemList.removeAll();

        List<Simulation> simulations = simulationService.list();
        for (Simulation sim : simulations) {
            itemList.add(new SimulationViewCard(this, sim));
        }
    }

    protected void onEdit(Simulation simulation) {
        simulationForm.setSimulation(simulation);
        dialog.open();
    }

    protected void onDelete(Simulation simulation) {
        simulationService.delete(simulation);
        updateContent();
    }

}
