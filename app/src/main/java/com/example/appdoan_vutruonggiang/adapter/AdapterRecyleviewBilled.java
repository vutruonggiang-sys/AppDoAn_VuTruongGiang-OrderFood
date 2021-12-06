package com.example.appdoan_vutruonggiang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.Bill;
import com.example.appdoan_vutruonggiang.presenter.Process_HoaDonGiaoDich;

import java.util.List;

public class AdapterRecyleviewBilled extends RecyclerView.Adapter<AdapterRecyleviewBilled.ViewHoder> {
    List<Bill> billList;
    Context context;
    public AdapterRecyleviewBilled(List<Bill> billList,Context context) {
        this.billList = billList;
        this.context=context;
    }

    @NonNull
    @Override
    public AdapterRecyleviewBilled.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_billed,parent,false);
        ViewHoder viewHoder=new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyleviewBilled.ViewHoder holder, int position) {
        Bill bill=billList.get(position);
        holder.tvtime.setText(bill.getTime());
        holder.tvtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Process_HoaDonGiaoDich.getHoaDon(context,bill.getName(),bill.getSdt(),bill.getAddress(),bill.getTime(),bill.getSumfood(),bill.getTongTien(),bill.getGiamGia());
            }
        });
    }

    public void release(){
        context=null;
    }

    @Override
    public int getItemCount() {
        if(billList==null)
            return 0;
        else
            return billList.size();

    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvtime;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvtime=itemView.findViewById(R.id.tv_timeBilled);
        }
    }
}
