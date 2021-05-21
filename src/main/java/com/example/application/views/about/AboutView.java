package com.example.application.views.about;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

@Route(value = "about", layout = MainView.class)
@PageTitle("About")
public class AboutView extends VerticalLayout {

    public AboutView() {
        addClassName("about-view");

        this.setPadding(true);
        add(new Text("Welcome to Stock Manager"));

        add(new HtmlComponent("br"));
        add(new HtmlComponent("hr"));

        add(new Text("Bienvenido a Stock Manager"));
    }

}
