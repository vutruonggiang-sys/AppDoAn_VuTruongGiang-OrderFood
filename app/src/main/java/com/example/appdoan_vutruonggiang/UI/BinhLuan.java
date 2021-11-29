package com.example.appdoan_vutruonggiang.UI;

public class BinhLuan {
    String name,noidung,sdt;
    long d;

    public BinhLuan(String name, String noidung, String sdt,long d) {
        this.name = name;
        this.noidung = noidung;
        this.sdt=sdt;
        this.d=d;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public long getD() {
        return d;
    }

    public void setD(long d) {
        this.d = d;
    }
}
