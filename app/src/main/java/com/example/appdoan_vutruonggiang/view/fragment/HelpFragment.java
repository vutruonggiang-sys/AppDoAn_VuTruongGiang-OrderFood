package com.example.appdoan_vutruonggiang.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.view.activity.LoginActivity;

public class HelpFragment extends Fragment {

    private View view;
    LoginActivity activity;
    TextView tvBackLogin;
    public static Fragment newInstance() {

        Bundle args = new Bundle();

        HelpFragment fragment = new HelpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.help_fragment,container,false);
        init();
        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(LoginFragment.newInstance());
            }
        });
        return view;
    }
    public void init(){
        activity= (LoginActivity) getActivity();
        tvBackLogin=view.findViewById(R.id.tvBackLogin);
    }
}
