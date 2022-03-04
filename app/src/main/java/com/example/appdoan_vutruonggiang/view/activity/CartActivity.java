package com.example.appdoan_vutruonggiang.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdoan_vutruonggiang.view.fragment.FragmentCart;
import com.example.appdoan_vutruonggiang.view.fragment.FragmentDaGiao;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.Food_Order;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    TextView tvGioHang, tvDaGiao;
    ImageView home, search, giohang, account;
    String hoTen = "", pass = "";
    List<Food> foodList;
    List<Food_Order> food_orderListCart;
    FirebaseDatabase firebaseDatabase;
    long tien_giam_gia = 0;
    FirebaseUser user;
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        anhXa();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String[] arrayEmail = user.getEmail().split("@");
        email = email + arrayEmail[0];
        Bundle bundle = this.getIntent().getExtras();
        foodList = bundle.getParcelableArrayList("list");
        firebaseDatabase = FirebaseDatabase.getInstance();
        tvDaGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(FragmentDaGiao.newInstance());
                FragmentDaGiao.newInstance();
                tvDaGiao.setTextColor(Color.WHITE);
                tvDaGiao.setBackgroundResource(R.drawable.border_bgblue_colorwhite);
                tvGioHang.setTextColor(Color.BLACK);
                tvGioHang.setBackgroundResource(R.drawable.border_bgwhite_colorblack);
            }
        });

        getFragment(FragmentCart.newInstance());
        FragmentCart.newInstance();
        tvGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(FragmentCart.newInstance());
                FragmentCart.newInstance();
                tvDaGiao.setTextColor(Color.BLACK);
                tvDaGiao.setBackgroundResource(R.drawable.border_bgwhite_colorblack);
                tvGioHang.setTextColor(Color.WHITE);
                tvGioHang.setBackgroundResource(R.drawable.border_bgblue_colorwhite);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                ArrayList<Food> listSearch = (ArrayList<Food>) foodList;
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("list", listSearch);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AccountActivity.class);
                ArrayList<Food> listSearch = (ArrayList<Food>) foodList;
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("list", listSearch);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    private void getFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.Fra_GioHang, fragment).commit();
    }

    public List<Food_Order> getFood_orderListCart() {
        return food_orderListCart;
    }

    public String getEmail() {
        return email;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
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

    public void setFood_orderListDaGiao(List<Food_Order> list) {

    }

    public void setTien_giam_gia(long d) {
        this.tien_giam_gia = d;
    }

    public void anhXa() {
        tvGioHang = findViewById(R.id.tvGioHang);
        tvDaGiao = findViewById(R.id.tvDaGiao);
        home = findViewById(R.id.homeCart);
        search = findViewById(R.id.searchCart);
        giohang = findViewById(R.id.giohang);
        account = findViewById(R.id.accountCart);
    }

}