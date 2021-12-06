package com.example.appdoan_vutruonggiang.presenter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.ThongBao;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Process_GiaoHang {
    public static void thongBaoNhanHangThanhCong(Context context,String sdt){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_nhanhang);
        TextView tv_ngaygio=dialog.findViewById(R.id.tv_ngaygio_thanhtoan);
        Button but_OK=dialog.findViewById(R.id.but_ok);
        but_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Calendar calendar=Calendar.getInstance();
        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        tv_ngaygio.setText("Ngày "+dateFormat.format(calendar.getTime()) +"đơn hàng của bạn đã bắt đầu giao.");

        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("thongbao").child(sdt);
        ThongBao thongBao=new ThongBao(dateFormat.format(calendar.getTime()),tv_ngaygio.getText().toString());
        databaseReference.child(dateFormat.format(calendar.getTime())).setValue(thongBao);
        dialog.show();
    }
}
