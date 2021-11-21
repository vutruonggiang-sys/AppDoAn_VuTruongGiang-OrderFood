package com.example.appdoan_vutruonggiang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdoan_vutruonggiang.Adapter.AdapterRecyleViewGiaoHangCart;
import com.example.appdoan_vutruonggiang.R;
import com.example.appdoan_vutruonggiang.UI.Food_Order;
import com.example.appdoan_vutruonggiang.UI.NhaHang;
import com.example.appdoan_vutruonggiang.activity.CartActivity;
import com.example.appdoan_vutruonggiang.sqlite.SqliteHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Fragment_Cart extends Fragment {
    RecyclerView recyclerView;
    Button but_order;
    List<Food_Order> food_orderList;
    CartActivity cartActivity;
    FirebaseDatabase firebaseDatabase;
    SqliteHelper sqliteHelper;
    List<Food_Order> listTable;
    public static Fragment newInstance() {

        Bundle args = new Bundle();

        Fragment_Cart fragment = new Fragment_Cart();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_cart,container,false);
        cartActivity= (CartActivity) getActivity();
        recyclerView=view.findViewById(R.id.dataGioHang);
        but_order=view.findViewById(R.id.but_order_cart);
        sqliteHelper=new SqliteHelper(cartActivity);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration decoration=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("food_giohang").child(cartActivity.getSdt());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                food_orderList=new ArrayList<>();
                Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                for(DataSnapshot data:dataSnapshotIterable){
                    Food_Order food_order=data.getValue(Food_Order.class);
                    food_orderList.add(food_order);
                }
                AdapterRecyleViewGiaoHangCart adapterRecyleViewGiaoHangCart=new AdapterRecyleViewGiaoHangCart(food_orderList,cartActivity);
                recyclerView.setAdapter(adapterRecyleViewGiaoHangCart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //databaseReference=firebaseDatabase.getReference().child("food_giohang").child(cartActivity.getSdt());
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position=viewHolder.getAdapterPosition();
                Food_Order food_order=food_orderList.get(position);
                food_orderList.remove(position);
                AdapterRecyleViewGiaoHangCart adapterRecyleViewGiaoHangCart1=new AdapterRecyleViewGiaoHangCart(food_orderList,cartActivity);
                recyclerView.setAdapter(adapterRecyleViewGiaoHangCart1);
                databaseReference.child(food_order.getId()).removeValue();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        but_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listTable=sqliteHelper.getAllGioHang();
                for(int i=1;i<listTable.size();i++){
                    if(!listTable.get(i).getIdNhaHang().equals(listTable.get(0).getIdNhaHang())){
                        Toast.makeText(cartActivity,"Bạn cần chọn các món ăn từ một nhà hàng để đưa vào đơn hàng! Vui Lòng Quý Khách Chọn Lại",Toast.LENGTH_LONG).show();
                        sqliteHelper.onDeleteAllGioHang();
                        return;
                    }
                }

//                list=Process_Food.xoaHetArray(food_orderList);
                AdapterRecyleViewGiaoHangCart adapterRecyleViewGiaoHangCart=new AdapterRecyleViewGiaoHangCart(food_orderList,cartActivity);
                recyclerView.setAdapter(adapterRecyleViewGiaoHangCart);

                if(listTable.size()!=0) {
                   DatabaseReference databaseReference1=firebaseDatabase.getReference().child("nhaHang");
                   databaseReference1.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           Iterable<DataSnapshot> dataSnapshotIterable=snapshot.getChildren();
                           for(DataSnapshot data:dataSnapshotIterable){
                               NhaHang nhaHang=data.getValue(NhaHang.class);
                               if(nhaHang.getId().equals(listTable.get(0).getIdNhaHang())){
                                   Calendar calendar=Calendar.getInstance();
                                   SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                                   String gioHienTai=simpleDateFormat.format(calendar.getTime());
                                   try{
                                       Date hienTai= simpleDateFormat.parse(gioHienTai);
                                       Date mo=simpleDateFormat.parse(nhaHang.getOpen());
                                       Date dong=simpleDateFormat.parse(nhaHang.getClose());
                                       if(hienTai.after(mo)&&hienTai.before(dong)){
                                           cartActivity.setFood_orderListDaGiao(listTable);
                                           Toast.makeText(cartActivity,"Hàng đã bắt giao",Toast.LENGTH_SHORT).show();
                                           for(int i=0;i<listTable.size();i++){
                                               databaseReference.child(listTable.get(i).getId()).removeValue();
                                               food_orderList.remove(listTable.get(i));
                                           }
                                           AdapterRecyleViewGiaoHangCart adapterRecyleViewGiaoHangCart2=new AdapterRecyleViewGiaoHangCart(food_orderList,cartActivity);
                                           recyclerView.setAdapter(adapterRecyleViewGiaoHangCart2);
                                           sqliteHelper.onDeleteAllGioHang();
                                       }else{
                                           if(hienTai.before(mo))
                                               Toast.makeText(cartActivity,"Hiện tại quán chưa mở cửa, Quý Khách Vui Lòng Chờ hoặc Chọn Cửa Hàng Mở Cửa",Toast.LENGTH_LONG).show();
                                           if(hienTai.after(dong))
                                               Toast.makeText(cartActivity,"Hiện tại quán đã đóng cửa, Quý Khách Vui Lòng Chờ hoặc Chọn Cửa Hàng Mở Cửa",Toast.LENGTH_LONG).show();
                                           sqliteHelper.onDeleteAllGioHang();
                                       }
                                   }catch (Exception e){

                                   }
                               }
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });
                }else{
                    Toast.makeText(cartActivity,"Bạn chưa chọn, Vui lòng bạn chọn để chúng tôi giao hàng nhanh nhất!!!",Toast.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }
}