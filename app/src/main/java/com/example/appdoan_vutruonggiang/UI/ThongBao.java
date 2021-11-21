package com.example.appdoan_vutruonggiang.UI;

public class ThongBao {
    String id,thongBao;
    public ThongBao(){

    }

    public ThongBao(String id,String thongBao) {
        this.id=id;
        this.thongBao = thongBao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThongBao() {
        return thongBao;
    }

    public void setThongBao(String thongBao) {
        this.thongBao = thongBao;
    }
}
