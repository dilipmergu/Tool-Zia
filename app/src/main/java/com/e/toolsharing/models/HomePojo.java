package com.e.toolsharing.models;

import com.google.gson.annotations.SerializedName;

public class HomePojo {

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public HomePojo(String image, String name, String status, String price, String reviews) {
        this.image = image;
        this.name = name;
        this.status = status;
        this.price = price;
        this.reviews = reviews;
    }

    @SerializedName("image")
    private String image;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;

    @SerializedName("price")
    private String price;

    @SerializedName("reviews")
    private String reviews;
}
