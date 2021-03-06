package com.example.appdoan_vutruonggiang.adapter;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterRecyleViewSearch extends RecyclerView.Adapter<AdapterRecyleViewSearch.ViewHoder> implements Filterable {
    List<Food> foodList, foodListSearch;
    Context context;
    IItemFood iItemFood;
    TextView tvNoFindResult;
    ImageView imgNoFindResult;

    public AdapterRecyleViewSearch(List<Food> foodList, Context context, IItemFood iItemFood, TextView tvNoFindResult, ImageView imgNoFindResult) {
        this.foodList = foodList;
        this.foodListSearch = foodList;
        this.context = context;
        this.iItemFood = iItemFood;
        this.tvNoFindResult = tvNoFindResult;
        this.imgNoFindResult = imgNoFindResult;
    }

    @NonNull
    @Override
    public AdapterRecyleViewSearch.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_search, parent, false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyleViewSearch.ViewHoder holder, int position) {
        Food food = foodList.get(position);
        if (food == null) {
            return;
        }
        holder.tvName.setText(food.getName());
        holder.tvReview.setText(food.getReview() + "");
        holder.tvPrice.setText(customFormat("###,###",food.getPrice()));
        Glide.with(context).load(food.getUrl()).into(holder.anh);
        holder.anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iItemFood.onClickItemFood(food);
            }
        });
    }

    public void release() {
        context = null;
    }

    @Override
    public int getItemCount() {
        if (foodList != null) {
            return foodList.size();
        } else {
            return 0;
        }
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView tvName, tvPrice, tvReview;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            anh = itemView.findViewById(R.id.imAnhSearch);
            tvName = itemView.findViewById(R.id.tvNameSearch);
            tvPrice = itemView.findViewById(R.id.tvPriceSearch);
            tvReview = itemView.findViewById(R.id.tvReviewSearch);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchStr = constraint.toString();
                if (searchStr.isEmpty()) {
                } else {
                    List<Food> listDisplay = new ArrayList<>();
                    for (Food food : foodList) {
                        if (food.getName().toUpperCase().contains(searchStr.toUpperCase().trim()))
                            listDisplay.add(food);
                    }
                    foodList = listDisplay;
                    if ((listDisplay.size() == 0)) {
                        tvNoFindResult.setVisibility(View.VISIBLE);
                        imgNoFindResult.setVisibility(View.VISIBLE);
                    } else {
                        tvNoFindResult.setVisibility(View.GONE);
                        imgNoFindResult.setVisibility(View.GONE);
                    }
                }
                FilterResults results = new FilterResults();
                results.values = foodList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                foodList = (List<Food>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public String customFormat(String pattern, float value ) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        return  output;
    }
}
