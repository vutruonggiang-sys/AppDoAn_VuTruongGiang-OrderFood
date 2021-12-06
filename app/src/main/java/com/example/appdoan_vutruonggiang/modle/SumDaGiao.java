package com.example.appdoan_vutruonggiang.modle;

import java.util.List;

public class SumDaGiao {
    List<Food_Order> food_orderList;
    public SumDaGiao(){}

    public SumDaGiao(List<Food_Order> food_orderList) {
        this.food_orderList = food_orderList;
    }

    public List<Food_Order> getFood_orderList() {
        return food_orderList;
    }

    public void setFood_orderList(List<Food_Order> food_orderList) {
        this.food_orderList = food_orderList;
    }
}
