package com.example.appdoan_vutruonggiang.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.UI.BinhLuan;
import com.example.appdoan_vutruonggiang.R;

import java.util.List;

public class AdapterRecyleViewBinhLuan extends RecyclerView.Adapter<AdapterRecyleViewBinhLuan.ViewHoder> {
    List<BinhLuan> binhLuanList;

    public AdapterRecyleViewBinhLuan(List<BinhLuan> binhLuanList) {
        this.binhLuanList = binhLuanList;
    }

    @NonNull
    @Override
    public AdapterRecyleViewBinhLuan.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_binhluan,parent,false);
        ViewHoder viewHoder=new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyleViewBinhLuan.ViewHoder holder, int position) {
        BinhLuan binhLuan=binhLuanList.get(position);
        holder.tvName.setText(binhLuan.getName());
        holder.tvNoiDung.setText(binhLuan.getNoidung());
    }

    @Override
    public int getItemCount() {
        if(binhLuanList!=null) {
            return binhLuanList.size();
        }else{
            return 0;
        }
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvName,tvNoiDung;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_Name_binhluan);
            tvNoiDung=itemView.findViewById(R.id.tv_noidung_binhluan);
        }
    }
}
