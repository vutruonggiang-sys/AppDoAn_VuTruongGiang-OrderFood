package com.example.appdoan_vutruonggiang.modle;

public class User {
    String name,pass,sdt;

    public User() {}

    public User(String name, String pass, String sdt) {
        this.name = name;
        this.pass = pass;
        this.sdt = sdt;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
