package com.example.appdoan_vutruonggiang.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.view.activity.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordFragment extends Fragment {

    private View view;
    EditText edEmail;
    Button butSendEmail;
    FirebaseAuth auth;
    TextView tvBackLogin;
    LoginActivity activity;
    public static Fragment newInstance() {

        Bundle args = new Bundle();

        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_forget_password,container,false);
        init();
        auth = FirebaseAuth.getInstance();
        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(LoginFragment.newInstance());
            }
        });
        butSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.sendPasswordResetEmail(edEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(activity,activity.getString(R.string.successfull),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }

    public void init(){
        activity= (LoginActivity) getActivity();
        edEmail=view.findViewById(R.id.edEmail);
        butSendEmail=view.findViewById(R.id.butSendEmail);
        tvBackLogin=view.findViewById(R.id.tvBackLogin);
    }
}
