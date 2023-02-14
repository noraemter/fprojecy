package com.example.androidproject.helper;

public class BillDetails {
    private String mealName;
    private double price;
    private int quantity;

    public BillDetails(String mealName, double price, int quantity) {
        this.mealName = mealName;
        this.price = price;
        this.quantity = quantity;
    }

    public BillDetails(String mealName, int quantity) {
        this.mealName = mealName;
        this.quantity = quantity;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Mala name: " + mealName + ",Quantity: " + quantity;

    }
}
