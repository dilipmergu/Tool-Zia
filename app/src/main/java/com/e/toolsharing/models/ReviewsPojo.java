package com.e.toolsharing.models;

public class ReviewsPojo {
    public ReviewsPojo(String pid, String image, String name, String rating, String review) {
        this.pid = pid;
        this.image = image;
        this.name = name;
        this.rating = rating;
        this.review = review;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    private String pid;
    private String image;
    private String name;
    private String rating;
    private String review;





    public ReviewsPojo()
    {

    }

}
