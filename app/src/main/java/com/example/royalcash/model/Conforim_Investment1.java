package com.example.royalcash.model;

public class Conforim_Investment1 {
    String email, phonenumber, transcation, methode, date,ammount;

    public Conforim_Investment1() {
    }

    public Conforim_Investment1(String email,
                                String phonenumber, String transcation, String methode,
                                String date, String ammount) {
        this.email = email;
        this.phonenumber = phonenumber;
        this.transcation = transcation;
        this.methode = methode;
        this.date = date;
        this.ammount = ammount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getTranscation() {
        return transcation;
    }

    public void setTranscation(String transcation) {
        this.transcation = transcation;
    }

    public String getMethode() {
        return methode;
    }

    public void setMethode(String methode) {
        this.methode = methode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }
}