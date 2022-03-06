package com.example.appdoan_vutruonggiang.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.User;
import com.example.appdoan_vutruonggiang.inteface.ILogin;
import com.example.appdoan_vutruonggiang.presenter.ProcessConnection;
import com.example.appdoan_vutruonggiang.presenter.ProcessLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements ILogin {
    ProcessLogin process_login;
    TextInputLayout tilEmail, tilPass;
    TextInputEditText tietEmail, tietPass;
    CheckBox checkBoxStore;
    AppCompatButton but_login;
    TextView tv_forgetPass, tv_help, tv_register;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    ProcessConnection process_connection;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_);
        anhXa();
        firebaseDatabase = FirebaseDatabase.getInstance();

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);

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
                                Toast.makeText(getApplicationContext(), "Successful Login", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HelpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        process_connection = new ProcessConnection();
        if (!process_connection.check_inonline(LoginActivity.this)) {
            process_connection.show_disconnect(LoginActivity.this);
        }
        super.onStart();
    }

    @Override
    public void loginSuccessful(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void anhXa() {
        tilEmail = findViewById(R.id.tilEmail);
        tilPass = findViewById(R.id.tilPass);
        tietEmail = findViewById(R.id.tietEmail);
        tietPass = findViewById(R.id.tietPass);
        checkBoxStore = findViewById(R.id.checkBoxStore);
        but_login = findViewById(R.id.but_login);
        tv_forgetPass = findViewById(R.id.tv_forgetPass);
        tv_help = findViewById(R.id.tv_help);
        tv_register = findViewById(R.id.tv_register);
    }
}