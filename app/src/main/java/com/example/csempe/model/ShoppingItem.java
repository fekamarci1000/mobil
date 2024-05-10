package com.example.csempe.model;

public class ShoppingItem {
    private String name;
    private String info;
    private String price;
    private float ratings;
    private final int imageResource;

    public ShoppingItem(String name, String info, String price, float ratings, int imageResource) {
        this.name = name;
        this.info = info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
