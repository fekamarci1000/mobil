package com.example.csempe.model;

public class userModel {
    private String username;
    private String email;
    private String password;
    private String postalCode;
    private String address;

    // Konstruktor
    public userModel(String username, String email, String password, String postalCode, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.postalCode = postalCode;
        this.address = address;
    }

    // Getter és setter metódusok
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
