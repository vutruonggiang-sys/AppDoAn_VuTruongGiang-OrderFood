package com.example.appdoan_vutruonggiang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.appdoan_vutruonggiang.R;

public class WebviewActivity extends Activity {
    String sdt="",hoTen="",diaChi="",email="",pass="";
    String url="",name="",detail="",id="",idNhaHang="",type="";
    float price=0,rating=5;
    String urlWeb="https://www.facebook.com/vutruonggiang1912/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_webview);
    }
}