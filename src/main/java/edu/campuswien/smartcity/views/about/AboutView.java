package edu.campuswien.smartcity.views.about;

import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import edu.campuswien.smartcity.views.MainLayout;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Tag("about-view")
@JsModule("./views/about/about-view.ts")
public class AboutView extends LitTemplate implements HasStyle {

    public AboutView() {
        addClassNames("flex", "flex-col", "h-full");
    }
}
