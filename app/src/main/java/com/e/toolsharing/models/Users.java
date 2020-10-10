package com.e.toolsharing.models;

public class Users {
    private String name;
    private String phone;
    private String password;
    private String username;
    private String email;
    private String image;

    public Users()
    {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Users(String name, String phone, String password, String username, String email, String image) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.username = username;
        this.email=email;
        this.image=image;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }





}
