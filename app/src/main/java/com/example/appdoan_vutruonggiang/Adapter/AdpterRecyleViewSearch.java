package com.example.appdoan_vutruonggiang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.example.appdoan_vutruonggiang.inteface.IItemFood;
import com.example.appdoan_vutruonggiang.R;

import java.util.ArrayList;
import java.util.List;

public class AdpterRecyleViewSearch extends RecyclerView.Adapter<AdpterRecyleViewSearch.ViewHoder> implements Filterable {
    List<Food> foodList,foodListSearch;
    Context context;
    IItemFood iItemFood;
    public AdpterRecyleViewSearch(List<Food> foodList,Context context,IItemFood iItemFood) {
        this.foodList = foodList;
        this.foodListSearch=foodList;
        this.context=context;
        this.iItemFood=iItemFood;
    }

    @NonNull
    @Override
    public AdpterRecyleViewSearch.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_search,parent,false);
        ViewHoder viewHoder=new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterRecyleViewSearch.ViewHoder holder, int position) {
        Food food=foodList.get(position);
        if(food==null){
            return;
        }
        holder.tvName.setText(food.getName());
        holder.tvReview.setText(food.getReview()+"");
        holder.tvPrice.setText(food.getPrice()+"VND");
        Glide.with(context).load(food.getUrl()).into(holder.anh);
        holder.anh.setOnClickListener(new View.OnClickListener() {
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
        if(foodList!=null) {
            return foodList.size();
        }else{
            return 0;
        }
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView tvName,tvPrice,tvReview;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            anh=itemView.findViewById(R.id.imAnhSearch);
            tvName=itemView.findViewById(R.id.tvNameSearch);
            tvPrice=itemView.findViewById(R.id.tvPriceSearch);
            tvReview=itemView.findViewById(R.id.tvReviewSearch);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchStr=constraint.toString();
                if(searchStr.isEmpty()){
                    foodList=foodListSearch;
                }else{
                    List<Food> listDisplay=new ArrayList<>();
                    for (Food food: foodListSearch) {
                        if(food.getName().toUpperCase().contains(searchStr.toUpperCase().trim()))
                            listDisplay.add(food);
                    }
                    foodList=listDisplay;
                }
                FilterResults results=new FilterResults();
                results.values=foodList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                foodList=(List<Food>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
