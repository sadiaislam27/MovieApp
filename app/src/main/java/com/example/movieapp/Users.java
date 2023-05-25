package com.example.movieapp;

public class Users {
    String name,phone,age;

    public Users() {
    }

    public Users(String name, String phone, String age) {
        this.name = name;
        this.phone = phone;
        this.age = age;

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
