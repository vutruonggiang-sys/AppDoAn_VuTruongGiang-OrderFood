package com.example.appdoan_vutruonggiang.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.Food_Order;
import com.example.appdoan_vutruonggiang.modle.RevenueByTime;
import com.example.appdoan_vutruonggiang.modle.ThongTinNguoiOrder;
import com.example.appdoan_vutruonggiang.modle.TotalRevenueRes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdapterRecyleViewSumDaGiao extends RecyclerView.Adapter<AdapterRecyleViewSumDaGiao.ViewHoder> {
    List<ThongTinNguoiOrder> thongTinNguoiOrderList;
    String email;
    List<Food_Order> food_orderList;
    Context context;
    AdapterRecyleViewGioHang adapterRecyleViewGioHang;
    int d = 0;
    FirebaseDatabase firebaseDatabase;
    String tongFood = "";
    long tong = 0;
    List<RevenueByTime> revenueByTimeList = new ArrayList<>();
    List<TotalRevenueRes> totalRevenueResList = new ArrayList<>();
    String idRes = "";

    public AdapterRecyleViewSumDaGiao(List<ThongTinNguoiOrder> thongTinNguoiOrderList, String email, Context context) {
        this.thongTinNguoiOrderList = thongTinNguoiOrderList;
        this.email = email;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRecyleViewSumDaGiao.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_item_dagiao, parent, false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyleViewSumDaGiao.ViewHoder holder, int position) {
        ThongTinNguoiOrder thongTinNguoiOrder = thongTinNguoiOrderList.get(position);
        boolean check = false;
        holder.progressBar.setVisibility(View.GONE);
        if (thongTinNguoiOrder == null) {
            holder.tv_sum_dagiao.setText(0 + "");
            return;
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.dataDagiao.setLayoutManager(layoutManager);
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("da_giao").child(email).child(thongTinNguoiOrder.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                food_orderList = new ArrayList<>();
                tong = 0;
                food_orderList.clear();
                Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                for (DataSnapshot data : dataSnapshotIterable) {
                    Food_Order food_order = data.getValue(Food_Order.class);
                    food_orderList.add(food_order);
                    if (food_orderList.size() == 1) {
                        idRes = idRes + food_orderList.get(0).getIdNhaHang();
                    }
                    tong = tong + food_order.getPrice() * food_order.getAmount();
                    tongFood = tongFood + food_order.getName() + " (X" + (food_order.getPrice() * food_order.getAmount()) + ") ";
                }
                adapterRecyleViewGioHang = new AdapterRecyleViewGioHang(food_orderList, context);
                holder.dataDagiao.setAdapter(adapterRecyleViewGioHang);
                holder.tv_sum_dagiao.setText(tong + "");
                holder.tvTotalPayable.setText(tong + 20000 - thongTinNguoiOrder.getGiaKhuyenMai() + " VND");
                databaseReference.child("doanhthu").child(idRes).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> dataSnapshotIterable1 = snapshot.getChildren();
                        for (DataSnapshot data : dataSnapshotIterable1) {
                            TotalRevenueRes totalRevenueRes = data.getValue(TotalRevenueRes.class);
                            totalRevenueResList.add(totalRevenueRes);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("TotalRevenueTime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapshotIterable1 = snapshot.getChildren();
                for (DataSnapshot data : dataSnapshotIterable1) {
                    RevenueByTime revenueByTime = data.getValue(RevenueByTime.class);
                    revenueByTimeList.add(revenueByTime);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.checkBox_ThanhToan.setChecked(check);
        holder.checkBox_ThanhToan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                d = 1;
            }
        });

        holder.but_ThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (d != 1) {
                    Toast.makeText(context, context.getString(R.string.reqOrder), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    holder.progressBar.setVisibility(View.VISIBLE);
                    holder.progressBar.isShown();
                    long totalMoneyTime = 0;
                    for (int i = 0; i < food_orderList.size(); i++) {
                        for (int j = 0; j < totalRevenueResList.size(); j++) {
                            if (totalRevenueResList.get(j).getId().equals(food_orderList.get(i).getId())) {
                                TotalRevenueRes totalRevenueRes1 = totalRevenueResList.get(j);
                                databaseReference.child("doanhthu").child(food_orderList.get(i).getIdNhaHang()).child(food_orderList.get(i).getId()).child("total").setValue(totalRevenueRes1.getTotal() + food_orderList.get(i).getPrice() * food_orderList.get(i).getAmount());
                            }
                        }
                        if (i == food_orderList.size()) {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                        totalMoneyTime = totalMoneyTime + food_orderList.get(i).getAmount() * food_orderList.get(i).getPrice();
                    }
                    databaseReference.child("thong_tin_nguoi_nhan_hang").child(email).child(thongTinNguoiOrder.getId()).removeValue();
                    databaseReference.child("da_giao").child(email).child(thongTinNguoiOrder.getId()).removeValue();
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                    String gioHienTai = simpleDateFormat.format(calendar.getTime());
                    try {
                        Date hienTai = simpleDateFormat.parse(gioHienTai);
                        Date tenHour = simpleDateFormat.parse("11:00");
                        Date onePmHour = simpleDateFormat.parse("13:00");
                        Date fivePmHour = simpleDateFormat.parse("17:00");
                        Date sevenPmHour = simpleDateFormat.parse("19:00");
                        for (int i = 0; i < revenueByTimeList.size(); i++) {
                            if (idRes.equals(revenueByTimeList.get(i).getId())) {
                                if (hienTai.after(tenHour) && hienTai.before(onePmHour)) {
                                    databaseReference.child("TotalRevenueTime").child(idRes).child("lunch").setValue(totalMoneyTime + revenueByTimeList.get(i).getLunch());
                                } else {
                                    if (hienTai.after(fivePmHour) && hienTai.before(sevenPmHour)) {
                                        databaseReference.child("TotalRevenueTime").child(idRes).child("tonight").setValue(totalMoneyTime + revenueByTimeList.get(i).getTonight());
                                    } else {
                                        databaseReference.child("TotalRevenueTime").child(idRes).child("nothing").setValue(totalMoneyTime + revenueByTimeList.get(i).getNothing());
                                    }
                                }
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        holder.tv_hienThiMaGiamGia.setText(thongTinNguoiOrder.getGiaKhuyenMai() + " VND");
    }

    public void release() {
        context = null;
    }

    @Override
    public int getItemCount() {
        if (thongTinNguoiOrderList == null)
            return 0;
        else
            return thongTinNguoiOrderList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tv_sum_dagiao;
        RecyclerView dataDagiao;
        CheckBox checkBox_ThanhToan;
        TextView tv_hienThiMaGiamGia;
        Button but_ThanhToan;
        TextView tvTotalPayable;
        ProgressBar progressBar;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tv_sum_dagiao = itemView.findViewById(R.id.tv_sum_money);
            dataDagiao = itemView.findViewById(R.id.data_sum_dagiao);
            checkBox_ThanhToan = itemView.findViewById(R.id.check_thanhToan);
            tv_hienThiMaGiamGia = itemView.findViewById(R.id.tvMaGiamGia);
            but_ThanhToan = itemView.findViewById(R.id.but_order_cart);
            tvTotalPayable = itemView.findViewById(R.id.tvTotalPayable);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
