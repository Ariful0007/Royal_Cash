package com.example.royalcash.model;

public class Profit_package {
    String email,porfit;

    public Profit_package() {
    }

    public Profit_package(String email, String porfit) {
        this.email = email;
        this.porfit = porfit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPorfit() {
        return porfit;
    }

    public void setPorfit(String porfit) {
        this.porfit = porfit;
    }
}
