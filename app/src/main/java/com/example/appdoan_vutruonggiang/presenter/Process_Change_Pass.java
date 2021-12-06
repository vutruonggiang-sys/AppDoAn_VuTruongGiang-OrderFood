package com.example.appdoan_vutruonggiang.presenter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Process_Change_Pass {
    public static void getChange(Context context,String sdt, String hoTen, String diachi, String email,String pass){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_changepass);
        EditText oldPass=dialog.findViewById(R.id.ed_old_pass);
        EditText newPass=dialog.findViewById(R.id.ed_new_pass);
        EditText conformPass=dialog.findViewById(R.id.ed_conform_pass);
        Button but_Conform=dialog.findViewById(R.id.but_conform);
        but_Conform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pass.equals(oldPass.getText().toString().trim())){
                    Toast.makeText(context,"Bạn nhập sai mật khẩu",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(oldPass.getText().toString().trim().equals("")||newPass.getText().toString().trim().equals("")||conformPass.getText().toString().trim().equals("")){
                    Toast.makeText(context,"Không được để trống",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(oldPass.getText().toString().trim().equals(newPass.getText().toString().trim())){
                    Toast.makeText(context,"Bạn đã nhập mật khẩu cũ",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newPass.getText().toString().trim().equals(conformPass.getText().toString().trim())){
                    Toast.makeText(context,"Bạn đã thay đổi mật khẩu",Toast.LENGTH_SHORT).show();
                    User user=new User(diachi,email,hoTen,newPass.getText().toString(),sdt);
                    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference=firebaseDatabase.getReference().child("user");
                    databaseReference.child(sdt).setValue(user);
                    dialog.dismiss();
                }else{
                    Toast.makeText(context,"xác nhận sai mật khẩu",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        dialog.show();
    }
}
