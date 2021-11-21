package com.example.appdoan_vutruonggiang.sqlite;

import android.app.Application;

import static com.facebook.stetho.Stetho.initializeWithDefaults;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initializeWithDefaults(this);
    }
}
