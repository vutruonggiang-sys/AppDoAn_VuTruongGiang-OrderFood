package com.example.appdoan_vutruonggiang.presenter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.view.activity.LoginActivity;

public class ProcessingDangXuat {
    public static void dangXuat(Context context, Class<LoginActivity> login_activityClass){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dangxuat);
        dialog.setCancelable(true);

        Button but_Cancel=dialog.findViewById(R.id.but_logout_Cancel);
        Button but_OK=dialog.findViewById(R.id.but_logout_OK);

        but_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        but_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(context,login_activityClass);
                context.startActivity(intent);
            }
        });
        dialog.show();
    }
}
