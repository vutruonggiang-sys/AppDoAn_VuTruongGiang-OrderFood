package com.example.appdoan_vutruonggiang.presenter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.UI.ThongTinNguoiOrder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Presenter_SaveNguoiNhanHang {
    public static void saveNguoiNhanHang(Context context, String id, long giaKM, String hoTen, String sdt, String diaChi){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.border);
        EditText name=dialog.findViewById(R.id.edName);
        EditText phone=dialog.findViewById(R.id.edPhonenumber);
        EditText address=dialog.findViewById(R.id.edAddress);
        Button But_OK=dialog.findViewById(R.id.But_OK_SaveNguoiNhan);
        name.setText(hoTen);
        phone.setText(sdt);
        address.setText(diaChi);
        But_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference().child("thong_tin_nguoi_nhan_hang").child(sdt);
                ThongTinNguoiOrder thongTinNguoiOrder=new ThongTinNguoiOrder(id,giaKM,name.getText().toString()
                        ,phone.getText().toString(),address.getText().toString());
                databaseReference.child(id).setValue(thongTinNguoiOrder);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
