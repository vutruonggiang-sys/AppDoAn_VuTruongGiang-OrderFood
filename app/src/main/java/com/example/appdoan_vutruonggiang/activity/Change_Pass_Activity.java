package com.example.appdoan_vutruonggiang.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.UI.User;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Change_Pass_Activity extends Activity {
    ImageView but_back_account;
    EditText ed_old_pass,ed_new_pass,ed_confirm_pass;
    Button but_confirm;
    String sdt="",hoTen="",diaChi="",email="",pass="",pass1="";
    List<Food> foodList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_change__pass_);
        anhXa();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("user");

        Bundle bundle=this.getIntent().getExtras();
        sdt=sdt+bundle.getString("phoneNumber");
        hoTen=hoTen+bundle.getString("hoten");
        diaChi=diaChi+bundle.getString("diachi");
        email=email+bundle.getString("gmail");
        pass=pass+bundle.get("pass");
        foodList=bundle.getParcelableArrayList("list");
        but_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        if(!pass.equals(ed_old_pass.getText().toString().trim())){
            Toast.makeText(Change_Pass_Activity.this,"Bạn nhập sai mật khẩu cũ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(ed_old_pass.getText().toString().trim().equals("")||ed_new_pass.getText().toString().trim().equals("")||ed_confirm_pass.getText().toString().trim().equals("")){
            Toast.makeText(Change_Pass_Activity.this,"Không được để trống",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.toString().trim().equals(ed_new_pass.getText().toString().trim())){
            Toast.makeText(Change_Pass_Activity.this,"Bạn đã nhập mật khẩu cũ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(ed_new_pass.getText().toString().trim().equals(ed_confirm_pass.getText().toString().trim())){
            Toast.makeText(Change_Pass_Activity.this,"Bạn đã thay đổi mật khẩu",Toast.LENGTH_SHORT).show();
            User user=new User(diaChi,email,hoTen,ed_new_pass.getText().toString(),sdt);
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference databaseReference=firebaseDatabase.getReference().child("user");
            databaseReference.child(sdt).setValue(user);
        }else{
            Toast.makeText(Change_Pass_Activity.this,"xác nhận sai mật khẩu",Toast.LENGTH_SHORT).show();
            return;
        }
            }
        });
        but_back_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), AccountActivity.class);
                ArrayList<Food> listSearch= (ArrayList<Food>) foodList;
                Bundle bundle1=new Bundle();
                bundle1.putString("phoneNumber",sdt);
                bundle1.putString("hoten",hoTen);
                bundle1.putString("diachi",diaChi);
                bundle1.putString("gmail",email);
                bundle1.putString("pass",pass);
                bundle1.putParcelableArrayList("list",listSearch);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }
    public void anhXa(){
        but_back_account=findViewById(R.id.but_back_account);
        ed_old_pass=findViewById(R.id.ed_old_pass);
        ed_new_pass=findViewById(R.id.ed_new_pass);
        ed_confirm_pass=findViewById(R.id.ed_confirm_pass);
        but_confirm=findViewById(R.id.but_confirm);
    }
}