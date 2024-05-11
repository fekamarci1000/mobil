package com.example.csempe.model;

public class ShoppingItem {

    private String name;
    private String brand;
    private String price;
    private float ratings;
    private int imageResource;

    public ShoppingItem() {
    }

    public ShoppingItem(String name, String brand, String price, float ratings, int imageResource) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.ratings = ratings;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public int getImageResource() {
        return imageResource;
    }
}
