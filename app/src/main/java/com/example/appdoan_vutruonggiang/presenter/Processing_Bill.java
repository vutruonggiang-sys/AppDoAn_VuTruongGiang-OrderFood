package com.example.appdoan_vutruonggiang.presenter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.UI.Bill;
import com.example.appdoan_vutruonggiang.UI.Food_Order;
import com.example.appdoan_vutruonggiang.UI.GiamGia;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Processing_Bill {
    public static void getBill(Context context, String name, String sdt, String address, String time, List<Food_Order> list,long giaGiam){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_bill);
        dialog.setCancelable(true);
        TextView tvTime=dialog.findViewById(R.id.tv_time_bill);
        EditText edName=dialog.findViewById(R.id.edNameUser);
        EditText edSdt=dialog.findViewById(R.id.edSDTUser);
        EditText edAddress=dialog.findViewById(R.id.edAddressUser);
        Button but_cancel=dialog.findViewById(R.id.but_bill_Cancel);
        Button but_ok=dialog.findViewById(R.id.but_bill_OK);
        TextView sumFood =dialog.findViewById(R.id.tonghopmon);
        TextView tong_tien=dialog.findViewById(R.id.tv_tongtienhoadon);
        TextView tv_giamGia=dialog.findViewById(R.id.tv_giamGia);
        TextView tv_tienPhaiTra=dialog.findViewById(R.id.tv_tongtien);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference= firebaseDatabase.getReference().child("bill").child(sdt);

        tvTime.setText(time);
        edName.setText(name);
        edSdt.setText(sdt);
        edAddress.setText(address);



        tv_giamGia.setText(giaGiam +" VND");
        but_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        String tonghop="";
        long tong=0;
        for(int i=0;i<list.size();i++){
            tonghop=tonghop+list.get(i).getName()+"(x"+list.get(i).getAmount()+")  ";
            tong=tong+list.get(i).getAmount()*list.get(i).getPrice();
        }
        long t=tong+20000- giaGiam;
        tv_tienPhaiTra.setText(t+" VND");
        sumFood.setText(tonghop);
        tong_tien.setText(tong+" VND");
        long finalTong = tong;
        but_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bill bill=new Bill(tvTime.getText().toString(),edName.getText().toString(),edSdt.getText().toString(),edAddress.getText().toString(),sumFood.getText().toString(), finalTong, giaGiam);
                databaseReference.child(tvTime.getText().toString()).setValue(bill);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
