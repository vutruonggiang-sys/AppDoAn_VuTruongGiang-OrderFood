package com.example.appdoan_vutruonggiang.view.activity;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.Bill;
import com.example.appdoan_vutruonggiang.modle.Food_Order;
import com.example.appdoan_vutruonggiang.modle.GiamGia;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class PrintBillActivity extends Activity {
    TextView tv_time_bill,tonghopmon,tv_tongtienhoadon,tv_phiShip,tv_giamGia,tv_tongtien;
    TextView edNameUser,edSDTUser,edAddressUser;
    Button but_bill_Cancel,but_bill_OK;
    String sdt="",hoTen="",diaChi="",email="",pass="",key="";
    List<Food> foodList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Food_Order> food_orderList;
    List<GiamGia> giamGiaList;
    String tonghop="";
    long tong=0,t=0;
    long giaKhuyenMai=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_print_bill);
        anhXa();

        Bundle bundle=this.getIntent().getExtras();
        sdt=sdt+bundle.getString("phoneNumber");
        hoTen=hoTen+bundle.getString("hoten");
        diaChi=diaChi+bundle.getString("diachi");
        email=email+bundle.getString("gmail");
        pass=pass+bundle.getString("pass");
        foodList=bundle.getParcelableArrayList("list");
        key=key+bundle.getString("maDelete");

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        edNameUser.setText(hoTen);
        edAddressUser.setText(diaChi);
        edSDTUser.setText(sdt);

        Calendar calendar=Calendar.getInstance();
        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String time=dateFormat.format(calendar.getTime());
        tv_time_bill.setText(time);

        databaseReference.child("da_giao").child(sdt).child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                food_orderList=new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                for(DataSnapshot data:dataSnapshotIterable){
                    Food_Order food_order=data.getValue(Food_Order.class);
                    food_orderList.add(food_order);
                    tonghop=tonghop+food_order.getName()+"(x"+food_order.getAmount()+")  ";
                    tong=tong+food_order.getAmount()*food_order.getPrice();
                }
                tv_tongtienhoadon.setText(tong+" VND");
                tonghopmon.setText(tonghop);
                t=t+20000+tong;
                tv_tongtien.setText(t+" VND");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("key_dagiao").child(sdt).child(key).child("giaKhuyenMai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                giaKhuyenMai=Long.valueOf(snapshot.getValue().toString());
                tv_giamGia.setText(giaKhuyenMai+" VND");
                t=t-giaKhuyenMai;
                tv_tongtien.setText(t+" VND");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        but_bill_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> valuesUpdate=new HashMap<>();
                valuesUpdate.put("giamGia",0);
                valuesUpdate.put("id","id");
                valuesUpdate.put("name","name");
                HashMap<String,Object> keyUpdate=new HashMap<>();
                keyUpdate.put("haha",valuesUpdate);
                databaseReference.child("luu_ma_van_chuyen").child(sdt).updateChildren(keyUpdate);

                databaseReference.child("key_dagiao").child(sdt).child(key).removeValue();
                databaseReference.child("da_giao").child(sdt).child(key).removeValue();

                Intent intent=new Intent(getBaseContext(), MainActivity.class);
                ArrayList<Food> listSearch= (ArrayList<Food>) foodList;
                Bundle bundle1=new Bundle();
                bundle1.putString("phoneNumber",sdt);
                bundle1.putString("hoten",hoTen);
                bundle1.putString("diachi",diaChi);
                bundle1.putString("gmail",email);
                bundle1.putString("pass",pass);
                bundle1.putParcelableArrayList("list",listSearch);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        but_bill_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bill bill=new Bill(time,edNameUser.getText().toString(),edSDTUser.getText().toString(),edAddressUser.getText().toString(),
                        tonghopmon.getText().toString(),tong,giaKhuyenMai);
                databaseReference.child("bill").child(sdt).child(time).setValue(bill);

                HashMap<String,Object> valuesUpdate=new HashMap<>();
                valuesUpdate.put("giamGia",0);
                valuesUpdate.put("id","id");
                valuesUpdate.put("name","name");
                HashMap<String,Object> keyUpdate=new HashMap<>();
                keyUpdate.put("haha",valuesUpdate);
                databaseReference.child("luu_ma_van_chuyen").child(sdt).updateChildren(keyUpdate);

                databaseReference.child("key_dagiao").child(sdt).child(key).removeValue();
                databaseReference.child("da_giao").child(sdt).child(key).removeValue();

                Intent intent=new Intent(getBaseContext(), PrintBillActivity.class);
                ArrayList<Food> listSearch= (ArrayList<Food>) foodList;
                Bundle bundle1=new Bundle();
                bundle1.putString("phoneNumber",sdt);
                bundle1.putString("hoten",hoTen);
                bundle1.putString("diachi",diaChi);
                bundle1.putString("gmail",email);
                bundle1.putString("pass",pass);
                bundle1.putParcelableArrayList("list",listSearch);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }
    public void anhXa(){
        tv_time_bill=findViewById(R.id.tv_time_bill);
        tonghopmon=findViewById(R.id.tonghopmon);
        tv_tongtienhoadon=findViewById(R.id.tv_tongtienhoadon);
        tv_phiShip=findViewById(R.id.tv_phiShip);
        tv_giamGia=findViewById(R.id.tv_giamGia);
        tv_tongtien=findViewById(R.id.tv_tongtien);
        edNameUser=findViewById(R.id.edNameUser);
        edSDTUser=findViewById(R.id.edSDTUser);
        edAddressUser=findViewById(R.id.edAddressUser);
        but_bill_Cancel=findViewById(R.id.but_bill_Cancel);
        but_bill_OK=findViewById(R.id.but_bill_OK);
    }
}