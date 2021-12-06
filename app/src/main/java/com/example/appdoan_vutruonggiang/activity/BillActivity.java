package com.example.appdoan_vutruonggiang.activity;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.appdoan_vutruonggiang.adapter.AdapterRecyleviewBilled;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.Bill;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BillActivity extends Activity {
    ImageView but_bill_back_account;
    RecyclerView dataBilled;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String sdt="",hoTen="",diaChi="",email="",pass="";
    List<Food> foodList;
    List<Bill> billList,bills;
    AdapterRecyleviewBilled adapterRecyleviewBilled;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bill2);
        anhXa();

        Bundle bundle=this.getIntent().getExtras();
        sdt=sdt+bundle.getString("phoneNumber");
        hoTen=hoTen+bundle.getString("hoten");
        diaChi=diaChi+bundle.getString("diachi");
        email=email+bundle.getString("gmail");
        pass=pass+bundle.get("pass");
        foodList=bundle.getParcelableArrayList("list");

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(BillActivity.this,RecyclerView.VERTICAL,false);
        dataBilled.setLayoutManager(layoutManager);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("bill").child(sdt);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                billList=new ArrayList<>();
                bills=new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                for(DataSnapshot data:dataSnapshotIterable){
                    Bill bill=data.getValue(Bill.class);
                    billList.add(bill);
                }
                for(int i=billList.size()-1;i>=0;i--){
                    Bill bill=billList.get(i);
                    bills.add(bill);
                }
                adapterRecyleviewBilled=new AdapterRecyleviewBilled(bills,BillActivity.this);
                dataBilled.setAdapter(adapterRecyleviewBilled);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        but_bill_back_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), AccountActivity.class);
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
        but_bill_back_account=findViewById(R.id.but_bill_back_account);
        dataBilled=findViewById(R.id.dataBilled);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adapterRecyleviewBilled!=null){
            adapterRecyleviewBilled.release();
        }
    }
}