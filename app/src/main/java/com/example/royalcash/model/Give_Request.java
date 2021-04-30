package com.example.royalcash.model;

public class Give_Request {
    String email,name,phone,methode,ammount_in,date;

    public Give_Request() {
    }

    public Give_Request(String email, String name, String phone, String methode, String ammount_in, String date) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.methode = methode;
        this.ammount_in = ammount_in;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMethode() {
        return methode;
    }

    public void setMethode(String methode) {
        this.methode = methode;
    }

    public String getAmmount_in() {
        return ammount_in;
    }

    public void setAmmount_in(String ammount_in) {
        this.ammount_in = ammount_in;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
