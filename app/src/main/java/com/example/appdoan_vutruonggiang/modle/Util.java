package com.example.appdoan_vutruonggiang.modle;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Util {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isNetworkAvailable(Context context){
        if(context==null){
            return false;
        }
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivityManager==null){
            return false;
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            Network network=connectivityManager.getActiveNetwork();
            if(network==null){
                return false;
            }
            NetworkCapabilities networkCapabilities=connectivityManager.getNetworkCapabilities(network);
            return networkCapabilities!=null &&  networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
        }else{
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }
}
