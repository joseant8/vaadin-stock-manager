package com.example.application.views.stocklist;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

@Route(value = "stock-list", layout = MainView.class)
@PageTitle("Stock List")
public class StockListView extends Div {

    public StockListView() {
        addClassName("stock-list-view");
        add(new Text("Content placeholder"));
    }

}
