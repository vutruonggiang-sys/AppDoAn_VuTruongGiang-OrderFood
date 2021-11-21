package com.example.appdoan_vutruonggiang.UI;

public class Bill {
    String time,name,sdt,address,sumfood;
    long tongTien,giamGia;
    public Bill(){}

    public Bill(String time, String name, String sdt, String address, String sumfood,long tongTien,long giamGia) {
        this.time = time;
        this.name = name;
        this.sdt = sdt;
        this.address = address;
        this.sumfood = sumfood;
        this.tongTien=tongTien;
        this.giamGia=giamGia;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSumfood() {
        return sumfood;
    }

    public void setSumfood(String sumfood) {
        this.sumfood = sumfood;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }

    public long getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(long giamGia) {
        this.giamGia = giamGia;
    }
}
