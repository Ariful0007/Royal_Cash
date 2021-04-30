package com.example.royalcash.model;

public class Person_to_person {
    String main_email,sub_email;

    public Person_to_person() {
    }

    public Person_to_person(String main_email, String sub_email) {
        this.main_email = main_email;
        this.sub_email = sub_email;
    }

    public String getMain_email() {
        return main_email;
    }

    public void setMain_email(String main_email) {
        this.main_email = main_email;
    }

    public String getSub_email() {
        return sub_email;
    }

    public void setSub_email(String sub_email) {
        this.sub_email = sub_email;
    }
}
