package com.example.appdoan_vutruonggiang.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.adapter.AdapterRecyleViewSumDaGiao;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.modle.ThongTinNguoiOrder;
import com.example.appdoan_vutruonggiang.view.activity.HomePageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentDaGiao extends Fragment {
    RecyclerView recyclerView;
    List<ThongTinNguoiOrder> thongTinNguoiNhanList;
    HomePageActivity homePageActivity;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AdapterRecyleViewSumDaGiao adapterRecyleViewSumDaGiao;
    FirebaseUser user;
    String email="";
    TextView tvCart,tvDaGiao;
    private View view;

    public static Fragment newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentDaGiao fragment = new FragmentDaGiao();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_dagiao,container,false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String[] arrayEmail = user.getEmail().split("@");
        email = email + arrayEmail[0];
        init();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("thong_tin_nguoi_nhan_hang").child(email);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thongTinNguoiNhanList=new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                for(DataSnapshot data:dataSnapshotIterable){
                    ThongTinNguoiOrder thongTinNguoiOrder=data.getValue(ThongTinNguoiOrder.class);
                    thongTinNguoiNhanList.add(thongTinNguoiOrder);
                }
                adapterRecyleViewSumDaGiao=new AdapterRecyleViewSumDaGiao(thongTinNguoiNhanList, email, homePageActivity);
                recyclerView.setAdapter(adapterRecyleViewSumDaGiao);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePageActivity.getFragment(FragmentCart.newInstance());
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(adapterRecyleViewSumDaGiao!=null){
            adapterRecyleViewSumDaGiao.release();
        }
    }

    public void init(){
        homePageActivity= (HomePageActivity) getActivity();
        recyclerView=view.findViewById(R.id.dataDaGiao);
        tvCart=view.findViewById(R.id.tvCart);
        tvDaGiao=view.findViewById(R.id.tvDaGiao);
    }
}
