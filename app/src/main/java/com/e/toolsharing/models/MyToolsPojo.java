package com.e.toolsharing.models;

public class MyToolsPojo {

    public MyToolsPojo()
    {

    }


    public MyToolsPojo(String time, String image, String name, String category, String price, String desc, String condition, String status, String posted_by, String booked_by, String approve_by_owner, String date, String pid, String from_date, String to_date, String total_price) {
        this.time = time;
        this.image = image;
        this.name = name;
        this.category = category;
        this.price = price;
        this.desc = desc;
        this.condition = condition;
        this.status = status;
        this.posted_by = posted_by;
        this.booked_by = booked_by;
        this.approve_by_owner = approve_by_owner;
        this.date = date;
        this.pid = pid;
        this.from_date = from_date;
        this.to_date = to_date;
        this.total_price = total_price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(String posted_by) {
        this.posted_by = posted_by;
    }

    public String getBooked_by() {
        return booked_by;
    }

    public void setBooked_by(String booked_by) {
        this.booked_by = booked_by;
    }

    public String getApprove_by_owner() {
        return approve_by_owner;
    }

    public void setApprove_by_owner(String approve_by_owner) {
        this.approve_by_owner = approve_by_owner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    private String time;
    private String image;
    private String name;
    private String category;
    private String price;
    private String desc;
    private String condition;
    private String status;
    private String posted_by;
    private String booked_by;
    private String approve_by_owner;
    private String date;
    private String pid;
    private String from_date;
    private String to_date;
    private String total_price;



}
