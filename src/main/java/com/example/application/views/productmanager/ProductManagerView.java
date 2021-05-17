package com.example.application.views.productmanager;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

@Route(value = "product-manager", layout = MainView.class)
@PageTitle("Product Manager")
public class ProductManagerView extends Div {

    public ProductManagerView() {
        addClassName("product-manager-view");
        add(new Text("Content placeholder"));
    }

}
