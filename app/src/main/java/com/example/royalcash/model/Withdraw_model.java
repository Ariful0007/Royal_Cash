package com.example.royalcash.model;

public class Withdraw_model {
    String email,ammount,methode,uuid;

    public Withdraw_model() {
    }

    public Withdraw_model(String email, String ammount, String methode, String uuid) {
        this.email = email;
        this.ammount = ammount;
        this.methode = methode;
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getMethode() {
        return methode;
    }

    public void setMethode(String methode) {
        this.methode = methode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
