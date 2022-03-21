package com.example.appdoan_vutruonggiang.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.adapter.AdapterRecyleViewSearch;
import com.example.appdoan_vutruonggiang.inteface.IItemFood;
import com.example.appdoan_vutruonggiang.presenter.Food;
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

public class SearchFragment extends Fragment {
    private View view;
    AdapterRecyleViewSearch adpterRecyleViewSearch;
    RecyclerView dataSearch;
    ImageView home, search, giohang, account;
    List<Food> foodList;
    FirebaseUser user;
    HomePageActivity homePageActivity;
    EditText edSearch;
    TextView tvNoFindResult;
    ImageView imgNoFindResult;

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);
        init();
        user = FirebaseAuth.getInstance().getCurrentUser();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(homePageActivity, RecyclerView.VERTICAL, false);
        dataSearch.setLayoutManager(layoutManager);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("food");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                for (DataSnapshot data : dataSnapshotIterable) {
                    Food food = data.getValue(Food.class);
                    foodList.add(food);
                }
                adpterRecyleViewSearch = new AdapterRecyleViewSearch(foodList, homePageActivity, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        goToDetail(food);
                    }
                }, tvNoFindResult, imgNoFindResult);
                dataSearch.setAdapter(adpterRecyleViewSearch);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adpterRecyleViewSearch = new AdapterRecyleViewSearch(foodList, homePageActivity, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        goToDetail(food);
                    }
                }, tvNoFindResult, imgNoFindResult);
                dataSearch.setAdapter(adpterRecyleViewSearch);
                adpterRecyleViewSearch.getFilter().filter(edSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                adpterRecyleViewSearch = new AdapterRecyleViewSearch(foodList, homePageActivity, new IItemFood() {
                    @Override
                    public void onClickItemFood(Food food) {
                        goToDetail(food);
                    }
                }, tvNoFindResult, imgNoFindResult);
                dataSearch.setAdapter(adpterRecyleViewSearch);
                adpterRecyleViewSearch.getFilter().filter(edSearch.getText().toString());
            }
        });
        return view;
    }

    public void goToDetail(Food food) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
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
        homePageActivity = (HomePageActivity) getActivity();
        dataSearch = view.findViewById(R.id.dataSearch);
        home = view.findViewById(R.id.home);
        search = view.findViewById(R.id.search);
        giohang = view.findViewById(R.id.giohang);
        account = view.findViewById(R.id.account);
        edSearch = view.findViewById(R.id.edSearch);
        tvNoFindResult = view.findViewById(R.id.tvNoFindResult);
        imgNoFindResult = view.findViewById(R.id.imgNoFindResult);
        foodList = new ArrayList<>();
    }
}
