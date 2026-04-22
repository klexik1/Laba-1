package com.khalchukov.entity;

public class SmartphoneEntity {

    private Integer id;
    private String model;
    private double price;

    public SmartphoneEntity() {
    }

    public SmartphoneEntity(String model, double price) {
        this.model = model;
        this.price = price;
    }

    public SmartphoneEntity(Integer id, String model, double price) {
        this.id = id;
        this.model = model;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SmartphoneEntity{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", price=" + price +
                '}';
    }
}
