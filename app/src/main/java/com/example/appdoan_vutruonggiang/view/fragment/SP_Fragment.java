package com.example.appdoan_vutruonggiang.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.view.activity.HomePageActivity;
import com.example.appdoan_vutruonggiang.view.activity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SP_Fragment extends Fragment {

    private View view;
    private LoginActivity loginActivity;
    private FirebaseAuth auth;
    private FirebaseUser user;

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        SP_Fragment fragment = new SP_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sp_fragment, container, false);
        loginActivity = (LoginActivity) getActivity();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user == null) {
                    loginActivity.getFragment(LoginFragment.newInstance());
                } else {
                    Intent intent = new Intent(loginActivity.getApplicationContext(), HomePageActivity.class);
                    startActivity(intent);
                    loginActivity.finish();
                }
            }
        }, 1000);
        return view;
    }
}
