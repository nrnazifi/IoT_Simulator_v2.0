package edu.campuswien.smartcity.component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.icon.VaadinIcon;
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
        day.setWidth(4.5F, Unit.EM);
        //day.addClassNames();

        night = new IntegerField();
        night.setPlaceholder("0");
        night.setPrefixComponent(VaadinIcon.MOON.create());
        night.setWidth(4.5F, Unit.EM);

        add(day, new Text(" "), night);
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
