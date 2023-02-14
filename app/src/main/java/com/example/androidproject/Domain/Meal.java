package com.example.androidproject.Domain;

import java.io.Serializable;

public class Meal implements Serializable {
    private int id;
    private String title;
    private String pic;
    private String description;
    private Double sellingPrice;
    private int numberInCart;

    public Meal(int id, String title, String pic, String description, Double sellingPrice) {
        this.id = id;
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.sellingPrice = sellingPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
