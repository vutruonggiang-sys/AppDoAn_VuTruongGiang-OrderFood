package com.example.appdoan_vutruonggiang.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.appdoan_vutruonggiang.modle.Util;

public class NetworkChangeListener extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Util.isNetworkAvailable(context)) {
            Toast.makeText(context, "Offline!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Online!", Toast.LENGTH_SHORT).show();
        }
    }
}
