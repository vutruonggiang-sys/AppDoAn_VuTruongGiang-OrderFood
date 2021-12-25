package com.example.appdoan_vutruonggiang.presenter;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;

import com.example.appdoan_vutruonggiang.R;

public class ProcessConnection {
    public ProcessConnection() {
    }

    public  boolean check_inonline(Context context){
        final ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager!=null){
            NetworkInfo info=connectivityManager.getActiveNetworkInfo();
            if(info!=null&&info.isConnectedOrConnecting())
                return true;
        }
        return false;
    }
    public void show_disconnect(Context context){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.form_disconnect);
        if(check_inonline(context))
            dialog.dismiss();
        dialog.show();
    }
}
