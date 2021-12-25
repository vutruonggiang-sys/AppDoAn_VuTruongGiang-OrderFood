package com.example.appdoan_vutruonggiang.presenter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.GiamGia;
import com.example.appdoan_vutruonggiang.modle.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProcessingDangKy {
    public static void dangKy(Context context,EditText edAddress,EditText edName,EditText edConform,EditText edPass,EditText edEmail,EditText edSdt){
                if(edAddress.getText().toString().trim().equals("")||edConform.getText().toString().trim().equals("")||edEmail.getText().toString().trim().equals("")
                        ||edName.getText().toString().trim().equals("")||edPass.getText().toString().trim().equals("")||edSdt.getText().toString().trim().equals(""))
                {
                    Toast.makeText(context,"Không được để trống",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!edConform.getText().toString().equals(edPass.getText().toString()))
                {
                    Toast.makeText(context,"Mât khẩu không đúng",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!edEmail.getText().toString().contains("@gmail.com")){
                    Toast.makeText(context,"Email không đúng",Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference().child("user");

                User user=new User(edAddress.getText().toString(),edEmail.getText().toString(),edName.getText().toString(),edPass.getText().toString(),edSdt.getText().toString());
                databaseReference.child(edSdt.getText().toString()).setValue(user);

                Toast.makeText(context,"Bạn đã đăng ký thành công",Toast.LENGTH_SHORT).show();

                DatabaseReference databaseReference1=firebaseDatabase.getReference().child("bank");
                databaseReference1.child(edSdt.getText().toString()).setValue("Bạn chưa chọn dịch vụ");

                DatabaseReference databaseReference2=firebaseDatabase.getReference().child("ma_giam_gia");
                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                        for(DataSnapshot data:dataSnapshotIterable){
                            GiamGia giamGia=data.getValue(GiamGia.class);
                            DatabaseReference databaseReference3=firebaseDatabase.getReference().child("giam_gia");
                            databaseReference3.child(edSdt.getText().toString()).child(giamGia.getId()).setValue(giamGia);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
}
