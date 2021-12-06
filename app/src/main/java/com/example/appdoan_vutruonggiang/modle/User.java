package com.example.appdoan_vutruonggiang.modle;

public class User {
    String address,email,name,pass,sdt;

    public User() {}

    public User(String address, String email, String name, String pass, String sdt) {
        this.address = address;
        this.email = email;
        this.name = name;
        this.pass = pass;
        this.sdt = sdt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
