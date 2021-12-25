package com.example.appdoan_vutruonggiang.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appdoan_vutruonggiang.R;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetPasswordActivity extends Activity {
    EditText edEmail,edXacNhanMa;
    Button but_send_email;
    TextView tv_back_login;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        anhXa();
        auth = FirebaseAuth.getInstance();
        tv_back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
//        binding.butSendEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email=binding.edEmail.getText().toString();
//                boolean check=checkEmail(email);
//                if(check){
//                    auth.sendPasswordResetEmail(email)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(ForgetPasswordActivity.this,"Giử Email Thành Công",Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }else{
//                    Toast.makeText(ForgetPasswordActivity.this,"Email không tồn tại",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        but_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private boolean checkEmail(String s){
        return Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }
    public void anhXa(){
        edEmail=findViewById(R.id.edEmail);
        edXacNhanMa=findViewById(R.id.edXacNhanMa);
        but_send_email=findViewById(R.id.but_send_email);
        tv_back_login=findViewById(R.id.tv_back_login);
    }
}