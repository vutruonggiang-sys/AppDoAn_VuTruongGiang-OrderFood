package com.example.appdoan_vutruonggiang.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.presenter.ProcessingDangKy;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisActivity extends Activity {
    TextInputLayout tilYourNumber,tilYourName,tilPass,tilConfirmPass;
    TextInputEditText tietYourNumber,tietYourName,tietPass,tietConfirmPass;
    TextView tvBackLogin;
    ImageView imgBackArrow;
    AppCompatButton butCancel,butRegis;
    ProcessingDangKy processingDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_regis);
        anhXa();
        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        butRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processingDangKy.dangKy(RegisActivity.this,tietYourNumber,tietYourName,tietPass,tietConfirmPass);
            }
        });
    }
    public  void anhXa(){
         tilYourNumber=findViewById(R.id.tilYourNumber);
         tietYourNumber=findViewById(R.id.tietYourNumber);
         tietYourName=findViewById(R.id.tietYourName);
         tilYourName=findViewById(R.id.tilYourName);
         tilPass=findViewById(R.id.tilPassword);
         tietPass=findViewById(R.id.tietPassword);
         tilConfirmPass=findViewById(R.id.tilConfirmPassword);
         tietConfirmPass=findViewById(R.id.tietConfirmPassword);
         butCancel=findViewById(R.id.acbCancel);
         butRegis=findViewById(R.id.acbRegis);
         tvBackLogin=findViewById(R.id.tv_BackLogin);
         imgBackArrow=findViewById(R.id.imgBackArrow);
         processingDangKy=new ProcessingDangKy();
    }
}