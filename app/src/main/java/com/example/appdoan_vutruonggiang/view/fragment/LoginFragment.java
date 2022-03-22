package com.example.appdoan_vutruonggiang.view.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.inteface.ILogin;
import com.example.appdoan_vutruonggiang.presenter.ProcessLogin;
import com.example.appdoan_vutruonggiang.view.activity.LoginActivity;
import com.example.appdoan_vutruonggiang.view.activity.HomePageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment implements ILogin {

    private View view;
    ProcessLogin process_login;
    TextInputLayout tilEmail, tilPass;
    TextInputEditText tietEmail, tietPass;
    CheckBox checkBoxStore;
    AppCompatButton but_login;
    TextView tv_forgetPass, tv_help, tv_register;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user;
    LoginActivity activity;
    public static Fragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.login_fragment,container,false);
        init();
        firebaseDatabase = FirebaseDatabase.getInstance();

        sharedPreferences = activity.getSharedPreferences("dataLogin", MODE_PRIVATE);

        tietEmail.setText(sharedPreferences.getString("email", ""));
        tietPass.setText(sharedPreferences.getString("pass", ""));
        checkBoxStore.setChecked(sharedPreferences.getBoolean("checked", false));

        databaseReference = firebaseDatabase.getReference().child("user");

        process_login = new ProcessLogin(this);
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tietEmail.getText().toString();
                String pass = tietPass.getText().toString();
                if (checkBoxStore.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("pass", pass);
                    editor.putBoolean("checked", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("email");
                    editor.remove("pass");
                    editor.remove("checked");
                    editor.commit();
                }
                auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(activity, "Successful Login", Toast.LENGTH_LONG).show();
                                    String[] arrayEmail = email.split("@");
                                    databaseReference = firebaseDatabase.getReference().child("user");
                                    databaseReference.child(arrayEmail[0]).child("pass").setValue(pass);
                                    Intent intent = new Intent(activity, HomePageActivity.class);
                                    startActivity(intent);
                                    activity.finish();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Try Again", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(RegisFragment.newInstance());
            }
        });
        tv_forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(ForgetPasswordFragment.newInstance());
            }
        });
        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(HelpFragment.newInstance());
            }
        });
        return view;
    }
    public void init() {
        activity= (LoginActivity) getActivity();
        tilEmail = view.findViewById(R.id.tilEmail);
        tilPass = view.findViewById(R.id.tilPass);
        tietEmail = view.findViewById(R.id.tietEmail);
        tietPass = view.findViewById(R.id.tietPass);
        checkBoxStore = view.findViewById(R.id.checkBoxStore);
        but_login = view.findViewById(R.id.but_login);
        tv_forgetPass = view.findViewById(R.id.tv_forgetPass);
        tv_help = view.findViewById(R.id.tv_help);
        tv_register = view.findViewById(R.id.tv_register);
    }

    @Override
    public void loginSuccessful(String s) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginError(String s) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
    }
}
