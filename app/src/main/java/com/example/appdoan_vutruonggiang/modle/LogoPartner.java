package com.example.appdoan_vutruonggiang.modle;

public class LogoPartner {
    String name;
    int url;

    public LogoPartner(String name, int url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public LogoPartner() {
    }
}
