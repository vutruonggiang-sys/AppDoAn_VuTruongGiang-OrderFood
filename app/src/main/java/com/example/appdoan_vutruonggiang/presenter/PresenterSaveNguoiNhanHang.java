package com.example.appdoan_vutruonggiang.presenter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.ThongTinNguoiOrder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class PresenterSaveNguoiNhanHang {
    public PresenterSaveNguoiNhanHang (){}
    public void saveNguoiNhanHang(Context context, String id, long giaKM, String hoTen, String sdt){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.border);
        TextInputLayout tilPhoneNumber=dialog.findViewById(R.id.tilPhoneNumber);
        TextInputLayout tilName=dialog.findViewById(R.id.tilName);
        TextInputLayout tilCity=dialog.findViewById(R.id.tilCity);
        TextInputLayout tilDistrict=dialog.findViewById(R.id.tilDistrict);
        TextInputLayout tilAddress=dialog.findViewById(R.id.tilAddress);
        TextInputEditText tietPhoneNumber=dialog.findViewById(R.id.tietPhoneNumber);
        TextInputEditText tietName=dialog.findViewById(R.id.tietName);
        TextInputEditText tietCity=dialog.findViewById(R.id.tietCity);
        TextInputEditText tietDistrict=dialog.findViewById(R.id.tietDistrict);
        TextInputEditText tietAddress=dialog.findViewById(R.id.tietAddress);
        Button But_OK=dialog.findViewById(R.id.But_OK_SaveNguoiNhan);
        tietName.setText(hoTen);
        tietPhoneNumber.setText(sdt);
        List<String> cities= Arrays.asList("Hà Nội","Ninh Bình","Hà Nam");
        List<String> districts1=Arrays.asList("Bắc Từ Liêm","Nam Từ Liêm","Cầu Giấy");
        List<String> districts2=Arrays.asList("Hoa Lư","TP Ninh Bình");
        List<String> districts3=Arrays.asList("TP Phủ Lý","Duy Tiên");
        But_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference().child("thong_tin_nguoi_nhan_hang").child(sdt);
                ThongTinNguoiOrder thongTinNguoiOrder=new ThongTinNguoiOrder(id,giaKM,tietName.getText().toString()
                        ,tietPhoneNumber.getText().toString(),address.getText().toString());
                databaseReference.child(id).setValue(thongTinNguoiOrder);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
