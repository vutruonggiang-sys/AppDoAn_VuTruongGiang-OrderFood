package com.example.appdoan_vutruonggiang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.LogoPartner;

import java.util.List;

public class AdapterLogoPartner extends RecyclerView.Adapter<AdapterLogoPartner.ViewHolder> {
    List<LogoPartner> logoPartners;

    public AdapterLogoPartner(List<LogoPartner> logoPartners) {
        this.logoPartners = logoPartners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logo_partner,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LogoPartner logoPartner=logoPartners.get(position);
        holder.imgLogo.setImageResource(logoPartner.getUrl());
        holder.tvNameLogo.setText(logoPartner.getName());
    }

    @Override
    public int getItemCount() {
        if (logoPartners == null)
            return 0;
        else
            return logoPartners.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView tvNameLogo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            tvNameLogo = itemView.findViewById(R.id.tvNameLogo);
        }
    }
}
