package com.example.appdoan_vutruonggiang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.NhaHang;

import java.util.List;

public class AdapterRetaurant extends RecyclerView.Adapter<AdapterRetaurant.ViewHolder> {

    List<NhaHang> nhaHangList;
    Context context;

    public AdapterRetaurant(List<NhaHang> nhaHangList, Context context) {
        this.nhaHangList = nhaHangList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRetaurant.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reataurant,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRetaurant.ViewHolder holder, int position) {
        NhaHang nhaHang=nhaHangList.get(position);
        holder.tvNameRestaurant.setText(nhaHang.getName());
        Glide.with(context).load(nhaHang.getUrl()).into(holder.imgRestaurant);
    }

    @Override
    public int getItemCount() {
        if (nhaHangList == null)
            return 0;
        else {
            return nhaHangList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRestaurant;
        TextView tvNameRestaurant;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRestaurant = itemView.findViewById(R.id.imgRestaurant);
            tvNameRestaurant = itemView.findViewById(R.id.tvNameRestaurant);
        }
    }
}
