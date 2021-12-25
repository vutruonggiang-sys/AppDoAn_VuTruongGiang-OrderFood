package com.example.appdoan_vutruonggiang.presenter;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.example.appdoan_vutruonggiang.R;

public class ProcessHoaDonGiaoDich {
    public ProcessHoaDonGiaoDich() {
    }

    public static void getHoaDon(Context context, String name, String sdt, String address, String time, String tong, long tongTien, long giamGia){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_hoadongiaodich);
        dialog.setCancelable(true);
        TextView tvTime=dialog.findViewById(R.id.tv_time_billed);
        TextView edName=dialog.findViewById(R.id.tvNameUser);
        TextView edSdt=dialog.findViewById(R.id.tvSDTUser);
        TextView edAddress=dialog.findViewById(R.id.tvAddressUser);
        TextView sumFood =dialog.findViewById(R.id.tv_tonghopmon);
        TextView tv_tongTien=dialog.findViewById(R.id.tv_tongtienhoadon);
        TextView tv_giamGia=dialog.findViewById(R.id.tv_GiamGia);
        TextView tv_tienPhaiTra=dialog.findViewById(R.id.tv_tongtien);

        tvTime.setText(time);
        edName.setText(name);
        edSdt.setText(sdt);
        edAddress.setText(address);
        sumFood.setText(tong);
        tv_tongTien.setText(tongTien+" VND");
        tv_giamGia.setText(giamGia+" VND");
        long t=tongTien+20000-giamGia;
        tv_tienPhaiTra.setText(t+" VND");
        dialog.show();
    }
}
