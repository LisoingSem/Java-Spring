package com.example.productmenegement.model;

public class Product {
    private int id;
    private String name;
    private double pricePerUnit;
    private boolean activeForSell;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public boolean isActiveForSell() {
        return activeForSell;
    }

    public void setActiveForSell(boolean activeForSell) {
        this.activeForSell = activeForSell;
    }
}