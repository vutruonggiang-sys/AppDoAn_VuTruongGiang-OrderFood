package com.example.appdoan_vutruonggiang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.Food_Order;
import com.example.appdoan_vutruonggiang.sqlite.SqliteHelper;

import java.util.List;

public class AdapterRecyleViewGiaoHangCart extends RecyclerView.Adapter<AdapterRecyleViewGiaoHangCart.ViewHoder> {
    List<Food_Order> food_orderList;
    Context context;
    SqliteHelper sqliteHelper;
    public AdapterRecyleViewGiaoHangCart(List<Food_Order> food_orderList,Context context) {
        this.food_orderList = food_orderList;
        this.context=context;
    }

    @NonNull
    @Override
    public AdapterRecyleViewGiaoHangCart.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_giohang_chuagiao,parent,false);
        ViewHoder viewHoder=new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyleViewGiaoHangCart.ViewHoder holder, int position) {
        Food_Order food_order=food_orderList.get(position);
        Glide.with(context).load(food_order.getUrl()).into(holder.image);
        holder.tv_price.setText(food_order.getPrice()+"");
        holder.tv_number.setText(food_order.getAmount()+"");
        holder.tv_id_nh.setText(food_order.getIdNhaHang());
        holder.tv_Foodname.setText(food_order.getName());
        holder.checkButton.setChecked(false);
        sqliteHelper=new SqliteHelper(context);
        holder.checkButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sqliteHelper.onAddGioHang(food_order);
                }else{
                    if(!sqliteHelper.checkGioHang(food_order.getId())){
                        sqliteHelper.onDeleteGioHang(food_order.getId());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(food_orderList==null)
        return 0;
        else
            return food_orderList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tv_Foodname,tv_id_nh,tv_number,tv_price;
        CheckBox checkButton;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image_giohang_chuagiao);
            tv_Foodname=itemView.findViewById(R.id.tv_name_giohang_chuagiao);
            tv_id_nh= itemView.findViewById(R.id.tv_name_nhaHang);
            tv_number=itemView.findViewById(R.id.tv_number_giohang_chuagiao);
            tv_price=itemView.findViewById(R.id.tv_price_giohang_chuagiao);
            checkButton=itemView.findViewById(R.id.butRadio_chon);
        }
    }
}
