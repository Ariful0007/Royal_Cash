package com.example.royalcash.model;

public class Balance_Model {
    String user_id;

    String finalUserEmail;
    String finalUserName;

    String finalUserPhone;
    String toString;
    String coin;

    public Balance_Model() {
    }

    public Balance_Model(String user_id, String finalUserEmail,
                         String finalUserName, String finalUserPhone, String toString, String coin) {
        this.user_id = user_id;
        this.finalUserEmail = finalUserEmail;
        this.finalUserName = finalUserName;
        this.finalUserPhone = finalUserPhone;
        this.toString = toString;
        this.coin = coin;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFinalUserEmail() {
        return finalUserEmail;
    }

    public void setFinalUserEmail(String finalUserEmail) {
        this.finalUserEmail = finalUserEmail;
    }

    public String getFinalUserName() {
        return finalUserName;
    }

    public void setFinalUserName(String finalUserName) {
        this.finalUserName = finalUserName;
    }

    public String getFinalUserPhone() {
        return finalUserPhone;
    }

    public void setFinalUserPhone(String finalUserPhone) {
        this.finalUserPhone = finalUserPhone;
    }

    public String getToString() {
        return toString;
    }

    public void setToString(String toString) {
        this.toString = toString;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }
}
