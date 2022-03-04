package com.example.appdoan_vutruonggiang.modle;

public class BinhLuan {
    String name, noidung, email;
    long d;

    public BinhLuan(String name, String noidung, String email, long d) {
        this.name = name;
        this.noidung = noidung;
        this.email = email;
        this.d = d;
    }

    public BinhLuan() {
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getD() {
        return d;
    }

    public void setD(long d) {
        this.d = d;
    }
}
