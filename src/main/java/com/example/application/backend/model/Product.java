package com.example.application.backend.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Product extends AbstractEntity implements Cloneable{

    // Id en AbstractEntity

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private FamilyProduct family;

    private Double price;

    private Boolean active = true;

    // relaciones
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Stock> stocks = new LinkedList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FamilyProduct getFamily() {
        return family;
    }

    public void setFamily(FamilyProduct family) {
        this.family = family;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}
