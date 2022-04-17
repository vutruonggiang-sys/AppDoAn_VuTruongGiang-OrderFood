package com.example.appdoan_vutruonggiang.modle;

public class TotalRevenueRes {
    public String id;
    public long total;

    public TotalRevenueRes(String id, long total) {
        this.id = id;
        this.total = total;
    }

    public TotalRevenueRes() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public TotalRevenueRes(String id) {
        this.id = id;
    }
}
