package edu.campuswien.smartcity.views.report;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import edu.campuswien.smartcity.views.MainLayout;

@PageTitle("Report View")
@Route(value = "report", layout = MainLayout.class)
@Tag("report-view")
@JsModule("./views/report/report-view.ts")
public class ReportView extends LitTemplate {
    private static final long serialVersionUID = 3450816484053818388L;


    public ReportView() {

    }
}
