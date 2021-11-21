package com.example.appdoan_vutruonggiang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdoan_vutruonggiang.UI.Bank;
import com.example.appdoan_vutruonggiang.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterRecyleViewBank extends RecyclerView.Adapter<AdapterRecyleViewBank.ViewHoder> {
    List<Bank> bankList;
    Context context;
    String sdt;
    public AdapterRecyleViewBank(List<Bank> bankList,Context context,String sdt) {
        this.bankList = bankList;
        this.context=context;
        this.sdt=sdt;
    }

    @NonNull
    @Override
    public AdapterRecyleViewBank.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_bank,parent,false);
        ViewHoder viewHoder=new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyleViewBank.ViewHoder holder, int position) {
        Bank bank=bankList.get(position);
        if(bankList==null) return;
        holder.tvName.setText(bank.getName());
        Glide.with(context).load(bank.getUrlAnh()).into(holder.anh);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference().child("bank").child(sdt);
                databaseReference.setValue(bank.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(bankList==null)
        return 0;
        else
            return bankList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView tvName;
        LinearLayout linearLayout;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            anh=itemView.findViewById(R.id.item_ImageBank);
            tvName=itemView.findViewById(R.id.tv_item_NameBank);
            linearLayout=itemView.findViewById(R.id.item_bank);
        }
    }
}
