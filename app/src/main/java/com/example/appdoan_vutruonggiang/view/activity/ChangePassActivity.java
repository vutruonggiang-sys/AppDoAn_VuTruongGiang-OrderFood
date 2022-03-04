package com.example.appdoan_vutruonggiang.view.activity;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.User;
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

import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.List;

public class ChangePassActivity extends Activity {
    ImageView but_back_account;
    EditText ed_old_pass,ed_new_pass,ed_confirm_pass;
    AppCompatButton but_confirm;
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
        foodList=bundle.getParcelableArrayList("list");
        but_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        if(!pass.equals(ed_old_pass.getText().toString().trim())){
//            Toast.makeText(ChangePassActivity.this,"Bạn nhập sai mật khẩu cũ",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(ed_old_pass.getText().toString().trim().equals("")||ed_new_pass.getText().toString().trim().equals("")||ed_confirm_pass.getText().toString().trim().equals("")){
//            Toast.makeText(ChangePassActivity.this,"Không được để trống",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(pass.toString().trim().equals(ed_new_pass.getText().toString().trim())){
//            Toast.makeText(ChangePassActivity.this,"Bạn đã nhập mật khẩu cũ",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(ed_new_pass.getText().toString().trim().equals(ed_confirm_pass.getText().toString().trim())){
//            Toast.makeText(ChangePassActivity.this,"Bạn đã thay đổi mật khẩu",Toast.LENGTH_SHORT).show();
//            User user=new User(hoTen,ed_new_pass.getText().toString(),email);
//            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
//            DatabaseReference databaseReference=firebaseDatabase.getReference().child("user");
//            databaseReference.child(email).setValue(user);
//        }else{
//            Toast.makeText(ChangePassActivity.this,"xác nhận sai mật khẩu",Toast.LENGTH_SHORT).show();
//            return;
//        }
            }
        });
        but_back_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), AccountActivity.class);
                ArrayList<Food> listSearch= (ArrayList<Food>) foodList;
                Bundle bundle1=new Bundle();
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