package edu.campuswien.smartcity.views.utils;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ViewUtil {

    public static void showNotification(String msg, NotificationVariant theme, VaadinIcon vaadinIcon, boolean isClosable,
                                        String buttonText, ComponentEventListener<ClickEvent<Button>> buttonListener) {
        Notification notification = new Notification();

        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        if(vaadinIcon != null) {
            Icon icon = vaadinIcon.create();
            layout.add(icon);
        }

        Div info = new Div(new Text(msg));
        layout.add(info);

        if(buttonListener != null) {
            buttonText = buttonText != null ? buttonText : "View";
            Button actionBtn = new Button(buttonText, buttonListener);
            actionBtn.getStyle().set("margin", "0 0 0 var(--lumo-space-l)");
            layout.add(actionBtn);

            actionBtn.addClickListener(e -> notification.close());
        }

        if(isClosable) {
            Button closeBtn = new Button(
                    VaadinIcon.CLOSE_SMALL.create(),
                    clickEvent -> notification.close());
            closeBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            layout.add(closeBtn);
        }

        if(theme != null) {
            notification.addThemeVariants(theme);
        } else {
            notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
        }

        notification.add(layout);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.open();
    }
}
