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
    ProcessingDangKy processingDangKy;

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
                processingDangKy.dangKy(RegisActivity.this,edAddress,edName,edConform,edPass,edEmail,edSdt);
            }
        });
    }
    public  void anhXa(){
         edName=findViewById(R.id.edNameRegister);
         edSdt=findViewById(R.id.edSdtRegister);
         edAddress=findViewById(R.id.edAddressRegister);
         edEmail=findViewById(R.id.edEmailRegister);
         edPass=findViewById(R.id.edPassRegister);
         edConform=findViewById(R.id.edConformPassRegister);
         butCancel=findViewById(R.id.but_Register_Cancel);
         butOK=findViewById(R.id.but_Register_OK);

        processingDangKy=new ProcessingDangKy();
    }
}