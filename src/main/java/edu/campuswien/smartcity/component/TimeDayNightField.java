package edu.campuswien.smartcity.component;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import edu.campuswien.smartcity.data.entity.DayTypeEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class TimeDayNightField extends CustomField<List<Integer>> {

    private final IntegerField day;
    private final IntegerField night;
    private DayTypeEnum dayType;

    public TimeDayNightField(String id, DayTypeEnum dayType) {
        this();
        setLabel(dayType.getDescription());
        setId(id);
        this.dayType = dayType;
    }

    public TimeDayNightField() {
        day = new IntegerField();
        day.setPlaceholder("0");
        day.setPrefixComponent(VaadinIcon.SUN_O.create());
        day.addClassNames("day-night-field");

        night = new IntegerField();
        night.setPlaceholder("0");
        night.setPrefixComponent(VaadinIcon.MOON.create());
        night.addClassNames("day-night-field");

        VerticalLayout verticalLayout = new VerticalLayout(day,night);
        add(verticalLayout);
    }

    @Override
    protected List<Integer> generateModelValue() {
        return Arrays.asList(day.getValue(), night.getValue());
    }

    @Override
    protected void setPresentationValue(List<Integer> times) {
        day.setValue(times.get(0));
        night.setValue(times.get(1));
    }
}
