package com.example.appdoan_vutruonggiang.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.presenter.ProcessingDangKy;

public class RegisActivity extends Activity {
    EditText edName,edSdt,edAddress,edEmail,edPass,edConform;
    Button butCancel,butOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_regis);
        anhXa();
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        butOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProcessingDangKy.dangKy(RegisActivity.this,edAddress,edName,edConform,edPass,edEmail,edSdt);
            }
        });
    }
    public  void anhXa(){
        EditText edName=findViewById(R.id.edNameRegister);
        EditText edSdt=findViewById(R.id.edSdtRegister);
        EditText edAddress=findViewById(R.id.edAddressRegister);
        EditText edEmail=findViewById(R.id.edEmailRegister);
        EditText edPass=findViewById(R.id.edPassRegister);
        EditText edConform=findViewById(R.id.edConformPassRegister);
        Button butCancel=findViewById(R.id.but_Register_Cancel);
        Button butOK=findViewById(R.id.but_Register_OK);
    }
}