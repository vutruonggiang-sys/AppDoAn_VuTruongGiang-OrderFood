package com.example.appdoan_vutruonggiang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoan_vutruonggiang.Adapter.AdapterRecyleViewSumDaGiao;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.UI.Food_Order;
import com.example.appdoan_vutruonggiang.UI.GiamGia;
import com.example.appdoan_vutruonggiang.UI.Key;
import com.example.appdoan_vutruonggiang.activity.CartActivity;
import com.example.appdoan_vutruonggiang.activity.PrintBillActivity;
import com.example.appdoan_vutruonggiang.inteface.IThanhToan;
import com.example.appdoan_vutruonggiang.presenter.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_DaGiao extends Fragment {
    RecyclerView recyclerView;
    Button thanhToan;
    TextView tvSum;
    List<Food_Order> food_orderList;
    List<Key> keyList;
    CartActivity cartActivity;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AdapterRecyleViewSumDaGiao adapterRecyleViewSumDaGiao;
    List<GiamGia> giamGiaList;
    long giaG=0;
    IThanhToan iThanhToan;
    public static Fragment newInstance() {
        
        Bundle args = new Bundle();
        
        Fragment_DaGiao fragment = new Fragment_DaGiao();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dagiao,container,false);
        cartActivity= (CartActivity) getActivity();
        recyclerView=view.findViewById(R.id.dataDaGiao);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("key_dagiao").child(cartActivity.getSdt());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                keyList=new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                for(DataSnapshot data:dataSnapshotIterable){
                    Key key=data.getValue(Key.class);
                    keyList.add(key);
                }
                adapterRecyleViewSumDaGiao=new AdapterRecyleViewSumDaGiao(keyList, cartActivity.getSdt(), cartActivity, new IThanhToan() {
                    @Override
                    public void getKey_delete(String key) {
                        Intent intent=new Intent(cartActivity, PrintBillActivity.class);
                        ArrayList<Food> listSearch= (ArrayList<Food>) cartActivity.getFoodList();
                        Bundle bundle1=new Bundle();
                        bundle1.putString("phoneNumber",cartActivity.getSdt());
                        bundle1.putString("hoten",cartActivity.getHoTen());
                        bundle1.putString("diachi",cartActivity.getDiaChi());
                        bundle1.putString("gmail",cartActivity.getEmail());
                        bundle1.putString("pass",cartActivity.getPass());
                        bundle1.putString("maDelete",key);
                        bundle1.putParcelableArrayList("list",listSearch);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapterRecyleViewSumDaGiao);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        tvSum=view.findViewById(R.id.tv_sum_money);
//        recyclerView.setLayoutManager(layoutManager);
//        food_orderList=new ArrayList<>();
//        food_orderList=cartActivity.getFood_orderListDaGiao();
//        tvSum.setText(cartActivity.getSum()+"");
//        RecyclerView.ItemDecoration decoration=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(decoration);
//        firebaseDatabase=FirebaseDatabase.getInstance();
//        databaseReference=firebaseDatabase.getReference().child("da_giao").child(cartActivity.getSdt());
////        databaseReference1=firebaseDatabase.getReference().child("da_giao").child(cartActivity.getSdt());
////
////        databaseReference1.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
////
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////
////            }
////        });
//        AdapterRecyleViewGioHang adapterRecyleViewGioHang=new AdapterRecyleViewGioHang(food_orderList);
//        recyclerView.setAdapter(adapterRecyleViewGioHang);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(adapterRecyleViewSumDaGiao!=null){
            adapterRecyleViewSumDaGiao.release();
        }
    }
}
