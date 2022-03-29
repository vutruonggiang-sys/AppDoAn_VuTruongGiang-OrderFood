package com.example.appdoan_vutruonggiang.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.adapter.AdapterRecyleViewFood;
import com.example.appdoan_vutruonggiang.adapter.AdapterRecyleViewFood1;
import com.example.appdoan_vutruonggiang.adapter.AdapterRetaurant;
import com.example.appdoan_vutruonggiang.inteface.IItemFood;
import com.example.appdoan_vutruonggiang.modle.NhaHang;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.example.appdoan_vutruonggiang.presenter.ProcessFood;
import com.example.appdoan_vutruonggiang.view.activity.HomePageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment {
    private View view;
    ViewFlipper vf_Khuyen_mai;
    LinearLayout DoAnNhanh, DoUong, Com, All;
    RecyclerView dataRecyleView1, dataRecyleView, dataRestaurant;
    ImageView home;
    List<Food> foodList = new ArrayList<>();
    AdapterRecyleViewFood adapterRecyleViewFood;
    AdapterRecyleViewFood1 adapterRecyleViewFood1;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProcessFood process_food;
    FirebaseUser user;
    String email = "";
    HomePageActivity homePageActivity;
    List<NhaHang> nhaHangList;
    public static Fragment newInstance() {

        Bundle args = new Bundle();

        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_page_fragment,container,false);
        init();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String[] arrayEmail=user.getEmail().split("@");
        email=email+arrayEmail[0];
        process_food.khuyenMai(vf_Khuyen_mai);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(homePageActivity, RecyclerView.VERTICAL, false);
        dataRecyleView.setLayoutManager(layoutManager);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(homePageActivity, RecyclerView.HORIZONTAL, false);
        dataRecyleView1.setLayoutManager(layoutManager1);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("food").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                for (DataSnapshot data : dataSnapshotIterable) {
                    Food food = data.getValue(Food.class);
                    foodList.add(food);
                }
                process_food.sapTheoDanhGia(foodList);
                adapterRecyleViewFood = new AdapterRecyleViewFood(foodList, homePageActivity, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        goToDetail(food);
                    }
                }, email);
                adapterRecyleViewFood1 = new AdapterRecyleViewFood1(foodList, homePageActivity, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        goToDetail(food);
                    }
                }, email);
                dataRecyleView.setAdapter(adapterRecyleViewFood);
                dataRecyleView1.setAdapter(adapterRecyleViewFood1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //chon do an nhanh, do uong, com...
        DoAnNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Food> fooDFast;
                fooDFast = process_food.doAnNhanh(foodList);
                adapterRecyleViewFood = new AdapterRecyleViewFood(fooDFast, homePageActivity, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        goToDetail(food);
                    }
                }, email);
                adapterRecyleViewFood1 = new AdapterRecyleViewFood1(fooDFast, homePageActivity, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        goToDetail(food);
                    }
                }, email);
                dataRecyleView.setAdapter(adapterRecyleViewFood);
                dataRecyleView1.setAdapter(adapterRecyleViewFood1);
            }
        });
        DoUong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Food> foodDrink = process_food.doUong(foodList);
                adapterRecyleViewFood = new AdapterRecyleViewFood(foodDrink, homePageActivity, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        goToDetail(food);
                    }
                }, email);
                adapterRecyleViewFood1 = new AdapterRecyleViewFood1(foodDrink, homePageActivity, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        goToDetail(food);
                    }
                }, email);
                dataRecyleView.setAdapter(adapterRecyleViewFood);
                dataRecyleView1.setAdapter(adapterRecyleViewFood1);
            }
        });
        Com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Food> foodRice = process_food.com(foodList);
                adapterRecyleViewFood = new AdapterRecyleViewFood(foodRice, homePageActivity, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        goToDetail(food);
                    }
                }, email);
                adapterRecyleViewFood1 = new AdapterRecyleViewFood1(foodRice, homePageActivity, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        goToDetail(food);
                    }
                }, email);
                dataRecyleView.setAdapter(adapterRecyleViewFood);
                dataRecyleView1.setAdapter(adapterRecyleViewFood1);
            }
        });
        All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterRecyleViewFood = new AdapterRecyleViewFood(foodList, homePageActivity, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        goToDetail(food);
                    }
                }, email);
                adapterRecyleViewFood1 = new AdapterRecyleViewFood1(foodList, homePageActivity, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        goToDetail(food);
                    }
                }, email);
                dataRecyleView.setAdapter(adapterRecyleViewFood);
                dataRecyleView1.setAdapter(adapterRecyleViewFood1);
            }
        });
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getRestaurant();
            }
        },2000);
        return view;
    }

    public void getRestaurant(){
        nhaHangList=new ArrayList<>();
        databaseReference.child("nhaHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                for(DataSnapshot data:dataSnapshotIterable){
                    NhaHang nhaHang=data.getValue(NhaHang.class);
                    nhaHangList.add(nhaHang);
                }
                AdapterRetaurant adapterRetaurant=new AdapterRetaurant(nhaHangList,homePageActivity);
                dataRestaurant.setAdapter(adapterRetaurant);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void goToDetail(Food food){
        DetailFragment detailFragment=new DetailFragment();
        Bundle bundle=new Bundle();
        bundle.putString("url", food.getUrl());
        bundle.putString("food_name", food.getName());
        bundle.putFloat("food_price", food.getPrice());
        bundle.putFloat("food_rating", food.getReview());
        bundle.putString("food_detail", food.getDetail());
        bundle.putString("food_idnhahang", food.getIdNhaHang());
        bundle.putString("food_id", food.getId());
        bundle.putString("food_type", food.getType());
        detailFragment.setArguments(bundle);
        homePageActivity.getFragment(detailFragment);
        homePageActivity.getBottomNavigationView().setVisibility(View.GONE);
    }

    public void init() {
        homePageActivity= (HomePageActivity) getActivity();
        vf_Khuyen_mai = view.findViewById(R.id.vf_Khuyen_mai);
        DoAnNhanh = view.findViewById(R.id.DoAnNhanh);
        DoUong = view.findViewById(R.id.DoUong);
        Com = view.findViewById(R.id.Com);
        All = view.findViewById(R.id.All);
        dataRecyleView1 = view.findViewById(R.id.dataRecyleView1);
        dataRecyleView = view.findViewById(R.id.dataRecyleView);
        dataRestaurant=view.findViewById(R.id.dataRestau);
        home = view.findViewById(R.id.home);
        process_food = new ProcessFood();
    }
}
