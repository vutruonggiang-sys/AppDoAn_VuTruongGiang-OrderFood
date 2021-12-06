package com.example.appdoan_vutruonggiang.modle;

public class Bank {
    String name,urlAnh;
    public Bank(){

    }

    public Bank(String name, String urlAnh) {
        this.name = name;
        this.urlAnh = urlAnh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlAnh() {
        return urlAnh;
    }

    public void setUrlAnh(String urlAnh) {
        this.urlAnh = urlAnh;
    }
}
