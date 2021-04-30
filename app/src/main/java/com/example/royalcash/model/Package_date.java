package com.example.royalcash.model;

public class Package_date {
    String email,date,time12,geta;

    public Package_date() {
    }

    public Package_date(String email, String date, String time12, String geta) {
        this.email = email;
        this.date = date;
        this.time12 = time12;
        this.geta = geta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime12() {
        return time12;
    }

    public void setTime12(String time12) {
        this.time12 = time12;
    }

    public String getGeta() {
        return geta;
    }

    public void setGeta(String geta) {
        this.geta = geta;
    }
}
