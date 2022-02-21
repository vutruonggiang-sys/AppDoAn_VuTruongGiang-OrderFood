package com.example.appdoan_vutruonggiang.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.ThongBao;

import java.util.List;

public class AdapterRecyleThongBao extends RecyclerView.Adapter<AdapterRecyleThongBao.ViewHoder> {
    List<ThongBao> thongBaoList;

    public AdapterRecyleThongBao(List<ThongBao> thongBaoList) {
        this.thongBaoList = thongBaoList;
    }

    @NonNull
    @Override
    public AdapterRecyleThongBao.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_thongbao,parent,false);
        ViewHoder viewHoder=new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyleThongBao.ViewHoder holder, int position) {
        ThongBao thongBao=thongBaoList.get(position);
        holder.tvTB.setText(thongBao.getThongBao());

        holder.tvTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    holder.imageView.setVisibility(View.GONE);
                    holder.linearLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(thongBaoList==null)
        return 0;
        else
            return thongBaoList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvTB;
        ImageView imageView;
        LinearLayout linearLayout;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvTB=itemView.findViewById(R.id.tvThongBao);
            imageView=itemView.findViewById(R.id.thongbao);
            linearLayout=itemView.findViewById(R.id.nenthongbao);
        }
    }
}
