package com.example.application.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Stock extends AbstractEntity implements Cloneable{

    // Id en AbstractEntity

    @NotNull
    @NotNull(message = "Quantity is required")
    private Double quantity;

    private LocalDate expirationDate;

    private String lot;

    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private StatusStock status;

    // relaciones
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull(message = "Product is required")
    private Product product;


    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public StatusStock getStatus() {
        return status;
    }

    public void setStatus(StatusStock status) {
        this.status = status;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
