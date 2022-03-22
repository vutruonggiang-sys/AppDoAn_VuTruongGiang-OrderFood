package com.example.appdoan_vutruonggiang.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.presenter.BottomNavigationBehavior;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.example.appdoan_vutruonggiang.presenter.NetworkChangeListener;
import com.example.appdoan_vutruonggiang.view.fragment.AccountFragment;
import com.example.appdoan_vutruonggiang.view.fragment.FragmentCart;
import com.example.appdoan_vutruonggiang.view.fragment.HomePageFragment;
import com.example.appdoan_vutruonggiang.view.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePageActivity extends AppCompatActivity{
    private BottomNavigationView bottomNavigationView;
    private Food food;
    NetworkChangeListener networkChangeListener=new NetworkChangeListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        init();
        getFragment(HomePageFragment.newInstance());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigationHomePage:
                        getFragment(HomePageFragment.newInstance());
                        return true;
                    case R.id.navigationSearch:
                        getFragment(SearchFragment.newInstance());
                        return true;
                    case R.id.navigationCart:
                        getFragment(FragmentCart.newInstance());
                        return true;
                    case R.id.navigationAccount:
                        getFragment(AccountFragment.newInstance());
                        return true;
                    default:
                        return  false;
                }
            }
        });
    }

    public void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
    }

    public void init(){
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}