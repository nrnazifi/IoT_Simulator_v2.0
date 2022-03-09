package edu.campuswien.smartcity.views.simulation;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import edu.campuswien.smartcity.data.entity.ParkingLot;
import edu.campuswien.smartcity.data.entity.Simulation;
import edu.campuswien.smartcity.views.parking.ParkingTemplateView;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@JsModule("./views/simulation/simulation-view-card.ts")
@Tag("simulation-view-card")
public class SimulationViewCard extends LitTemplate {
    //Content
    @Id
    private Image image;
    @Id
    private Span header;
    @Id
    private Span subtitle;
    @Id
    private Paragraph text;
    //Actions
    @Id
    private Span delete;
    @Id
    private Span edit;

    public SimulationViewCard(SimulationView mainView, Simulation simulation) {
        //this.image.setSrc("");
        this.image.setAlt(simulation.getName());
        this.header.setText(simulation.getName());
        //this.subtitle.setText(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(parkingLot.getLastUpdatedTime()));
        //this.text.setText(parkingLot.getDescription());

        edit.addClickListener(e -> mainView.onEdit(simulation));
        delete.addClickListener(e -> mainView.onDelete(simulation));
    }
}