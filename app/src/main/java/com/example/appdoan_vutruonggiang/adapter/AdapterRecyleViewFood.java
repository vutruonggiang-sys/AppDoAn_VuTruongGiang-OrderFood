package com.example.appdoan_vutruonggiang.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.example.appdoan_vutruonggiang.inteface.IItemFood;
import com.example.appdoan_vutruonggiang.presenter.ProcessFood;
import com.example.appdoan_vutruonggiang.R;


import java.text.DecimalFormat;
import java.util.List;

public class AdapterRecyleViewFood extends RecyclerView.Adapter<AdapterRecyleViewFood.ViewHoder> {
    List<Food> foodList;
    IItemFood iItemFood;
    Context context;
    String email;
    ProcessFood processFood;
    public AdapterRecyleViewFood(List<Food> foodList,Context context,IItemFood iItemFood,String email) {
        this.context=context;
        this.foodList = foodList;
        this.iItemFood=iItemFood;
        this.email=email;
    }
    @NonNull
    @Override
    public AdapterRecyleViewFood.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_food,parent,false);
        ViewHoder viewHoder=new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyleViewFood.ViewHoder holder, int position) {
        Food food=foodList.get(position);
        Glide.with(context).load(food.getUrl()).into(holder.image);
        holder.tvName.setText(food.getName());
        holder.tvPrice.setText(customFormat("###,###",food.getPrice())+" VND");
        holder.tvReview.setText(food.getReview()+"");
        holder.order_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processFood=new ProcessFood();
                processFood.getChoose(context,food,email);
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iItemFood.onClickItemFood(food);
            }
        });
    }
    public void release(){
        context=null;
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }


    public class ViewHoder extends RecyclerView.ViewHolder {
        ImageView image;
        AppCompatButton order_image;
        TextView tvName,tvPrice,tvReview;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.imAnh);
            tvName=itemView.findViewById(R.id.tvName);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvReview=itemView.findViewById(R.id.tvReview);
            order_image=itemView.findViewById(R.id.but_add_order);
        }
    }

    public String customFormat(String pattern, float value ) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        return  output;
    }

}
