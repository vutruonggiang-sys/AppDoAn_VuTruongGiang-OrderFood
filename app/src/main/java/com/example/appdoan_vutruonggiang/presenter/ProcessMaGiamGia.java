package com.example.appdoan_vutruonggiang.presenter;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.adapter.AdapterRecyleViewMaGiamGia;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.GiamGia;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProcessMaGiamGia {
    public ProcessMaGiamGia() {
    }

    public void getMaGiamGia(Context context, String sdt){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_magiamgia);
        RecyclerView recyclerView=dialog.findViewById(R.id.dataMaPhiGiamGia);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("giam_gia");
        databaseReference.child(sdt).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<GiamGia> giamGiaList;
                giamGiaList=new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                for(DataSnapshot data:dataSnapshotIterable){
                    GiamGia giamGia=data.getValue(GiamGia.class);
                    giamGiaList.add(giamGia);
                }
                AdapterRecyleViewMaGiamGia adapterRecyleViewMaGiamGia=new AdapterRecyleViewMaGiamGia(giamGiaList,sdt);
                recyclerView.setAdapter(adapterRecyleViewMaGiamGia);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        dialog.show();
    }
}
