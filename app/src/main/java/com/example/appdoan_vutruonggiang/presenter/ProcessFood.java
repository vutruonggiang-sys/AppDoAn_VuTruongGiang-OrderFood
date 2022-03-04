package com.example.appdoan_vutruonggiang.presenter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.Food_Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProcessFood {
    public ProcessFood() {
    }

    public List<Food> doAnNhanh(List<Food> foodList){
        List<Food> foodFast=new ArrayList<>();
        for(int i=0;i<foodList.size();i++){
            if(foodList.get(i).getType().equals("doannhanh")){
                foodFast.add(foodList.get(i));
            }
        }
        return foodFast;
    }
    public List<Food> doUong(List<Food> foodList){
        List<Food> foodDrink=new ArrayList<>();
        for(int i=0;i<foodList.size();i++){
            if(foodList.get(i).getType().equals("douong")){
                foodDrink.add(foodList.get(i));
            }
        }
        return foodDrink;
    }
    public List<Food> com(List<Food> foodList){
        List<Food> foodPrice=new ArrayList<>();
        for(int i=0;i<foodList.size();i++){
            if(foodList.get(i).getType().equals("com")){
                foodPrice.add(foodList.get(i));
            }
        }
        return foodPrice;
    }
    public List<Food> sapTheoDanhGia(List<Food> foodList){
        Comparator<Food> c=new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                return Float.compare(o2.getReview(),o1.getReview());
            }
        };
        Collections.sort(foodList,c);
        return foodList;
    }
    public void getChoose(Context context,Food food,String email){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_process_number_order);
        dialog.setCancelable(true);
        TextView title=dialog.findViewById(R.id.tv_name_item_order);
        TextView cong=dialog.findViewById(R.id.but_tang);
        TextView tru=dialog.findViewById(R.id.but_giam);
        TextView tvHienThiNumber=dialog.findViewById(R.id.tv_display_amount);
        TextView tvAmount=dialog.findViewById(R.id.tv_amount);
        ImageView imageView=dialog.findViewById(R.id.image_food_datHang);
        Button cancel=dialog.findViewById(R.id.but_Cancel);
        Button order=dialog.findViewById(R.id.but_order);
        title.setText("Bạn đang chọn món "+food.getName());
        tru.setEnabled(false);
        Glide.with(context).load(food.getUrl()).into(imageView);
        Food_Order food_order=new Food_Order(1,food.id,food.getName(),(long) food.getPrice(),food.getUrl(),food.getIdNhaHang());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tang=Integer.parseInt(tvAmount.getText().toString());
                tang++;
                tvAmount.setText(tang+"");
                tvHienThiNumber.setText("X"+tang);
                tru.setEnabled(true);
                food_order.setAmount(tang);
            }
        });
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int giam=Integer.parseInt(tvAmount.getText().toString());
                if(giam<=1){
                    giam=1;
                    tru.setEnabled(false);
                    tvAmount.setText(giam+"");
                    tvHienThiNumber.setText("X"+giam);
                }else{
                    tru.setEnabled(true);
                    giam--;
                    tvAmount.setText(giam+"");
                    tvHienThiNumber.setText("X"+giam);
                }
                food_order.setAmount(giam);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Bạn vừa thêm vào giỏ hàng món "+food.getName()+" với sô lượng là "+food_order.getAmount(),Toast.LENGTH_SHORT).show();
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference();
                databaseReference.child("food_giohang").child(email).child(food_order.getId()).setValue(food_order);
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    public void khuyenMai(ViewFlipper viewFlipper){
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
    }

}
