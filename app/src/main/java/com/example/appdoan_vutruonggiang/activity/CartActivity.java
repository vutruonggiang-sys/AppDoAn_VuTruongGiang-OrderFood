package com.example.appdoan_vutruonggiang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdoan_vutruonggiang.UI.Key;
import com.example.appdoan_vutruonggiang.fragment.Fragment_Cart;
import com.example.appdoan_vutruonggiang.fragment.Fragment_DaGiao;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.UI.Food_Order;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    TextView tvGioHang,tvDaGiao;
    ImageView but_ThongBao,home,search,giohang,account;
    String sdt="",hoTen="",diaChi="",email="",pass="";
    List<Food> foodList;
    List<Food_Order> food_orderListCart;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference1,databaseReference2;
    long tong=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        anhXa();
        Bundle bundle=this.getIntent().getExtras();
        sdt=sdt+bundle.getString("phoneNumber");
        hoTen=hoTen+bundle.getString("hoten");
        diaChi=diaChi+bundle.getString("diachi");
        email=email+bundle.getString("gmail");
        pass=pass+bundle.getString("pass");
        foodList=bundle.getParcelableArrayList("list");

        firebaseDatabase=FirebaseDatabase.getInstance();


        tvDaGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(Fragment_DaGiao.newInstance());
                Fragment_DaGiao.newInstance();
            }
        });

        getFragment(Fragment_Cart.newInstance());
        Fragment_Cart.newInstance();
        tvGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(Fragment_Cart.newInstance());
                Fragment_Cart.newInstance();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), MainActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putString("phoneNumber",sdt);
                bundle1.putString("hoten",hoTen);
                bundle1.putString("diachi",diaChi);
                bundle1.putString("gmail",email);
                bundle1.putString("pass",pass);

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
        account.setOnClickListener(new View.OnClickListener() {
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
        but_ThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), ThongBaoActivity.class);
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
    private void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.Fra_GioHang,fragment).commit();
    }

    public List<Food_Order> getFood_orderListCart() {
        return food_orderListCart;
    }
    public String getSdt(){
        return sdt;
    }
    public String getHoTen(){return hoTen;}
    public String getDiaChi(){return diaChi;}

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public void setFood_orderListDaGiao(List<Food_Order> list){
        for(int i=0;i<list.size();i++){
            tong=tong+list.get(i).getPrice()*list.get(i).getAmount();
        }
        Calendar calendar=Calendar.getInstance();
        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String time=dateFormat.format(calendar.getTime());
        databaseReference1=firebaseDatabase.getReference().child("da_giao").child(sdt).child(time);
        databaseReference2=firebaseDatabase.getReference().child("key_dagiao").child(sdt).child(time);
        Key key=new Key(time,time);
        databaseReference2.setValue(key);
        for(int i=0;i<list.size();i++){
            databaseReference1.child(list.get(i).getId()).setValue(list.get(i));
        }
    }
    public void anhXa(){
        tvGioHang=findViewById(R.id.tvGioHang);
        tvDaGiao=findViewById(R.id.tvDaGiao);
        but_ThongBao=findViewById(R.id.but_ThongBao);
        home=findViewById(R.id.homeCart);
        search=findViewById(R.id.searchCart);
        giohang=findViewById(R.id.giohang);
        account=findViewById(R.id.accountCart);
    }

}