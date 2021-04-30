package com.example.royalcash.model;

public class Transcation_model {
    String email,investment_type,ammount;

    public Transcation_model() {
    }

    public Transcation_model(String email, String investment_type, String ammount) {
        this.email = email;
        this.investment_type = investment_type;
        this.ammount = ammount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvestment_type() {
        return investment_type;
    }

    public void setInvestment_type(String investment_type) {
        this.investment_type = investment_type;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }
}
