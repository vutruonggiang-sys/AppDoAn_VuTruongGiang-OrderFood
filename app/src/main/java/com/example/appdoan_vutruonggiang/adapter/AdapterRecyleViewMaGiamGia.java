package com.example.appdoan_vutruonggiang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.GiamGia;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class AdapterRecyleViewMaGiamGia extends RecyclerView.Adapter<AdapterRecyleViewMaGiamGia.ViewHoder> {
    List<GiamGia> giamGiaList;
    String email;

    public AdapterRecyleViewMaGiamGia(List<GiamGia> giamGiaList,String email) {
        this.giamGiaList = giamGiaList;
        this.email=email;
    }

    @NonNull
    @Override
    public AdapterRecyleViewMaGiamGia.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_magiamgia,parent,false);
        ViewHoder viewHoder=new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyleViewMaGiamGia.ViewHoder holder, int position) {
        GiamGia giamGia=giamGiaList.get(position);
        holder.tv_ma.setText("Mã giảm giá là: "+giamGia.getId());
        holder.tv_ten.setText("Tên mã giảm giá là: "+giamGia.getName());
        holder.tv_giaTri.setText("Giá Trị mã giảm giá là: "+giamGia.getGiamGia());
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("luu_ma_van_chuyen").child(email);
        GiamGia giamGia1=new GiamGia("abc","haha",0);
        databaseReference.child("haha").setValue(giamGia1);
        databaseReference.child("haha1").setValue(giamGia1);
        holder.tv_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> valueUpdate=new HashMap<>();
                valueUpdate.put("giamGia",giamGia.getGiamGia());
                valueUpdate.put("id",giamGia.getId());
                valueUpdate.put("name",giamGia.getName());
                HashMap<String,Object> keyUpdate=new HashMap<>();
                keyUpdate.put("haha",valueUpdate);
                databaseReference.updateChildren(keyUpdate);
            }
        });
        holder.tv_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> valueUpdate=new HashMap<>();
                valueUpdate.put("giamGia",giamGia.getGiamGia());
                valueUpdate.put("id",giamGia.getId());
                valueUpdate.put("name",giamGia.getName());
                HashMap<String,Object> keyUpdate=new HashMap<>();
                keyUpdate.put("haha",valueUpdate);
                databaseReference.updateChildren(keyUpdate);
            }
        });
        holder.tv_giaTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> valueUpdate=new HashMap<>();
                valueUpdate.put("giamGia",giamGia.getGiamGia());
                valueUpdate.put("id",giamGia.getId());
                valueUpdate.put("name",giamGia.getName());
                HashMap<String,Object> keyUpdate=new HashMap<>();
                keyUpdate.put("haha",valueUpdate);
                databaseReference.updateChildren(keyUpdate);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(giamGiaList==null)
            return 0;
        else
            return giamGiaList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tv_ma,tv_ten,tv_giaTri;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tv_ma=itemView.findViewById(R.id.tvMa);
            tv_ten=itemView.findViewById(R.id.tvTen);
            tv_giaTri=itemView.findViewById(R.id.tv_GiaTri);
        }
    }
}
