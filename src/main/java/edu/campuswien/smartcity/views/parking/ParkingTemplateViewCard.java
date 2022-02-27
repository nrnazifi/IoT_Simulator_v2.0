package edu.campuswien.smartcity.views.parking;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
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

    @Id
    private Image image;
    @Id
    private Span header;
    @Id
    private Span subtitle;
    @Id
    private Paragraph text;

    @Id
    private Anchor delete;
    @Id
    private Anchor edit;
    @Id
    private Anchor duplicate;
    @Id
    private Anchor export;
    @Id
    private Anchor spotList;

    public ParkingTemplateViewCard(ParkingLot parkingLot) {
        //this.image.setSrc("");
        this.image.setAlt(parkingLot.getName());
        this.header.setText(parkingLot.getName());
        this.subtitle.setText(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(parkingLot.getLastUpdatedTime()));
        this.text.setText(parkingLot.getDescription());

        this.delete.setHref("/delete");
        this.edit.setHref("/edit");
        this.duplicate.setHref("/duplicate");
        this.export.setHref("/export");
        this.spotList.setHref("/spotList");
    }
}