package com.example.appdoan_vutruonggiang.UI;

public class GiamGia {
    String id,name;
    long giamGia;
    public GiamGia(){}

    public GiamGia(String id, String name, long giamGia) {
        this.id = id;
        this.name = name;
        this.giamGia = giamGia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(long giamGia) {
        this.giamGia = giamGia;
    }
}
