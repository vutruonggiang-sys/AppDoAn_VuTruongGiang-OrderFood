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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements ILogin, ValueEventListener {
    int d=0;
    ProcessLogin process_login;
    TextInputLayout tilPhoneNumber,tilPass;
    TextInputEditText tietPhoneNumber,tietPass;
    CheckBox checkBoxStore;
    AppCompatButton but_login;
    TextView tv_forgetPass,tv_help,tv_register;
    List<User> userList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    User user;
    SharedPreferences sharedPreferences;
    ProcessConnection process_connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_);
        anhXa();
        firebaseDatabase =FirebaseDatabase.getInstance();

        sharedPreferences= getSharedPreferences("dataLogin",MODE_PRIVATE);

        tietPhoneNumber.setText(sharedPreferences.getString("sdt",""));
        tietPass.setText(sharedPreferences.getString("pass",""));
        checkBoxStore.setChecked(sharedPreferences.getBoolean("checked",false));

        databaseReference=firebaseDatabase.getReference().child("user");
        databaseReference.addValueEventListener(this);

        process_login=new ProcessLogin(this);
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt=tietPhoneNumber.getText().toString();
                String pass=tietPass.getText().toString();
                for(User user1:userList){
                    if(user1.getPass().equals(pass) && user1.getSdt().equals(sdt)) {
                        d++;
                        user=user1;
                        break;
                    }
                }
                process_login.onLogin(d);
                if(d==1)
                {
                    if(checkBoxStore.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("sdt", sdt);
                        editor.putString("pass", pass);
                        editor.putBoolean("checked", true);
                        editor.commit();
                    }else{
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.remove("sdt");
                        editor.remove("pass");
                        editor.remove("checked");
                        editor.commit();
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("phoneNumber",sdt);
                    bundle.putString("hoten",user.getName());
                    bundle.putString("pass",pass);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),RegisActivity.class);
                startActivity(intent);
            }
        });
        tv_forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        process_connection =new ProcessConnection();
        if(!process_connection.check_inonline(LoginActivity.this)){
            process_connection.show_disconnect(LoginActivity.this);
        }
        super.onStart();
    }

    @Override
    public void loginSuccessful(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginError(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        userList=new ArrayList<>();
        Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
        for(DataSnapshot data:dataSnapshotIterable){
            User user=data.getValue(User.class);
            userList.add(user);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
    public void anhXa(){
        tilPhoneNumber=findViewById(R.id.tilPhoneNumber);
        tilPass=findViewById(R.id.tilPass);
        tietPhoneNumber=findViewById(R.id.tietPhoneNumber);
        tietPass=findViewById(R.id.tietPass);
        checkBoxStore=findViewById(R.id.checkBoxStore);
        but_login=findViewById(R.id.but_login);
        tv_forgetPass=findViewById(R.id.tv_forgetPass);
        tv_help=findViewById(R.id.tv_help);
        tv_register=findViewById(R.id.tv_register);
    }
}