package edu.campuswien.smartcity.views.parking;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import edu.campuswien.smartcity.data.entity.ParkingLot;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@JsModule("./views/parking/parking-template-view-card.ts")
@Tag("parking-template-view-card")
public class ParkingTemplateViewCard extends LitTemplate {
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
    @Id
    private Span duplicate;
    @Id
    private Span export;
    @Id
    private Span spotList;

    public ParkingTemplateViewCard(ParkingTemplateView mainView, ParkingLot parkingLot) {
        //this.image.setSrc("");
        this.image.setAlt(parkingLot.getName());
        this.header.setText(parkingLot.getName());
        this.subtitle.setText(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(parkingLot.getLastUpdatedTime()));
        this.text.setText(parkingLot.getDescription());

        edit.addClickListener(e -> mainView.onEdit(parkingLot));
        delete.addClickListener(e -> mainView.onDelete(parkingLot));
        duplicate.addClickListener(e -> mainView.onDuplicate(parkingLot));
        spotList.addClickListener(e -> mainView.onShowSpots(parkingLot));

        if(parkingLot.getSpots().isEmpty()) {
            spotList.setVisible(false);
        }
    }
}