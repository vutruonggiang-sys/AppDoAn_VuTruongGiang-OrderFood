package com.example.appdoan_vutruonggiang.modle;

public class User {
    String name,pass,email,url;

    public User() {}

    public User(String name, String pass, String email,String url) {
        this.name = name;
        this.pass = pass;
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
