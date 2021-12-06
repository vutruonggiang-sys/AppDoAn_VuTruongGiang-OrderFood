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

public class Processing_DangKy {
    public static void dangKy(Context context, List<User> userList){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_register);
        EditText edName=dialog.findViewById(R.id.edNameRegister);
        EditText edSdt=dialog.findViewById(R.id.edSdtRegister);
        EditText edAddress=dialog.findViewById(R.id.edAddressRegister);
        EditText edEmail=dialog.findViewById(R.id.edEmailRegister);
        EditText edPass=dialog.findViewById(R.id.edPassRegister);
        EditText edConform=dialog.findViewById(R.id.edConformPassRegister);
        Button butCancel=dialog.findViewById(R.id.but_Register_Cancel);
        Button butOK=dialog.findViewById(R.id.but_Register_OK);

        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        butOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(User user:userList){
                    if(user.getSdt().toString().equals(edSdt.getText().toString())){
                        Toast.makeText(context,"Số điện thoại đã tồn tại",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
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
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
