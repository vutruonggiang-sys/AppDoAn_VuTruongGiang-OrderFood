package com.example.appdoan_vutruonggiang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.appdoan_vutruonggiang.R;

public class Help_Activity extends Activity {
    TextView but_back_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_help_);
        anhXa();
        but_back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), Login_Activity.class);
                startActivity(intent);
            }
        });
    }
    public void anhXa(){
        but_back_login=findViewById(R.id.but_back_login);
    }
}