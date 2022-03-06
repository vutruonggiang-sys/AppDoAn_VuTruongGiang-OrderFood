package com.example.appdoan_vutruonggiang.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;


import com.example.appdoan_vutruonggiang.adapter.AdapterRecyleViewSearch;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.inteface.IItemFood;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    AdapterRecyleViewSearch adpterRecyleViewSearch;
    RecyclerView dataSearch;
    ImageView home, search, giohang, account;
    List<Food> foodList;
    SearchView searchView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        anhXa();
        user= FirebaseAuth.getInstance().getCurrentUser();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false);
        dataSearch.setLayoutManager(layoutManager);

        Bundle bundle = this.getIntent().getExtras();
        foodList = bundle.getParcelableArrayList("list");
        adpterRecyleViewSearch = new AdapterRecyleViewSearch(foodList, SearchActivity.this, new IItemFood() {
            @Override
            public void onClickItemFood(Food food) {
                chuyenManHinh(food);
            }
        });
        dataSearch.setAdapter(adpterRecyleViewSearch);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SearchActivity.this, MainActivity.class);
                Bundle bundle1 = new Bundle();
                intent1.putExtras(bundle1);
                startActivity(intent1);
                finish();
            }
        });
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SearchActivity.this, CartActivity.class);
                ArrayList<Food> listSearch = (ArrayList<Food>) foodList;
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("list", listSearch);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                finish();
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SearchActivity.this, AccountActivity.class);
                ArrayList<Food> listSearch = (ArrayList<Food>) foodList;
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("list", listSearch);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SearchActivity.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adpterRecyleViewSearch.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adpterRecyleViewSearch.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void chuyenManHinh(Food food) {
        Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("url", food.getUrl());
        bundle1.putString("food_name", food.getName());
        bundle1.putFloat("food_price", food.getPrice());
        bundle1.putFloat("food_rating", food.getReview());
        bundle1.putString("food_detail", food.getDetail());
        bundle1.putString("food_idnhahang", food.getIdNhaHang());
        bundle1.putString("food_id", food.getId());
        bundle1.putString("food_type", food.getType());
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adpterRecyleViewSearch != null) {
            adpterRecyleViewSearch.release();
        }
    }

    public void anhXa() {
        dataSearch = findViewById(R.id.dataSearch);
        home = findViewById(R.id.home);
        search = findViewById(R.id.search);
        giohang = findViewById(R.id.giohang);
        account = findViewById(R.id.account);
    }
}