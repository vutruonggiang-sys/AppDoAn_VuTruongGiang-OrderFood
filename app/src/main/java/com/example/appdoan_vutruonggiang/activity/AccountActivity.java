package com.example.appdoan_vutruonggiang.activity;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.UI.User;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.example.appdoan_vutruonggiang.presenter.Process_Bank;
import com.example.appdoan_vutruonggiang.presenter.Process_Change_Pass;
import com.example.appdoan_vutruonggiang.presenter.Process_Food;
import com.example.appdoan_vutruonggiang.presenter.Processing_DangXuat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends Activity {
    TextView tien,passAccount;
    ImageView eye_open,home,search,giohang,account;
    EditText nameAccount,emailAccount,addressAccount,sdtAccount;
    Button but_save_account,but_cancel_account;
    LinearLayout tv_phanHoi,tv_change_pass,tv_HoaDon,tv_chonThe,tv_DangXuat;
    String sdt="",hoTen="",diaChi="",email="",pass="";
    List<Food> foodList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_account);
        anhXa();
        //binding.account.setBackgroundColor();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("user");
        Bundle bundle=this.getIntent().getExtras();
        sdt=sdt+bundle.getString("phoneNumber");
        hoTen=hoTen+bundle.getString("hoten");
        diaChi=diaChi+bundle.getString("diachi");
        email=email+bundle.getString("gmail");
        pass=pass+bundle.get("pass");
        foodList=bundle.getParcelableArrayList("list");

        nameAccount.setText(hoTen);
        emailAccount.setText(email);
        addressAccount.setText(diaChi);
        sdtAccount.setText(sdt);
        sdtAccount.setEnabled(false);
        passAccount.setText("*******");
        but_cancel_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameAccount.setText(hoTen);
                emailAccount.setText(email);
                addressAccount.setText(diaChi);
                sdtAccount.setText(sdt);
                passAccount.setText("*******");
                User user=new User(diaChi,email,hoTen,pass,sdt);
                databaseReference.child(sdtAccount.getText().toString().trim()).setValue(user);
            }
        });
        but_save_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user=new User(addressAccount.getText().toString(),emailAccount.getText().toString(),
                        nameAccount.getText().toString(),pass,sdt);
                databaseReference.child(sdtAccount.getText().toString().trim()).setValue(user);
                Toast.makeText(AccountActivity.this,"Bạn đã lưu thành công",Toast.LENGTH_SHORT).show();
            }
        });
        tv_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), Change_Pass_Activity.class);
                ArrayList<Food> listSearch= (ArrayList<Food>) foodList;
                Bundle bundle1=new Bundle();

                String hoTen=nameAccount.getText().toString();
                String diaChi=addressAccount.getText().toString();
                String email=emailAccount.getText().toString();

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
        tv_chonThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Process_Bank.bank(AccountActivity.this,sdt);
            }
        });
        tv_DangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Processing_DangXuat.dangXuat(AccountActivity.this, Login_Activity.class);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), MainActivity.class);
                Bundle bundle1=new Bundle();

                String hoTen=nameAccount.getText().toString();
                String diaChi=addressAccount.getText().toString();
                String email=emailAccount.getText().toString();

                bundle1.putString("phoneNumber",sdt);
                bundle1.putString("hoten",hoTen);
                bundle1.putString("diachi",diaChi);
                bundle1.putString("gmail",email);
                bundle1.putString("pass",pass);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), CartActivity.class);
                ArrayList<Food> listSearch= (ArrayList<Food>) foodList;
                Bundle bundle1=new Bundle();
                String hoTen=nameAccount.getText().toString();
                String diaChi=addressAccount.getText().toString();
                String email=emailAccount.getText().toString();
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
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), SearchActivity.class);
                ArrayList<Food> listSearch= (ArrayList<Food>) foodList;
                Bundle bundle1=new Bundle();

                String hoTen=nameAccount.getText().toString();
                String diaChi=addressAccount.getText().toString();
                String email=emailAccount.getText().toString();

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
        tv_phanHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), ChatActivity.class);
                ArrayList<Food> listSearch= (ArrayList<Food>) foodList;
                Bundle bundle1=new Bundle();

                String hoTen=nameAccount.getText().toString();
                String diaChi=addressAccount.getText().toString();
                String email=emailAccount.getText().toString();

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
        tv_HoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), BillActivity.class);
                ArrayList<Food> listSearch= (ArrayList<Food>) foodList;
                Bundle bundle1=new Bundle();

                String hoTen=nameAccount.getText().toString();
                String diaChi=addressAccount.getText().toString();
                String email=emailAccount.getText().toString();

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
        tien=findViewById(R.id.tien);
        eye_open=findViewById(R.id.eye_open);
        home=findViewById(R.id.home);
        search=findViewById(R.id.search);
        giohang=findViewById(R.id.giohang);
        account=findViewById(R.id.account);
        nameAccount=findViewById(R.id.nameAccount);
        emailAccount=findViewById(R.id.emailAccount);
        addressAccount=findViewById(R.id.addressAccount);
        sdtAccount=findViewById(R.id.sdtAccount);
        passAccount=findViewById(R.id.passAccount);
        but_cancel_account=findViewById(R.id.but_cancel_account);
        but_save_account=findViewById(R.id.but_save_account);
        tv_phanHoi=findViewById(R.id.tv_phanHoi);
        tv_change_pass=findViewById(R.id.tv_change_pass);
        tv_chonThe=findViewById(R.id.tv_chonThe);
        tv_HoaDon=findViewById(R.id.tv_HoaDon);
        tv_DangXuat=findViewById(R.id.tv_DangXuat);
    }
}