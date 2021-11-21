package com.example.appdoan_vutruonggiang.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.appdoan_vutruonggiang.Adapter.AdapterRecyleThongBao;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.UI.ThongBao;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThongBaoActivity extends Activity{
    ImageView but_back_giohang;
    RecyclerView dataThongBao;
    AdapterRecyleThongBao adapterRecyleThongBao;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<ThongBao> thongBaoList,thongBaos;
    String sdt="",hoTen="",diaChi="",email="",pass="";
    List<Food> foodList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_thong_bao);
        anhXa();
        Bundle bundle=this.getIntent().getExtras();
        sdt=sdt+bundle.getString("phoneNumber");
        hoTen=hoTen+bundle.getString("hoten");
        diaChi=diaChi+bundle.getString("diachi");
        email=email+bundle.getString("gmail");
        pass=pass+bundle.get("pass");
        foodList=bundle.getParcelableArrayList("list");

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(ThongBaoActivity.this,RecyclerView.VERTICAL,false);
        dataThongBao.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration decoration=new DividerItemDecoration(ThongBaoActivity.this,DividerItemDecoration.VERTICAL);
        dataThongBao.addItemDecoration(decoration);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("thongbao").child(sdt);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thongBaoList=new ArrayList<>();
                thongBaos=new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                for(DataSnapshot data:dataSnapshotIterable){
                    ThongBao thongBao=data.getValue(ThongBao.class);
                    thongBaoList.add(thongBao);
                }
                for(int i=thongBaoList.size()-1;i>=0;i--){
                    ThongBao tg=thongBaoList.get(i);
                    thongBaos.add(tg);
                }
                adapterRecyleThongBao =new AdapterRecyleThongBao(thongBaos);
                dataThongBao.setAdapter(adapterRecyleThongBao);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        but_back_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), CartActivity.class);
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
        but_back_giohang=findViewById(R.id.but_back_giohang);
        dataThongBao=findViewById(R.id.dataThongBao);
    }
}