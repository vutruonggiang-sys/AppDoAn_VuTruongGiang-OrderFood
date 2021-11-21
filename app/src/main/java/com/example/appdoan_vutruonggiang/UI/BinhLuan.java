package com.example.appdoan_vutruonggiang.UI;

public class BinhLuan {
    String name,noidung;

    public BinhLuan(String name, String noidung) {
        this.name = name;
        this.noidung = noidung;
    }
    public BinhLuan(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
