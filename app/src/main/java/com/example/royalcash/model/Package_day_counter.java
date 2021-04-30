package com.example.royalcash.model;

public class Package_day_counter {
    String email,counter;

    public Package_day_counter() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public Package_day_counter(String email, String counter) {
        this.email = email;
        this.counter = counter;
    }

}
