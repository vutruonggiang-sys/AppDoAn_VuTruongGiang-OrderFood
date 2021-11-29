package com.example.appdoan_vutruonggiang.UI;

public class ThongTinNguoiOrder {
    String id,hoTen,sdt,diaChi;
    long giaKhuyenMai;

    public ThongTinNguoiOrder(){}

    public ThongTinNguoiOrder(String id, long giaKhuyenMai,String hoTen,String sdt,String diaChi) {
        this.id = id;
        this.giaKhuyenMai=giaKhuyenMai;
        this.hoTen=hoTen;
        this.sdt=sdt;
        this.diaChi=diaChi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getGiaKhuyenMai() {
        return giaKhuyenMai;
    }

    public void setGiaKhuyenMai(long giaKhuyenMai) {
        this.giaKhuyenMai = giaKhuyenMai;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
