package com.example.royalcash.model;

public class Main_balance {
    String email,main_balance;

    public Main_balance() {
    }

    public Main_balance(String email, String main_balance) {
        this.email = email;
        this.main_balance = main_balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMain_balance() {
        return main_balance;
    }

    public void setMain_balance(String main_balance) {
        this.main_balance = main_balance;
    }
}
